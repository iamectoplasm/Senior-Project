package com.seniorproject.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.seniorproject.game.AssetLoader;
import com.seniorproject.game.FadeOverlay;
import com.seniorproject.game.SceneManager;
import com.seniorproject.game.SeniorProject;
import com.seniorproject.game.SeniorProject.ScreenType;

public class MainMenuScreen implements Screen
{
	private SeniorProject game;
	private Stage stage;
	
	/*
	 * 10/25/20 hacking in fade overlay to get screen fades up & running
	 */
	private Image fadeOverlay;
	/*
	 * end of bad code
	 */
	
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
		
		/*
		 * 10/25/20 hacking in fade overlay to get screen fades up & running
		 */
		AssetLoader.loadTextureAsset("backgrounds/transition fade.png");
		this.fadeOverlay = new Image(AssetLoader.getTextureAsset("backgrounds/transition fade.png"));
		fadeOverlay.setSize(800, 600);
		fadeOverlay.setTouchable(Touchable.disabled);
		//this.fadeOverlay = FadeOverlay.getInstance().getOverlay();
		//fadeOverlay.setVisible(true);
		stage.addActor(fadeOverlay);
		/*
		 * end of bad code
		 */
		
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
				//Screen newScreen = game.getScreenType(ScreenType.PERFORMANCE_SCREEN);
				//game.setScreen(newScreen);
				
				/*
				 * 10/25/20 hacking in fade overlay to get screen fades up & running
				 */
				SequenceAction changeScreen = new SequenceAction();
				Action fadeIn = Actions.fadeIn(0.5f);
				fadeIn.setActor(fadeOverlay);
				
				changeScreen.addAction(fadeIn);
				changeScreen.addAction(Actions.run(new Runnable()
				{
					@Override
					public void run()
					{
						//game.setScreen(game.getScreenType(ScreenType.PERFORMANCE_SCREEN));
						game.setScreen(game.getScreenType(ScreenType.SCENE_INTRO_SCREEN));
						SceneManager sm = SeniorProject.performanceScreen.getSceneManager();
						SeniorProject.sceneIntroScreen.updateToNextScene(sm.getCurrentScene().getScriptConfigFile());
					}
				}));
				
				stage.getRoot().addAction(changeScreen);
				fadeOverlay.clear();
				/*
				 * end of bad code
				 */
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
				game.setScreen(game.getScreenType(ScreenType.SCENE_SELECT_SCREEN));
			}
		});
	}
	
	@Override
	public void show()
	{
		fadeOverlay.clear();
		/*
		 * 10/25/20 hacking in fade overlay to get screen fades up & running
		 */
		fadeOverlay.addAction(Actions.fadeOut(0.5f));
		/*
		 * end of bad code
		 */
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
		fadeOverlay.clear();
		/*
		 * 10/25/20 hacking in fade overlay to get screen fades up & running
		 */
		fadeOverlay.addAction(Actions.fadeIn(.5f));
		/*
		 * end of bad code
		 */
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void dispose()
	{
		stage.dispose();
	}

}
