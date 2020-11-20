package com.seniorproject.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;
import com.seniorproject.configs.ScriptConfig;
import com.seniorproject.game.AssetLoader;
import com.seniorproject.game.FadeOverlay;
import com.seniorproject.game.SeniorProject;
import com.seniorproject.game.SeniorProject.ScreenType;

public class SceneIntroScreen implements Screen
{
	private static final String TAG = SceneIntroScreen.class.getSimpleName();
	
	private Stage stage;
	
	private Image backgroundImage;
	
	private Label sceneTitleLabel;
	
	private Label sceneTab;
	private Label settingTab;
	private Label sceneLabel;
	private Label settingLabel;
	
	
	/*
	 * 10/25/20 hacking in fade overlay to get screen fades up & running
	 */
	private Image fadeOverlay;
	/*
	 * end of bad code
	 */
	
	public SceneIntroScreen(final SeniorProject game)
	{
		Gdx.app.debug(TAG, "SceneIntroScreen being created");
		
		this.stage = new Stage();
		
		backgroundImage = new Image(AssetLoader.SCENE_INTRO_SKIN.getDrawable("dark-heath-exterior"));
		stage.addActor(backgroundImage);
		
		sceneTitleLabel = new Label("Act 1 Scene 1", AssetLoader.SCENE_INTRO_SKIN, "scene-title");
		sceneTitleLabel.setX((stage.getWidth() / 2) - (sceneTitleLabel.getWidth() / 2));
		sceneTitleLabel.setY(stage.getHeight() - sceneTitleLabel.getHeight() - 64);
		stage.addActor(sceneTitleLabel);
		
		sceneLabel = new Label("", AssetLoader.SCENE_INTRO_SKIN, "scene-description");
		sceneLabel.setWidth(416);
		sceneLabel.setHeight(128);
		sceneLabel.setWrap(true);
		sceneLabel.setX((stage.getWidth() / 2) - (sceneLabel.getWidth() / 2));
		sceneLabel.setY(288);
		sceneLabel.setAlignment(Align.center, Align.center);
		
		sceneTab = new Label("The scene:", AssetLoader.SCENE_INTRO_SKIN, "tab-label");
		sceneTab.setX(sceneLabel.getX() + 32);
		sceneTab.setY(sceneLabel.getTop() - 16);
		
		settingLabel = new Label("", AssetLoader.SCENE_INTRO_SKIN, "scene-description");
		settingLabel.setWidth(416);
		settingLabel.setHeight(128);
		settingLabel.setWrap(true);
		settingLabel.setX((stage.getWidth() / 2) - (settingLabel.getWidth() / 2));
		settingLabel.setY(96);
		settingLabel.setAlignment(Align.center);
		
		settingTab = new Label("The setting:", AssetLoader.SCENE_INTRO_SKIN, "tab-label");
		settingTab.setX(settingLabel.getX() + 32);
		settingTab.setY(settingLabel.getTop() - 16);
		
		stage.addActor(sceneTab);
		stage.addActor(settingTab);
		stage.addActor(settingLabel);
		stage.addActor(sceneLabel);
		
		/*
		 * 10/25/20 hacking in fade overlay to get screen fades up & running
		 */
		this.fadeOverlay = FadeOverlay.getInstance().getOverlay();
		stage.addActor(fadeOverlay);
		/*
		 * end of bad code
		 */
		
		stage.addListener(new InputListener()
		{
			@Override
			public boolean keyDown(InputEvent event, int keycode)
			{
				if(keycode == Keys.SPACE)
				{
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
							//game.setScreen(game.getScreenType(ScreenType.MAIN_MENU_SCREEN));
							game.setScreen(game.getScreenType(ScreenType.PERFORMANCE_SCREEN));
							
							fadeOverlay.clear();
						}
					}));
					
					stage.getRoot().addAction(changeScreen);
					/*
					 * end of bad code
					 */
				}
			return true;
			}
		});
		
		//Json tempJson = new Json();
		//ScriptConfig first = tempJson.fromJson(ScriptConfig.class, Gdx.files.internal("scenes/act 1/scripts/1-1 script.json").read());
		//updateToNextScene(first);
	}
	
	public void updateToNextScene(ScriptConfig config)
	{
		Gdx.app.debug(TAG, "config being sent is " + config.getSceneIntro().getTitle());
		Gdx.app.debug(TAG, "config.getSceneIntro().getBackground() returns " + config.getSceneIntro().getBackground());
		
		backgroundImage.setDrawable(AssetLoader.SCENE_INTRO_SKIN.getDrawable(config.getSceneIntro().getBackground()));

		sceneTitleLabel.setText(config.getSceneIntro().getTitle());
		
		
		String sceneDescription = "";
		
		for(int i = 0; i < config.getSceneIntro().getDescriptionArray().size; i++)
		{
			sceneDescription += config.getSceneIntro().getDescriptionArray().get(i) + "\n";
		}
		sceneLabel.setText(sceneDescription);
		
		settingLabel.setText(config.getSceneIntro().getSetting());
	}

	@Override
	public void show()
	{
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
