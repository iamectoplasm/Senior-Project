package com.seniorproject.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.seniorproject.game.AssetLoader;
import com.seniorproject.game.SeniorProject;

public class SceneSelectScreen implements Screen
{
	private SeniorProject game;
	private Stage stage;
	
	public SceneSelectScreen(SeniorProject game)
	{
		this.game = game;
		this.stage = new Stage();
		
		Table table = new Table(AssetLoader.INTRO_SKIN);
		table.setFillParent(true);
		table.setBackground(AssetLoader.INTRO_SKIN.getDrawable("wood-bg"));
		
		TextButton act1Scene1Btn = new TextButton("Act 1 Scene 1", AssetLoader.INTRO_SKIN, "scene-selection-button");
		TextButton act1Scene2Btn = new TextButton("Act 1 Scene 2", AssetLoader.INTRO_SKIN, "scene-selection-button");
		TextButton act1Scene3Btn = new TextButton("Act 1 Scene 3", AssetLoader.INTRO_SKIN, "scene-selection-button");
		TextButton act1Scene4Btn = new TextButton("Act 1 Scene 4", AssetLoader.INTRO_SKIN, "scene-selection-button");
		TextButton act1Scene5Btn = new TextButton("Act 1 Scene 5", AssetLoader.INTRO_SKIN, "scene-selection-button");
		TextButton act1Scene6Btn = new TextButton("Act 1 Scene 6", AssetLoader.INTRO_SKIN, "scene-selection-button");
		TextButton act1Scene7Btn = new TextButton("Act 1 Scene 7", AssetLoader.INTRO_SKIN, "scene-selection-button");
		
		table.add(act1Scene1Btn).padRight(20);
		table.add(act1Scene2Btn);
		table.row();
		table.add(act1Scene3Btn).padRight(20);
		table.add(act1Scene4Btn);
		table.row();
		table.add(act1Scene5Btn).padRight(20);
		table.add(act1Scene6Btn);
		table.row();
		
		table.add(act1Scene7Btn).padRight(20);
		table.row();
		
		stage.addActor(table);
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
