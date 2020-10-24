package com.seniorproject.game;

import java.util.Hashtable;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.seniorproject.components.Active;
import com.seniorproject.components.MovementState;
import com.seniorproject.components.Position;
import com.seniorproject.enums.*;
import com.seniorproject.maps.StageMap;
import com.seniorproject.scenemeta.StudyConfig;
import com.seniorproject.scripting.ActionsConfig;
import com.seniorproject.scripting.ScriptConfig;
import com.seniorproject.scripting.ActionsConfig.LineAction;
import com.seniorproject.scripting.ActionsConfig.Setup;
import com.seniorproject.scripting.ScriptConfig.Line;

public class Scene
{
	private static final String TAG = Scene.class.getSimpleName();
	
	private String actSceneTitle;
	
	private ScriptConfig scriptConfig;
	private ActionsConfig actionsConfig;
	//private Json json;
	
	private Hashtable<String, StudyConfig> studyConfigs;
	
	//private Array<String> castList;
	
	private Array<Line> lines;
	
	private Array<Setup> sceneSetup;
	
	private Array<LineAction> lineActions;
	//private Array<ActorAction> actorActions;
	
	//
	
	private Hashtable<CharacterName, Integer> entityTable;
	
	public Scene(SceneFiles scene)
	{
		Json tempJson = new Json();
		actSceneTitle = scene.toString();
		scriptConfig = tempJson.fromJson(ScriptConfig.class, Gdx.files.internal(scene.getScriptFilePath()).read());
		actionsConfig = tempJson.fromJson(ActionsConfig.class, Gdx.files.internal(scene.getActionsFilePath()).read());
		
		StudyConfig analysisConfig = tempJson.fromJson(StudyConfig.class, Gdx.files.internal(scene.getAnalysisFilePath()).read());
		StudyConfig breakdownConfig = tempJson.fromJson(StudyConfig.class, Gdx.files.internal(scene.getBreakdownFilePath()).read());
		StudyConfig fullTextConfig = tempJson.fromJson(StudyConfig.class, Gdx.files.internal(scene.getFullTextFilePath()).read());
		StudyConfig translationConfig = tempJson.fromJson(StudyConfig.class, Gdx.files.internal(scene.getTranslationFilePath()).read());
		
		studyConfigs = new Hashtable<String, StudyConfig>();
		studyConfigs.put("analysis", analysisConfig);
		studyConfigs.put("breakdown", breakdownConfig);
		studyConfigs.put("fulltext", fullTextConfig);
		studyConfigs.put("translation", translationConfig);
		
		//castList = scriptConfig.getCast();
		lines = scriptConfig.getScript();
		
		sceneSetup = actionsConfig.getSceneSetup();
		lineActions = actionsConfig.getLineActions();
		
		entityTable = new Hashtable<CharacterName, Integer>();
		
		for(int i = 0; i < sceneSetup.size; i++)
		{
			Setup setup = sceneSetup.get(i);
			//Entity newPerformer = EntityFactory.getPerformerEntity(setup.getActor());
			
			PerformerManager.getInstance();
			Entity newPerformer = PerformerManager.activatePerformer(setup.getActor());
			Gdx.app.debug(TAG, "Performer " + setup.getActor().name() + " has been activated.");
			
			//int flippedY = StageMap.normalizePosition((int) setup.getStartPosition().y);
			Vector2 normalizedStart = StageMap.normalizePosition(setup.getStartPosition());
			
			Gdx.app.debug(TAG, "player being started at (" + normalizedStart.x + ", " + normalizedStart.y + ")");
			
			newPerformer.getComponent(Position.class).startingPosition = new Vector2(normalizedStart.x, normalizedStart.y);
			newPerformer.getComponent(Position.class).resetAllToStarting();
			newPerformer.getComponent(Position.class).mapZIndex = setup.getStageLayer().getZIndex();
			
			entityTable.put(setup.getActor(), newPerformer.getId());
		}
	}
	
	public void deactivateEntities()
	{
		for(int i = 0; i < sceneSetup.size; i++)
		{
			PerformerManager.deactivatePerformer(sceneSetup.get(i).getActor());
			Gdx.app.debug(TAG, "Performer " + sceneSetup.get(i).getActor().name() + " has been deactivated.");
		}
	}
	
	public String getSceneTitle()
	{
		String retString = "Act " + actSceneTitle.charAt(3) + " Scene " + actSceneTitle.charAt(9);
		return retString;
	}
	
	public Array<Line> getLines()
	{
		return lines;
	}
	
	public Array<LineAction> getLineActions()
	{
		return lineActions;
	}
	
	public Line getLineByID(int id)
	{
		for(int i = 0; i < lines.size; i++)
		{
			if(lines.get(i).getLineID() == id)
			{
				return lines.get(i);
			}
		}
		
		return null;
	}
	
	public LineAction getLineActionsByID(int id)
	{
		for(int i = 0; i < lineActions.size; i++)
		{
			if(lineActions.get(i).getLineID() == id)
			{
				return lineActions.get(i);
			}
		}
		
		return null;
	}
	
	public int getEntityIdByName(CharacterName name)
	{
		return entityTable.get(name);
	}
	
	public Hashtable<String, StudyConfig> getStudyConfigs()
	{
		return studyConfigs;
	}
}
