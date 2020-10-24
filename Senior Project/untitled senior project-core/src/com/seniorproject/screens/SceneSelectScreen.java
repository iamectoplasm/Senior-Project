package com.seniorproject.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.seniorproject.game.AssetLoader;
import com.seniorproject.game.SeniorProject;
import com.seniorproject.game.SeniorProject.ScreenType;
import com.seniorproject.scenemeta.StudyConfig;
import com.seniorproject.ui.SceneSelectUI;

public class SceneSelectScreen implements Screen
{
	private SeniorProject game;
	private Stage stage;
	
	public SceneSelectScreen(SeniorProject game)
	{
		this.game = game;
		this.stage = new Stage();
		Image bgImage = new Image(AssetLoader.SELECT_SKIN, "glamis-palette-cropped");
		bgImage.setScale(0.5f);
		stage.addActor(bgImage);
		
		ImageButton backButton = new ImageButton(AssetLoader.SELECT_SKIN, "exit");
		backButton.setScale(4f);
		backButton.setX(16);
		backButton.setY(stage.getHeight() - backButton.getHeight() - 16);
		addBackButtonListener(backButton);
		
		stage.addActor(backButton);
		
		SceneSelectUI sceneSelect = new SceneSelectUI();
		sceneSelect.setX((stage.getWidth() / 2) - (sceneSelect.getWidth() / 2));
		sceneSelect.setY(32);
		stage.addActor(sceneSelect);
		
		
		//stage.addActor(table);
	}
	
	private void addBackButtonListener(ImageButton button)
	{
		button.addListener(new ClickListener()
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
				game.setScreen(game.getScreenType(ScreenType.IntroScreen));
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
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose()
	{
		// TODO Auto-generated method stub
		
	}

}
