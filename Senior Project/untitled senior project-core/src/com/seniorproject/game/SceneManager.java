package com.seniorproject.game;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.seniorproject.enums.SceneFiles;
import com.seniorproject.scripting.ActionsConfig.Action;
import com.seniorproject.scripting.ActionsConfig.ActorAction;
import com.seniorproject.scripting.ActionsConfig.LineAction;
import com.seniorproject.scripting.ScriptConfig.Line;
import com.seniorproject.scripting.ScriptConfig.LineText;
import com.seniorproject.systems.PerformanceSystem;
import com.seniorproject.enums.CharacterName;

public class SceneManager
{
	private static final String TAG = SceneManager.class.getSimpleName();
	
	//private static SceneManager instance;
	
	private int currentLineID;
	
	private Array<SceneFiles> scenes;
	private int currentSceneIndex = 0;
	
	private Scene currentScene;
	private Line currentLine;
	private LineText currentLineText;
	
	private boolean lineIsShared;
	private int sharedLineIndex;
	
	private String currentActor = "";
	private String currentText = "";
	private Array<String> currentTextDisplay;
	
	private boolean actorHasChanged;
	
	//
	
	private LineAction currentLineAction;
	private World world;
	
	public SceneManager(World world)
	{
		this.world = world;
		this.scenes = new Array<SceneFiles>();
		scenes.addAll(SceneFiles.ACT1SCENE1,
				SceneFiles.ACT1SCENE2,
				SceneFiles.ACT1SCENE3,
				SceneFiles.ACT1SCENE4,
				SceneFiles.ACT1SCENE5,
				SceneFiles.ACT1SCENE6,
				SceneFiles.ACT1SCENE7);
		
		this.currentSceneIndex = 5;
		setupNewScene(new Scene(scenes.get(currentSceneIndex)));
	}
	
	public void setupNewScene(Scene scene)
	{
		currentScene = scene;
		currentLineID = 1;
		sharedLineIndex = 0;
		actorHasChanged = true;
		
		currentLine = currentScene.getLineByID(currentLineID);
		lineIsShared = currentLine.isSharedLine();
		
		currentLineText = currentLine.getLineText().get(0);
		
		currentActor = currentLineText.getActor();
		currentText = currentLineText.getText();
		
		currentTextDisplay = new Array<String>();
		currentTextDisplay.add(currentLineText.getText());
		
		currentLineAction = currentScene.getLineActions().get(0);
		updateCharacterActions();
	}
	
	public void stepForward()
	{
		if(lineIsShared && sharedLineIndex < currentLine.getLineText().size - 1)
		{
			
			sharedLineIndex += 1;
		}
		else
		{
			currentLineID += 1;
			sharedLineIndex = 0;
		}
		
		currentLine = currentScene.getLineByID(currentLineID);
		//Gdx.app.debug(TAG, "currentLine: " + currentLine.getLineText().get(sharedLineIndex).getText());
		String newActor = currentLine.getLineText().get(sharedLineIndex).getActor();
		
		lineIsShared = currentLine.isSharedLine();
		
		updateTextDisplay(currentLineID, newActor);
		
		if(sharedLineIndex == 0)
		{
			//We're only updating the actions if we're at index 0 of a shared line-- otherwise, they repeat
			updateActions(currentLineID);
		}
		
		if(currentLineID >= currentScene.getLines().size)
		{
			currentScene.deactivateEntities();
			currentSceneIndex += 1;
			setupNewScene(new Scene(scenes.get(currentSceneIndex)));
		}
	}
	
	public void updateTextDisplay(int lineID, String actor)
	{
		//Gdx.app.debug(TAG, "Now in updateTextDisplay() method, lineID given as: " + lineID);
		
		currentLine = currentScene.getLineByID(lineID);
		
		if(currentActor.equals(actor))
		{
			//Gdx.app.debug(TAG, "\t\tcurrentActor is same as updatedActor");
			
			actorHasChanged = false;
			
			String newLine = currentLine.getLineText().get(sharedLineIndex).getText();
			currentTextDisplay.add(newLine);
			//Gdx.app.debug(TAG, "\t\tnewLine being added to speech: " + newLine);
			
			//Gdx.app.debug(TAG, "\t\tCurrent text display lines:");
			//for(int i = 0; i < currentTextDisplay.size; i++)
			//{
			//	Gdx.app.debug(TAG, "\t\t\tindex: " + i + "\tline: " + currentTextDisplay.get(i));
			//}
		}
		else
		{
			//Gdx.app.debug(TAG, "\t\tcurrentActor is NOT the same as updatedActor");
			
			actorHasChanged = true;
			
			currentActor = currentLine.getLineText().get(sharedLineIndex).getActor();
			
			clearTextDisplay();
			String newLine = currentLine.getLineText().get(sharedLineIndex).getText();
			currentTextDisplay.add(newLine);
			
			//Gdx.app.debug(TAG, "\t\tCurrent speech lines:");
			//for(int i = 0; i < currentTextDisplay.size; i++)
			//{
			//	Gdx.app.debug(TAG, "\t\t\tindex: " + i + "\tline: " + currentTextDisplay.get(i));
			//}
		}
	}
	
	public boolean lineIsShared()
	{
		return lineIsShared;
	}
	
	public boolean actorHasChanged()
	{
		return actorHasChanged;
	}
	
	public void updateActions(int lineID)
	{
		currentLineAction = currentScene.getLineActionsByID(lineID);
		
		updateCharacterActions();
	}
	
	public Scene getCurrentScene()
	{
		return currentScene;
	}
	
	public int getCurrentLineID()
	{
		return currentLineID;
	}
	
	public String getCurrentActor()
	{
		return currentActor;
	}
	
	public String getCurrentLineText()
	{
		return currentText;
	}
	
	public Array<String> getCurrentTextDisplay()
	{
		return currentTextDisplay;
	}
	
	public void clearTextDisplay()
	{
		//Gdx.app.debug(TAG, "Clearing text array...");
		
		while(currentTextDisplay.notEmpty())
		{
			currentTextDisplay.removeIndex(0);
		}
		
		//Gdx.app.debug(TAG, "text displayed array now size " + currentTextDisplay.size);
	}
	
	private void updateCharacterActions()
	{
		Array<ActorAction> lineActions = currentLineAction.getActorActions();
		
		for(int i = 0; i < lineActions.size; i++)
		{
			ActorAction list = lineActions.get(i);
			
			Array<Action> actions = list.getActions();
			
			CharacterName name = list.getActor();
			int entityId = currentScene.getEntityIdByName(name);
			
			world.getSystem(PerformanceSystem.class).updateCharacterActions(entityId, actions);
			
			//Gdx.app.debug(TAG, "Actions added to actor " + name);
			//for(int j = 0; j < actions.size; j++)
			//{
			//	Action tempAction = actions.get(j);
			//	Gdx.app.debug(TAG, "\t\tAction at index " + j + ": " + tempAction.getAction() + " " + tempAction.getDirection());
			//}
		}
	}
}
