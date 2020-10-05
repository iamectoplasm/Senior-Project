package com.seniorproject.game;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.seniorproject.game.ActionsConfig.Action;
import com.seniorproject.game.ActionsConfig.ActorAction;
import com.seniorproject.game.ActionsConfig.LineAction;
import com.seniorproject.game.EntityFactory.PerformerName;
import com.seniorproject.game.ScriptConfig.Line;
import com.seniorproject.game.ScriptConfig.LineText;
import com.seniorproject.systems.PerformanceSystem;

public class SceneManager
{
	private static final String TAG = SceneManager.class.getSimpleName();
	
	//private static SceneManager instance;
	
	public static enum ScenePerformance
	{
		ACT1SCENE1("scenes/act 1/act1scene1.json", "scenes/act 1/act1scene1actions.json"),
		ACT1SCENE2("scenes/act 1/act1scene2.json", "scenes/act 1/act1scene2actions.json"),
		ACT1SCENE3("scenes/act 1/act1scene3.json", "scenes/act 1/act1scene3actions.json"),
		ACT1SCENE4("scenes/act 1/act1scene4.json", "scenes/act 1/act1scene4actions.json"),
		ACT1SCENE5("scenes/act 1/act1scene5.json", "scenes/act 1/act1scene5actions.json"),
		ACT1SCENE6("scenes/act 1/act1scene6.json", "scenes/act 1/act1scene6actions.json"),
		ACT1SCENE7("scenes/act 1/act1scene7.json", "scenes/act 1/act1scene7actions.json");
		
		private String scriptFile;
		private String actionsFile;
		
		private ScenePerformance(String scriptFile, String actionsFile)
		{
			this.scriptFile = scriptFile;
			this.actionsFile = actionsFile;
		}
		
		public String getScriptFilePath()
		{
			return scriptFile;
		}
		
		public String getActionsFilePath()
		{
			return actionsFile;
		}
	}
	
	public static enum SceneStudy
	{
		ACT1SCENE6("meta/act 1/1-6 analysis.json",
				"meta/act 1/1-6 breakdown.json",
				"meta/act 1/1-6 fulltext.json",
				"meta/act 1/1-6 translation.json");
		
		private String analysisFile;
		private String breakdownFile;
		private String fullTextFile;
		private String translationFile;
		
		private SceneStudy(String analysisFile,
				String breakdownFile,
				String scriptFile,
				String translationFile)
		{
			this.analysisFile = analysisFile;
			this.breakdownFile = breakdownFile;
			this.fullTextFile = scriptFile;
			this.translationFile = translationFile;
		}
		
		public String getAnalysisFilePath()
		{
			return analysisFile;
		}
		
		public String getBreakdownFilePath()
		{
			return breakdownFile;
		}
		
		public String getFullTextFilePath()
		{
			return fullTextFile;
		}
		
		public String getTranslationFilePath()
		{
			return translationFile;
		}
	}
	
	private int currentLineID;
	
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
		
		currentScene = new Scene(ScenePerformance.ACT1SCENE6);
		currentLineID = 1;
		sharedLineIndex = 0;
		actorHasChanged = true;
		
		currentLine = currentScene.getLineByID(currentLineID);
		lineIsShared = currentLine.isSharedLine();
		
		currentLineText = currentLine.getLineText().get(0);
		
		currentActor = currentLineText.getActor();
		currentText = currentLineText.getText();
		
		//
		
		currentTextDisplay = new Array<String>();
		currentTextDisplay.add(currentLineText.getText());
		
		//
		
