package com.seniorproject.game;

import java.util.Hashtable;

import com.artemis.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.seniorproject.components.Position;
import com.seniorproject.configs.BlockingConfig;
import com.seniorproject.configs.ScriptConfig;
import com.seniorproject.configs.StudyConfig;
import com.seniorproject.configs.BlockingConfig.ActionsForLine;
import com.seniorproject.configs.BlockingConfig.Setup;
import com.seniorproject.configs.ScriptConfig.Line;
import com.seniorproject.enums.*;
import com.seniorproject.maps.StageMap;

public class Scene
{
	private static final String TAG = Scene.class.getSimpleName();
	
	private String actSceneTitle;
	
	private ScriptConfig scriptConfig;
	private BlockingConfig actionsConfig;
	
	private Hashtable<String, StudyConfig> studyConfigs;
	private Array<Line> lines;
	private Array<Setup> sceneSetup;
	private Array<ActionsForLine> lineActions;
	private Hashtable<CharacterName, Integer> entityTable;
	
	public Scene(SceneFiles scene)
	{
		Json tempJson = new Json();
		actSceneTitle = scene.toString();
		scriptConfig = tempJson.fromJson(ScriptConfig.class, Gdx.files.internal(scene.getScriptFilePath()).read());
		actionsConfig = tempJson.fromJson(BlockingConfig.class, Gdx.files.internal(scene.getActionsFilePath()).read());
		
		StudyConfig analysisConfig = tempJson.fromJson(StudyConfig.class, Gdx.files.internal(scene.getAnalysisFilePath()).read());
		StudyConfig breakdownConfig = tempJson.fromJson(StudyConfig.class, Gdx.files.internal(scene.getBreakdownFilePath()).read());
		StudyConfig fullTextConfig = tempJson.fromJson(StudyConfig.class, Gdx.files.internal(scene.getFullTextFilePath()).read());
		StudyConfig translationConfig = tempJson.fromJson(StudyConfig.class, Gdx.files.internal(scene.getTranslationFilePath()).read());
		
		studyConfigs = new Hashtable<String, StudyConfig>();
		studyConfigs.put("analysis", analysisConfig);
		studyConfigs.put("breakdown", breakdownConfig);
		studyConfigs.put("fulltext", fullTextConfig);
		studyConfigs.put("translation", translationConfig);
		
		lines = scriptConfig.getScript();
		
		sceneSetup = actionsConfig.getSceneSetup();
		lineActions = actionsConfig.getBlockingForLine();
		
		entityTable = new Hashtable<CharacterName, Integer>();
		
		for(int i = 0; i < sceneSetup.size; i++)
		{
			Setup setup = sceneSetup.get(i);
			
			PerformerManager.getInstance();
			Entity newPerformer = PerformerManager.activatePerformer(setup.getActor());
			//Gdx.app.debug(TAG, "Performer " + setup.getActor().name() + " has been activated.");
			
			Vector2 normalizedStart = StageMap.normalizePosition(setup.getStartPosition());
			
			//Gdx.app.debug(TAG, "player being started at (" + normalizedStart.x + ", " + normalizedStart.y + ")");
			
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
			//Gdx.app.debug(TAG, "Performer " + sceneSetup.get(i).getActor().name() + " has been deactivated.");
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
	
	public Array<ActionsForLine> getLineActions()
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
	
	public ActionsForLine getLineActionsByID(int id)
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
	
	public ScriptConfig getScriptConfigFile()
	{
		return scriptConfig;
	}
}
