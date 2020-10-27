package com.seniorproject.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.seniorproject.game.SeniorProject.ScreenType;

public class TransitionManager implements Screen
{
	private static TransitionManager transitionManager;
	
	public static TransitionManager getInstance()
	{
		if(transitionManager == null)
		{
			return new TransitionManager();
		}
		else
			return transitionManager;
	}
	
	private Image fadeOverlay;
	private SeniorProject game;
	
	private TransitionManager()
	{
		this.game = (SeniorProject) Gdx.app.getApplicationListener();
		
		String overlayFilePath = "backgrounds/transition fade.png";
		
		AssetLoader.loadTextureAsset(overlayFilePath);
		this.fadeOverlay = new Image(AssetLoader.getTextureAsset(overlayFilePath));
	}
	
	public void fadeIntoScreen(Stage stage)
	{
		stage.getRoot().setColor(Color.BLACK);
		stage.getRoot().addAction(Actions.fadeOut(0.5f));
		
		//SequenceAction fadeIn = new SequenceAction();
		//Action fade = Actions.fadeOut(0.5f);
		
		//fadeIn.addAction(fade);
		//fadeIn.addAction(Actions.run(new Runnable()
		//{
		//	@Override
		//	public void run()
		//	{
		//		//fadeOverlay.remove();
		//	}
		//}));
		
		//stage.getRoot().addAction(fadeIn);
	}
	
	public void fadeOutScreen(Stage stage)
	{
		stage.getRoot().setColor(Color.BLACK);
		stage.getRoot().addAction(Actions.fadeIn(0.5f));
		
		//fadeOverlay.setSize(stage.getWidth(), stage.getHeight());
		//stage.addActor(fadeOverlay);
		
		//SequenceAction fadeIn = new SequenceAction();
		//Action fade = Actions.fadeIn(0.5f);
		//fadeIn.setActor(fadeOverlay);
		
		//fadeIn.addAction(fade);
		//fadeIn.addAction(Actions.run(new Runnable()
		//{
		//	@Override
		//	public void run()
		//	{
		//		fadeOverlay.remove();
		//	}
		//}));
		
		//stage.getRoot().addAction(fadeIn);
	}
	
	public void runScreenChange(Stage stage, final ScreenType toScreen)
	{
		SequenceAction changeScreen = new SequenceAction();
		Action fadeIn = Actions.fadeIn(0.5f);
		fadeIn.setActor(stage.getRoot());
		
		changeScreen.addAction(fadeIn);
		changeScreen.addAction(Actions.run(new Runnable()
		{
			@Override
			public void run()
			{
				game.setScreen(game.getScreenType(toScreen));
			}
		}));
		
		stage.getRoot().setColor(0, 0, 0, 0);
		stage.getRoot().addAction(changeScreen);
		
		/*
		fadeOverlay.setSize(stage.getWidth(), stage.getHeight());
		stage.addActor(fadeOverlay);
		fadeOverlay.toFront();
		
		SequenceAction changeScreen = new SequenceAction();
		Action fadeIn = Actions.fadeIn(0.5f);
		fadeIn.setActor(fadeOverlay);
		
		changeScreen.addAction(fadeIn);
		changeScreen.addAction(Actions.run(new Runnable()
		{
			@Override
			public void run()
			{
				game.setScreen(game.getScreenType(toScreen));
			}
		}));
		
		stage.getRoot().addAction(changeScreen);
		*/
	}
	
	public void fadeTransition(Screen currentScreen, Screen nextScreen)
	{
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
