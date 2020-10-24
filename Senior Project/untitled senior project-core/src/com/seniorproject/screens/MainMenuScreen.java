package com.seniorproject.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.seniorproject.game.AssetLoader;
import com.seniorproject.game.SeniorProject;
import com.seniorproject.game.SeniorProject.ScreenType;

public class MainMenuScreen implements Screen
{
	private SeniorProject game;
	private Stage stage;
	
	public MainMenuScreen(final SeniorProject game)
	{
		this.game = game;
		this.stage = new Stage();
		
		Table table = new Table(AssetLoader.INTRO_SKIN);
		table.setFillParent(true);
		table.setBackground("intro-bg-temp");
		
		TextButton storyModeButton = new TextButton("Story Mode", AssetLoader.INTRO_SKIN, "scene-selection-button");
		TextButton studyModeButton = new TextButton("Study Mode", AssetLoader.INTRO_SKIN, "scene-selection-button");
		
		table.add(storyModeButton).spaceBottom(10).row();
		table.add(studyModeButton).spaceBottom(10).row();
		
		stage.addActor(table);
		
		//Listeners
		storyModeButton.addListener(new ClickListener()
		{
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{
				return true;
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button)
			{
				game.setScreen(game.getScreenType(ScreenType.Performance));
			}
		});
		
		//Listeners
		studyModeButton.addListener(new ClickListener()
		{
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{
				return true;
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button)
			{
				game.setScreen(game.getScreenType(ScreenType.SceneSelectScreen));
			}
		});
	}
	
	@Override
	public void show()
	{
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta)
	{
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide()
	{
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void dispose()
	{
		stage.dispose();
	}

}
