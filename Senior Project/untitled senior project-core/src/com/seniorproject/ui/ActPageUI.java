package com.seniorproject.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.seniorproject.assetmanagement.AssetLoader;
import com.seniorproject.enums.SceneFiles;
import com.seniorproject.game.SeniorProject;
import com.seniorproject.game.SeniorProject.GameMode;
import com.seniorproject.game.SeniorProject.ScreenType;
import com.seniorproject.ui.SceneSelectUI.Act;

public class ActPageUI extends Table
{
	private Act act;
	
	private Label actLabel;
	
	public ActPageUI(Act act, Array<ImageTextButton> buttons)
	{
		this.setSize(480, 352);
		
		setupButtonListeners(buttons);
		
		this.actLabel = new Label(act.getLabelText(), AssetLoader.SELECT_SKIN, "current-act-label");
		this.add(actLabel).padLeft(32).padBottom(16);
		this.add();
		
		this.row();
		
		int colIndex = 0;
		
		for(int i = 0; i < buttons.size; i++)
		{
			//this.add(buttons.get(i)).padRight(this.getWidth());
			this.add(buttons.get(i));
			if(colIndex % 2 == 1)
			{
				this.row();
			}
			colIndex++;
		}
		
		this.pack();
	}
	
	private void setupButtonListeners(Array<ImageTextButton> buttons)
	{
		for(int i = 0; i < buttons.size; i++)
		{
			final ImageTextButton currentButton = buttons.get(i);
			currentButton.addListener(new ClickListener()
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
					String sceneFilesString = currentButton.getName();
					SeniorProject.performanceScreen.getSceneManager().setMode(GameMode.STUDY_MODE, SceneFiles.valueOf(sceneFilesString));
					SeniorProject game = (SeniorProject) Gdx.app.getApplicationListener();
					game.setScreen(game.getScreenType(ScreenType.PERFORMANCE_SCREEN));
				}
			});
		}
	}
	
	public Act getAct()
	{
		return act;
	}
}
