package com.seniorproject.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.seniorproject.assetmanagement.AssetLoader;
import com.seniorproject.game.SceneManager;
import com.seniorproject.game.SeniorProject;
import com.seniorproject.game.SeniorProject.GameMode;
import com.seniorproject.game.SeniorProject.ScreenType;

public class MainMenuScreen implements Screen
{
	public static final String TAG = MainMenuScreen.class.getSimpleName();
	
	private SeniorProject game;
	private Stage stage;
	private ScreenTransitionActor transitionActor;
	
	public MainMenuScreen(final SeniorProject game)
	{
		this.game = game;
		this.stage = new Stage();
		
		Table table = new Table(AssetLoader.MAIN_MENU_SKIN);
		table.setFillParent(true);
		table.setBackground("main-menu-bg-1");
		
		TextButton storyModeButton = new TextButton("Story Mode", AssetLoader.MAIN_MENU_SKIN, "scene-selection-button");
		TextButton studyModeButton = new TextButton("Study Mode", AssetLoader.MAIN_MENU_SKIN, "scene-selection-button");
		TextButton demoModeButton = new TextButton("Demo Mode", AssetLoader.MAIN_MENU_SKIN, "scene-selection-button");
		
		table.add(storyModeButton).spaceBottom(10).row();
		table.add(studyModeButton).spaceBottom(10).row();
		table.add(demoModeButton).spaceBottom(10).row();
		
		stage.addActor(table);
		
		this.transitionActor = new ScreenTransitionActor();
		stage.addActor(transitionActor);
		
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
				SeniorProject.currentGameMode = GameMode.STORY_MODE;
				
				Action fade = Actions.addAction(ScreenTransitionAction.transition(ScreenTransitionAction.ScreenTransitionType.FADE_OUT	, 0.5f), transitionActor);
				
				stage.addAction(Actions.sequence(fade, Actions.delay(0.5f), new RunnableAction()
				{
					@Override
					public void run()
					{
						game.setScreen(game.getScreenType(ScreenType.SCENE_INTRO_SCREEN));
						
						SceneManager sm = SeniorProject.performanceScreen.getSceneManager();
						sm.setMode(SeniorProject.currentGameMode, null);
						SeniorProject.sceneIntroScreen.updateToNextScene(sm.getCurrentScene().getScriptConfigFile());
					}
				}));
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
				SeniorProject.currentGameMode = GameMode.STUDY_MODE;
				game.setScreen(game.getScreenType(ScreenType.SCENE_SELECT_SCREEN));
			}
		});
		
		//Listeners
		demoModeButton.addListener(new ClickListener()
		{
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{
				return true;
			}
					
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button)
			{
				SeniorProject.currentGameMode = GameMode.DEMO_MODE;
				
				Action fade = Actions.addAction(ScreenTransitionAction.transition(ScreenTransitionAction.ScreenTransitionType.FADE_OUT	, 0.5f), transitionActor);
				
				stage.addAction(Actions.sequence(fade, Actions.delay(0.5f), new RunnableAction()
				{
					@Override
					public void run()
					{
						game.setScreen(game.getScreenType(ScreenType.SCENE_INTRO_SCREEN));
						
						SceneManager sm = SeniorProject.performanceScreen.getSceneManager();
						sm.setMode(SeniorProject.currentGameMode, null);
						SeniorProject.sceneIntroScreen.updateToNextScene(sm.getCurrentScene().getScriptConfigFile());
					}
				}));
			}
		});
	}
	
	@Override
	public void show()
	{
		transitionActor.setVisible(true);
		stage.addAction(Actions.sequence
				(Actions.addAction
				(ScreenTransitionAction.transition(ScreenTransitionAction.ScreenTransitionType.FADE_IN, 0.5f), 
				transitionActor)));
		
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
		/*
		fadeOverlay.clear();
		/*
		 * 10/25/20 hacking in fade overlay to get screen fades up & running
		 */
		/*
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