		currentLineAction = currentScene.getLineActions().get(0);
		updateCharacterActions();
	}
	
	//public static SceneManager getInstance()
	//{
	//	if (instance == null)
	//	{
	//		instance = new SceneManager();
	//	}
	//
	//	return instance;
	//}
	
	public void stepForward()
	{
		if(lineIsShared && sharedLineIndex < currentLine.getLineText().size - 1)
		{
			
			sharedLineIndex += 1;
		}
		else
		{
			currentLineID += 1;
			//Gdx.app.debug(TAG, "currentLineID updated to " + currentLineID);
			sharedLineIndex = 0;
		}
		
		currentLine = currentScene.getLineByID(currentLineID);
		String newActor = currentLine.getLineText().get(sharedLineIndex).getActor();
		
		lineIsShared = currentLine.isSharedLine();
		
		updateTextDisplay(currentLineID, newActor);
		
		if(sharedLineIndex == 0)
		{
			//We're only updating the actions if we're at index 0 of a shared line-- otherwise, they repeat
			updateActions(currentLineID);
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
	
//	public void stepForward()
//	{
//		if(lineIsShared)
//		{
//			Gdx.app.debug(TAG, "In stepForward() method, line " + currentLine.getLineID() + " is shared");
//			
//			if(sharedLineIndex < currentLine.getLineText().size)
//			{
//				Gdx.app.debug(TAG, "\t\tIn stepForward() method, at sharedLineIndex " + sharedLineIndex);
//				
//				sharedLineIndex += 1;
//				String updatedActor = currentLine.getLineText().get(sharedLineIndex - 1).getActor();
//				//currentActor = currentLine.getLineText().get(sharedLineIndex - 1).getActor();
//				
//				if(currentActor.equals(updatedActor))
//				{
//					Gdx.app.debug(TAG, "\t\tIn stepForward() method, currentActor is the same as updatedActor");
//					
//					currentText = currentLine.getLineText().get(sharedLineIndex - 1).getText();
//					//String newLine = currentText;
//					//currentSpeech.add(newLine);
//					
//					//Gdx.app.debug(TAG, "\t\tCurrent speech lines:");
//					//for(int i = 0; i < currentSpeech.size; i++)
//					//{
//					//	Gdx.app.debug(TAG, "\t\t\tindex: " + i + "\tline: " + newLine);
//					//}
//				}
//				else
//				{
//					Gdx.app.debug(TAG, "\t\tIn stepForward() method, currentActor is NOT the same as updatedActor");
//					
//					currentActor = currentLine.getLineText().get(sharedLineIndex - 1).getActor();
//					currentText = currentLine.getLineText().get(sharedLineIndex - 1).getText();
//					
//					//clearSpeech();
//					//String newLine = currentLine.getLineText().get(sharedLineIndex - 1).getText();
//					//Gdx.app.debug(TAG, "newLine: " + newLine);
//					//currentSpeech.add(newLine);
//					
//					//Gdx.app.debug(TAG, "\t\tCurrent speech lines:");
//					//for(int i = 0; i < currentSpeech.size; i++)
//					//{
//					//	Gdx.app.debug(TAG, "\t\t\tindex: " + i + "\tline: " + newLine);
//					//}
//				}
//			}
//			else
//			{
//				Gdx.app.debug(TAG, "In stepForward() method, line " + currentLine.getLineID() + " is NOT shared");
//				
//				//sharedLineIndex = 0;
//				
//				//currentLineID += 1;
//				
//				updateScript(currentLineID);
//				updateActions(currentLineID);
//				
//				sharedLineIndex = 0;
//				currentLineID += 1;
//			}
//		}
//		else
//		{
//			//currentLineID += 1;
//			
//			updateScript(currentLineID);
//			updateActions(currentLineID);
//			
//			currentLineID += 1;
//			
//		}
//	}
	
//	public void updateScript(int lineID)
//	{
//		Gdx.app.debug(TAG, "Now in updateScript() method, lineID given as: " + lineID);
//		
//		currentLine = currentScene.getLineByID(lineID);
//		lineIsShared = currentLine.isSharedLine();
//		
//		String updatedActor = currentLine.getLineText().first().getActor();
//		
//		if(currentActor.equals(updatedActor))
//		{
//			Gdx.app.debug(TAG, "\t\tcurrentActor is same as updatedActor");
//			
//			currentText = currentText + "\n" + currentLine.getLineText().first().getText();
//			//String newLine = currentLine.getLineText().first().getText();
//			//currentSpeech.add(newLine);
//			//Gdx.app.debug(TAG, "\t\tnewLine being added to speech: " + newLine);
//			
//			//Gdx.app.debug(TAG, "\t\tCurrent speech lines:");
//			//for(int i = 0; i < currentSpeech.size; i++)
//			//{
//			//	Gdx.app.debug(TAG, "\t\t\tindex: " + i + "\tline: " + newLine);
//			//}
//		}
//		else
//		{
//			Gdx.app.debug(TAG, "\t\tcurrentActor is NOT the same as updatedActor");
//			
//			currentActor = currentLine.getLineText().first().getActor();
//			currentText = currentLine.getLineText().first().getText();
//			
//			//clearSpeech();
//			//String newLine = currentLine.getLineText().first().getText();
//			//currentSpeech.add(newLine);
//			
//			//Gdx.app.debug(TAG, "\t\tCurrent speech lines:");
//			//for(int i = 0; i < currentSpeech.size; i++)
//			//{
//			//	Gdx.app.debug(TAG, "\t\t\tindex: " + i + "\tline: " + newLine);
//			//}
//		}
//	}
	
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
		Gdx.app.debug(TAG, "Clearing text array...");
		
		while(currentTextDisplay.notEmpty())
		{
			currentTextDisplay.removeIndex(0);
		}
		
		Gdx.app.debug(TAG, "text displayed array now size " + currentTextDisplay.size);
	}
	
	private void updateCharacterActions()
	{
		Array<ActorAction> lineActions = currentLineAction.getActorActions();
		
		for(int i = 0; i < lineActions.size; i++)
		{
			ActorAction list = lineActions.get(i);
			
			Array<Action> actions = list.getActions();
			
			PerformerName name = list.getActor();
			int entityId = currentScene.getEntityIdByName(name);
			
			world.getSystem(PerformanceSystem.class).updateCharacterActions(entityId, actions);
			
			Gdx.app.debug(TAG, "Actions added to actor " + name);
			for(int j = 0; j < actions.size; j++)
			{
				Action tempAction = actions.get(j);
				Gdx.app.debug(TAG, "\t\tAction at index " + j + ": " + tempAction.getAction() + " " + tempAction.getDirection());
			}
		}
	}
}
