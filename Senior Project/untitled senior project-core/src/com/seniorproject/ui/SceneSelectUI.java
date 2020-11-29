package com.seniorproject.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.seniorproject.assetmanagement.AssetLoader;
import com.seniorproject.enums.SceneFiles;
import com.seniorproject.game.SceneManager;
import com.seniorproject.game.SeniorProject;
import com.seniorproject.game.SeniorProject.GameMode;
import com.seniorproject.game.SeniorProject.ScreenType;

public class SceneSelectUI extends Window
{
	private static final String TAG = SceneSelectUI.class.getSimpleName();
	
	private Label currentActLabel;
	
	private Table actButtonsTable;
	private ImageButton forwardButton;
	private ImageButton backButton;
	
	private Array<ImageTextButton> act1Btns;
	private Array<ImageTextButton> act2Btns;
	private Array<ImageTextButton> act3Btns;
	private Array<ImageTextButton> act4Btns;
	private Array<ImageTextButton> act5Btns;
	
	private Array<Act> acts;
	private Act currentAct;
	
	private Array<ActPageUI> actPages;
	private ActPageUI currentActPage;

	public SceneSelectUI()
	{
		super("", AssetLoader.SELECT_SKIN, "scene-select-book");
		this.debugAll();
		
		act1Btns = new Array<ImageTextButton>();
		populateButtonArray(act1Btns, "ACT1", 7);
		
		act2Btns = new Array<ImageTextButton>();
		populateButtonArray(act2Btns, "ACT2", 4);
		
		act3Btns = new Array<ImageTextButton>();
		populateButtonArray(act3Btns, "ACT3", 6);
		
		act4Btns = new Array<ImageTextButton>();
		populateButtonArray(act4Btns, "ACT4", 3);
		
		act5Btns = new Array<ImageTextButton>();
		populateButtonArray(act5Btns, "ACT5", 11);
		
		acts = new Array<Act>();
		acts.add(Act.ACT1);
		acts.add(Act.ACT2);
		acts.add(Act.ACT3);
		acts.add(Act.ACT4);
		acts.add(Act.ACT5);
		
		currentAct = acts.first();
		
		this.actPages = new Array<ActPageUI>();
		actPages.add(generateActPage(Act.ACT1, act1Btns));
		actPages.add(generateActPage(Act.ACT2, act2Btns));
		actPages.add(generateActPage(Act.ACT3, act3Btns));
		actPages.add(generateActPage(Act.ACT4, act4Btns));
		actPages.add(generateActPage(Act.ACT5, act5Btns));
		
		currentActPage = actPages.first();
		
		this.add(currentActPage);
		//this.add(actPages.get(1));
		this.row();
		
		this.actButtonsTable = new Table();
		this.forwardButton = new ImageButton(AssetLoader.SELECT_SKIN, "act-forward");
		this.backButton = new ImageButton(AssetLoader.SELECT_SKIN, "act-back");
		actButtonsTable.add(backButton);
		actButtonsTable.add(forwardButton);
		
		this.add(actButtonsTable);
		this.updateActButtons(currentAct);
		//this.add(backButton).align(Align.right);
		//this.add(forwardButton).align(Align.left);
		
		//this.updateSceneSelectionUI(currentAct);
		
		this.pack();
	}
	
	private void updateActButtons(final Act act)
	{
		if(currentAct == Act.ACT1)
		{
			backButton.setTouchable(Touchable.disabled);
		}
		else
		{
			backButton.setTouchable(Touchable.enabled);
			backButton.addListener(new ClickListener()
			{
				@Override
				public boolean touchDown(InputEvent event,
	                    float x,
	                    float y,
	                    int pointer,
	                    int button)
				{
					return true;
				}
				
				@Override
				public void touchUp(InputEvent event,
	                    float x,
	                    float y,
	                    int pointer,
	                    int button)
				{
					currentAct = act.getPreviousAct();
					updateCurrentActPage(currentAct);
				}
			});
		}
		
		if(currentAct == Act.ACT5)
		{
			forwardButton.setTouchable(Touchable.disabled);;
		}
		else
		{
			forwardButton.setTouchable(Touchable.enabled);;
			forwardButton.addListener(new ClickListener()
			{
				@Override
				public boolean touchDown(InputEvent event,
	                    float x,
	                    float y,
	                    int pointer,
	                    int button)
				{
					return true;
				}
				
				@Override
				public void touchUp(InputEvent event,
	                    float x,
	                    float y,
	                    int pointer,
	                    int button)
				{
					currentAct = act.getNextAct();
					updateCurrentActPage(currentAct);
				}
			});
		}
	}
	
	private void updateCurrentActPage(Act newAct)
	{
		//this.getCell(currentActPage).clearActor();
		this.getCells().first().setActor(this.getPageForAct(newAct.actInt));
		this.updateActButtons(newAct);
	}
	
	private void populateButtonArray(Array<ImageTextButton> buttonArray, String namePrefix, int numOfScenes)
	{
		for(int i = 1; i <= numOfScenes; i++)
		{
			String sceneTitle = "Scene " + i;
			ImageTextButton newButton = new ImageTextButton(sceneTitle, AssetLoader.SELECT_SKIN, "scene-button");
			
			newButton.setName(namePrefix + "SCENE" + i);
			
			buttonArray.add(newButton);
		}
	}
	
	private ActPageUI generateActPage(Act act, Array<ImageTextButton> buttons)
	{
		ActPageUI newPage = new ActPageUI(act, buttons);
		
		return newPage;
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
	
	public ActPageUI getPageForAct(int act)
	{
		switch(act)
		{
		case 1:
			return actPages.get(0);
		case 2:
			return actPages.get(1);
		case 3:
			return actPages.get(2);
		case 4:
			return actPages.get(3);
		case 5:
			return actPages.get(4);
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
		
		public Act getPreviousAct()
		{
			switch(this)
			{
			case ACT1:
				return null;
			case ACT2:
				return ACT1;
			case ACT3:
				return ACT2;
			case ACT4:
				return ACT3;
			case ACT5:
				return ACT4;
				default:
					return null;
			}
		}
		
		public Act getNextAct()
		{
			switch(this)
			{
			case ACT1:
				return ACT2;
			case ACT2:
				return ACT3;
			case ACT3:
				return ACT4;
			case ACT4:
				return ACT5;
			case ACT5:
				return null;
				default:
					return null;
			}
		}
		
		//public Array<ImageTextButton> getButtonArray()
		//{
		//	return this.buttons;
		//}
	}

}
