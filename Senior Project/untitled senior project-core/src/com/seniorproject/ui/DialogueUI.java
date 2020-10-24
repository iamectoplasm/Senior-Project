package com.seniorproject.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.seniorproject.game.AssetLoader;

public class DialogueUI extends Window
{
	private static final String TAG = DialogueUI.class.getSimpleName();
	
	private Label nameLabel;
	private Image portrait;
	private Label dialogue;
	
	private ScrollPane textDisplay;
	
	//private Array<String> currentTextArray;
	private String currentLines;
	private String newLine;
	private boolean textDrawInProgress;
	private float deltaTimeElapsed;
	
	private float dialogDuration = 5f;
	private int newCharIndex = 0;
	
	public DialogueUI()
	{
		//super("", AssetLoader.DIALOGUE_SKIN, "dialogue-window");
		super("", AssetLoader.DIALOGUE_SKIN);
		
		//this.currentText = "";
		//this.currentTextArray = new Array<String>();
		this.currentLines = "";
		this.newLine = "";
		this.textDrawInProgress = false;
		
		this.setWidth(448);
		//this.setHeight(128);
		
		this.align(Align.topLeft);
		
		this.nameLabel = new Label("", AssetLoader.DIALOGUE_SKIN, "name-label");
		nameLabel.setAlignment(Align.center);
		
		Stack portraitStack = new Stack();
		Image portraitBG = new Image(AssetLoader.DIALOGUE_SKIN, "portrait-bg-gray");
		this.portrait = new Image(AssetLoader.DIALOGUE_SKIN, "DUNCAN");
		portraitStack.add(portraitBG);
		portraitStack.add(portrait);
		
		
		this.dialogue = new Label("", AssetLoader.DIALOGUE_SKIN, "dialogue-label");
		dialogue.setWrap(true);
		dialogue.setAlignment(Align.topLeft);
		
		textDisplay = new ScrollPane(dialogue, AssetLoader.DIALOGUE_SKIN);
		textDisplay.setTouchable(Touchable.enabled);
		textDisplay.setHeight(portraitBG.getHeight());
		
		//ScrollPane textDisplay = new ScrollPane(dialogueStack, AssetLoader.DIALOGUE_SKIN, "");
		
		this.add().colspan(1);
		this.add(nameLabel).align(Align.bottomLeft).padBottom(-4).padLeft(8);
		this.row();
		//this.add(portraitStack).expandY();
		this.add(portraitStack).padRight(0);
		this.add(textDisplay).expand().fill().pad(4, 4, 4, 16);
		
		this.defaults().expand().fill();
	}
	
	public void setNewSpeaker(String actor)
	{
		nameLabel.setText(actor);
		
		String portraitName = actor.toUpperCase().replace(' ', '_');
		portrait.setDrawable(AssetLoader.DIALOGUE_SKIN, portraitName);
		
		currentLines = "";
		dialogue.setText(currentLines);
	}
	
	public void setDialogue(String newDialogue)
	{
		dialogue.setText(newDialogue);
	}
	
	public void updateTextDisplay()
	{
		//textDisplay.setScrollY(textDisplay.getHeight());
		textDisplay.scrollTo(0, 0, 0, 0);
	}
	
	public boolean isTextDrawInProgress()
	{
		return textDrawInProgress;
	}
	
	public String getCurrentLines()
	{
		return currentLines;
	}
	
	public String getCurrentActor()
	{
		return nameLabel.getText().toString();
	}
	
	public void setTextDrawInProgress(boolean drawInProgress)
	{
		this.textDrawInProgress = drawInProgress;
	}
	
	public void setCurrentLines(String currentLines)
	{
		this.currentLines = currentLines;
		dialogue.setText(currentLines);
	}
	
	public void updateDialogueUI(float delta, Array<String> currentTextArray)
	{
		if(newLine.isEmpty())
		{
			newLine = currentTextArray.get(currentTextArray.size - 1);
			//Gdx.app.debug(TAG, "newLine: " + newLine);
		}
		
		deltaTimeElapsed += delta;
		textDisplay.scrollTo(0, 0, 0, 0);
			
		if(deltaTimeElapsed >= .025f)
		{
			currentLines = currentLines + newLine.charAt(newCharIndex);
			dialogue.setText(currentLines);
				
			newCharIndex++;
				
			deltaTimeElapsed = 0;
				
			if (newCharIndex > newLine.length() - 1)
			{
				currentLines = currentLines + "\n";
					
				newLine = "";
				newCharIndex = 0;
				setTextDrawInProgress(false);
			}
		}
	}
}
