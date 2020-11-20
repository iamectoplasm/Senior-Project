package com.seniorproject.screens;

import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.seniorproject.game.AssetLoader;

public class SceneSelectUI extends Window
{
	private Label currentActLabel;
	
	private Array<ImageTextButton> act1Btns;
	private Array<ImageTextButton> act2Btns;
	private Array<ImageTextButton> act3Btns;
	private Array<ImageTextButton> act4Btns;
	private Array<ImageTextButton> act5Btns;
	
	private Array<Act> acts;
	private Act currentAct;

	public SceneSelectUI()
	{
		super("", AssetLoader.SELECT_SKIN, "scene-select-book");
		this.debugAll();
		
		act1Btns = new Array<ImageTextButton>();
		populateButtonArray(act1Btns, 7);
		
		act2Btns = new Array<ImageTextButton>();
		populateButtonArray(act2Btns, 4);
		
		act3Btns = new Array<ImageTextButton>();
		populateButtonArray(act3Btns, 6);
		
		act4Btns = new Array<ImageTextButton>();
		populateButtonArray(act4Btns, 3);
		
		act5Btns = new Array<ImageTextButton>();
		populateButtonArray(act5Btns, 11);
		
		acts = new Array<Act>();
		acts.add(Act.ACT1);
		acts.add(Act.ACT2);
		acts.add(Act.ACT3);
		acts.add(Act.ACT4);
		acts.add(Act.ACT5);
		
		currentAct = acts.first();
		
		currentActLabel = new Label(currentAct.getLabelText(), AssetLoader.SELECT_SKIN, "current-act-label");
		
		this.updateSceneSelectionUI(currentAct);
		this.pack();
	}
	
	private void populateButtonArray(Array<ImageTextButton> buttonArray, int numOfScenes)
	{
		for(int i = 1; i <= numOfScenes; i++)
		{
			String sceneTitle = "Scene " + i;
			ImageTextButton newButton = new ImageTextButton(sceneTitle, AssetLoader.SELECT_SKIN, "scene-button");
			buttonArray.add(newButton);
		}
	}
	
	private void updateSceneSelectionUI(Act act)
	{
		this.clear();
		currentActLabel.setText(act.getLabelText());
		Array<ImageTextButton> buttons = getButtonsForAct(act.actInt);
		
		this.add(currentActLabel).padRight(getWidth() + getWidth() / 2);
		this.add();
		this.row();
		
		int colIndex = 0;
		
		for(int i = 0; i < buttons.size; i++)
		{
			
			this.add(buttons.get(i)).padRight(this.getWidth());
			if(colIndex % 2 == 1)
			{
				this.row();
			}
			
			colIndex++;
			
		}
		
	}
	
	public Array<ImageTextButton> getButtonsForAct(int act)
	{
		switch(act)
		{
		case 1:
			return act1Btns;
		case 2:
			return act2Btns;
		case 3:
			return act3Btns;
		case 4:
			return act4Btns;
		case 5:
			return act5Btns;
			default:
				return null;
		}
	}
	
	public static enum Act
	{
		ACT1("Act 1", 1),
		ACT2("Act 2", 2),
		ACT3("Act 3", 3),
		ACT4("Act 4", 4),
		ACT5("Act 5", 5);
		
		private String labelText;
		private int actInt;
		//private Array<ImageTextButton> buttons;
		
		//private Act(String labelText, Array<ImageTextButton> buttonArray)
		private Act(String labelText, int actInt)
		{
			this.labelText = labelText;
			this.actInt = actInt;
			//this.buttons = buttonArray;
		}
		
		public String getLabelText()
		{
			return this.labelText;
		}
		
		public int getActInt()
		{
			return actInt;
		}
		
		//public Array<ImageTextButton> getButtonArray()
		//{
		//	return this.buttons;
		//}
	}

}
