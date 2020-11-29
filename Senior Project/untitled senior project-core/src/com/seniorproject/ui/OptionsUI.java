package com.seniorproject.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.seniorproject.assetmanagement.AssetLoader;
import com.seniorproject.game.SeniorProject;

public class OptionsUI extends Window
{
	private static final String TAG = OptionsUI.class.getSimpleName();

	public OptionsUI()
	{
		super("", AssetLoader.OPTIONS_SKIN);
		
		Label optionsTitle = new Label("Options", AssetLoader.OPTIONS_SKIN, "options-label");
		optionsTitle.setAlignment(Align.center);
		
		final ImageTextButton returnToScene = new ImageTextButton("Return to Scene", AssetLoader.OPTIONS_SKIN, "options-button");
		final ImageTextButton resetScene = new ImageTextButton("Reset Scene", AssetLoader.OPTIONS_SKIN, "options-button");
		final ImageTextButton mainMenu = new ImageTextButton("Main Menu", AssetLoader.OPTIONS_SKIN, "options-button");
		
		this.add(optionsTitle);
		this.row();
		this.add(mainMenu).padTop(16);
		this.row();
		this.add(returnToScene).padTop(8);
		this.row();
		this.add(resetScene).padTop(8);
		this.row();
		
		this.getCell(resetScene).setActorWidth(returnToScene.getWidth());
		
		this.pack();
		
		returnToScene.addListener(new ClickListener()
		{
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{
				return true;
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button)
			{
				returnToScene.getParent().setVisible(false);
			}
		});
		
		mainMenu.addListener(new ClickListener()
		{
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{
				return true;
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button)
			{
				SeniorProject.performanceScreen.getSceneManager().returnToMainMenu();
				//SeniorProject.performanceScreen.fadeToNewScreen(ScreenType.MAIN_MENU_SCREEN);
			}
		});
	}

}
