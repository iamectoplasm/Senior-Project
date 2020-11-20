package com.seniorproject.game;

import java.util.Hashtable;

import com.artemis.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.seniorproject.components.DrawableSprite;
import com.seniorproject.components.StagePosition;
import com.seniorproject.components.Position;
import com.seniorproject.components.PropComponent;
import com.seniorproject.configs.PerformConfig;
import com.seniorproject.configs.ScriptConfig;
import com.seniorproject.configs.StudyConfig;
import com.seniorproject.configs.PerformConfig.ActionsForLine;
import com.seniorproject.configs.PerformConfig.ActorSetup;
import com.seniorproject.configs.PerformConfig.PropSetup;
import com.seniorproject.configs.PerformConfig.SceneSetup;
import com.seniorproject.configs.ScriptConfig.Line;
import com.seniorproject.enums.*;
import com.seniorproject.maps.StageMap;

public class Scene
{
	private static final String TAG = Scene.class.getSimpleName();
	
	private String actSceneTitle;
	
	private ScriptConfig scriptConfig;
	private PerformConfig actionsConfig;
	
	private Hashtable<String, StudyConfig> studyConfigs;
	private Array<Line> lines;
	//private Array<SceneSetup> sceneSetup;
	private SceneSetup sceneSetup;
	private Array<ActorSetup> actorSetupArray;
	private Array<PropSetup> propSetupArray;
	
	
	private Array<ActionsForLine> lineActions;
	
	private Hashtable<CharacterName, Integer> performerTable;
	private Array<Entity> performersInScene;
	private Array<Entity> propsInScene;
	
	public Scene(SceneFiles scene)
	{
		Json tempJson = new Json();
		actSceneTitle = scene.toString();
		scriptConfig = tempJson.fromJson(ScriptConfig.class, Gdx.files.internal(scene.getScriptFilePath()).read());
		actionsConfig = tempJson.fromJson(PerformConfig.class, Gdx.files.internal(scene.getActionsFilePath()).read());
		
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
		
		//sceneSetup = actionsConfig.getSceneSetup();
		sceneSetup = actionsConfig.getSetup();
		actorSetupArray = sceneSetup.getActorSetup();
		propSetupArray = sceneSetup.getPropSetup();
		
		lineActions = actionsConfig.getActionsForLine();
		
		performerTable = new Hashtable<CharacterName, Integer>();
		performersInScene = new Array<Entity>();
		propsInScene = new Array<Entity>();
		
		//for(int i = 0; i < sceneSetup.size; i++)
		for(int i = 0; i < actorSetupArray.size; i++)
		{
			//ActorSetup setup = sceneSetup.get(i);
			ActorSetup setup = actorSetupArray.get(i);
			
			PerformerManager.getInstance();
			Entity newPerformer = PerformerManager.activatePerformer(setup.getActor());
			performersInScene.add(newPerformer);
			//Gdx.app.debug(TAG, "Performer " + setup.getActor().name() + " has been activated.");
			
			Vector2 normalizedStart = StageMap.getPositionNormalized(setup.getStartPosition());
			
			//Gdx.app.debug(TAG, "player being started at (" + normalizedStart.x + ", " + normalizedStart.y + ")");
			
			newPerformer.getComponent(StagePosition.class).startingPosition = new Vector2(normalizedStart.x, normalizedStart.y);
			newPerformer.getComponent(StagePosition.class).resetAllToStarting();
			newPerformer.getComponent(StagePosition.class).mapZIndex = setup.getStageLayer().getZIndex();
			
			performerTable.put(setup.getActor(), newPerformer.getId());
		}
		
		for(int i = 0; i < propSetupArray.size; i++)
		{
			PropSetup setup = propSetupArray.get(i);
			Entity newProp = PerformerManager.activateProp(setup.getProp());
			propsInScene.add(newProp);
			
			Prop.PropType type = Prop.getPropType(setup.getProp());
			
			StageMap map = SeniorProject.performanceScreen.getStageMap();
			switch(type)
			{
			case LIGHTING:
				map.setLightingLayer(newProp.getComponent(DrawableSprite.class).currentFrame);
				break;
			case OBJECT:
				newProp.getComponent(Position.class).position = new Vector2(50, 50);
			default:
				break;
			}
		}
	}
	
	public void deactivateEntities()
	{
		//for(int i = 0; i < sceneSetup.size; i++)
		for(int i = 0; i < actorSetupArray.size; i++)
		{
			//PerformerManager.deactivatePerformer(sceneSetup.get(i).getActor());
			PerformerManager.deactivatePerformer(actorSetupArray.get(i).getActor());
			//Gdx.app.debug(TAG, "Performer " + sceneSetup.get(i).getActor().name() + " has been deactivated.");
		}
		
		for(int i = 0; i < propSetupArray.size; i++)
		{
			PerformerManager.activateProp(propSetupArray.get(i).getProp());
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
		return performerTable.get(name);
	}
	
	public Hashtable<String, StudyConfig> getStudyConfigs()
	{
		return studyConfigs;
	}
	
	public ScriptConfig getScriptConfigFile()
	{
		return scriptConfig;
	}
	
	public Array<Entity> getPerformersInScene()
	{
		return performersInScene;
	}
}
