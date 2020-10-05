package com.seniorproject.game;

import java.util.Hashtable;

import com.artemis.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.seniorproject.components.Position;
import com.seniorproject.game.ActionsConfig.LineAction;
import com.seniorproject.game.ActionsConfig.Setup;
import com.seniorproject.game.EntityFactory.PerformerName;
import com.seniorproject.game.SceneManager.SceneStudy;
import com.seniorproject.game.ScriptConfig.Line;
import com.seniorproject.maps.StageMap;
import com.seniorproject.scenemeta.StudyConfig;
import com.seniorproject.screens.PerformanceScreen;

public class Scene
{
	private static final String TAG = Scene.class.getSimpleName();
	
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
	
	private Hashtable<PerformerName, Integer> entityTable;
	
	public Scene(SceneManager.ScenePerformance scene)
	{
		Json tempJson = new Json();
		scriptConfig = tempJson.fromJson(ScriptConfig.class, Gdx.files.internal(scene.getScriptFilePath()).read());
		actionsConfig = tempJson.fromJson(ActionsConfig.class, Gdx.files.internal(scene.getActionsFilePath()).read());
		
		SceneStudy sceneStudy = SceneManager.SceneStudy.valueOf(scene.toString());
		Gdx.app.debug(TAG, "sceneStudy: " + sceneStudy);
		
		StudyConfig analysisConfig = tempJson.fromJson(StudyConfig.class, Gdx.files.internal(sceneStudy.getAnalysisFilePath()).read());
		StudyConfig breakdownConfig = tempJson.fromJson(StudyConfig.class, Gdx.files.internal(sceneStudy.getBreakdownFilePath()).read());
		StudyConfig fullTextConfig = tempJson.fromJson(StudyConfig.class, Gdx.files.internal(sceneStudy.getFullTextFilePath()).read());
		StudyConfig translationConfig = tempJson.fromJson(StudyConfig.class, Gdx.files.internal(sceneStudy.getTranslationFilePath()).read());
		
		studyConfigs = new Hashtable<String, StudyConfig>();
		studyConfigs.put("analysis", analysisConfig);
		studyConfigs.put("breakdown", breakdownConfig);
		studyConfigs.put("fulltext", fullTextConfig);
		studyConfigs.put("translation", translationConfig);
		
		//castList = scriptConfig.getCast();
		lines = scriptConfig.getScript();
		
		sceneSetup = actionsConfig.getSceneSetup();
		lineActions = actionsConfig.getLineActions();
		
		entityTable = new Hashtable<PerformerName, Integer>();
		
		for(int i = 0; i < sceneSetup.size; i++)
		{
			Setup setup = sceneSetup.get(i);
			Entity newPerformer = EntityFactory.getPerformerEntity(setup.getActor());
			
			//int flippedY = StageMap.normalizePosition((int) setup.getStartPosition().y);
			Vector2 normalizedStart = StageMap.normalizePosition(setup.getStartPosition());
			
			Gdx.app.debug(TAG, "player being started at (" + normalizedStart.x + ", " + normalizedStart.y + ")");
			
			newPerformer.getComponent(Position.class).startingPosition = new Vector2(normalizedStart.x, normalizedStart.y);
			newPerformer.getComponent(Position.class).resetAllToStarting();
			
			entityTable.put(setup.getActor(), newPerformer.getId());
		}
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
	
	public int getEntityIdByName(PerformerName name)
	{
		return entityTable.get(name);
	}
	
	public Hashtable<String, StudyConfig> getStudyConfigs()
	{
		return studyConfigs;
	}
}
