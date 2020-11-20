package com.seniorproject.ui;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.seniorproject.enums.SceneFiles;
import com.seniorproject.game.AssetLoader;
import com.seniorproject.game.Scene;
import com.seniorproject.game.SceneManager;
import com.seniorproject.game.SeniorProject;
import com.seniorproject.game.SeniorProject.ScreenType;

public class PerformanceHUD implements Screen
{
	private static final String TAG = PerformanceHUD.class.getSimpleName();
	
	private Stage stage;
	private Viewport viewport;
	private Camera camera;
	private SceneManager sceneManager;
	
	private DialogueUI dialogueUI;
	private StudyUI studyUI;
	private OptionsUI optionsUI;
	
	private Label currentSceneLabel;
	private Label studyLabel;
	private ImageButton showStudyWindowBtn;
	//private ImageButton hideDashBtn;
	private Button hideStudyWindowBtn;
	
	private ImageButton optionsDisplayButton;
	
	private DialogueUI sharedLineDialogue;
	
	public PerformanceHUD(Camera camera, final SceneManager sceneManager)
	{
		this.camera = camera;
		//this.viewport = new ScreenViewport(this.camera);
		this.viewport = new FitViewport(800, 600, this.camera);
		this.stage = new Stage(viewport);
		this.sceneManager = sceneManager;
		
		//this.currentSceneLabel = new Label(sceneManager.getCurrentScene().getSceneTitle(), AssetLoader.STUDY_SKIN, "title-label");
		this.currentSceneLabel = new Label("Act 0 Scene 0", AssetLoader.STUDY_SKIN, "title-label");
		currentSceneLabel.setPosition(16, this.getStage().getHeight() - currentSceneLabel.getHeight() - 16);
		stage.addActor(currentSceneLabel);
		
		this.dialogueUI = new DialogueUI();
		//this.dialogueUI.setVisible(false);
		//this.dialogueUI.setMovable(true);
		//this.dialogueUI.setResizable(true);
		this.dialogueUI.setX(stage.getWidth() - dialogueUI.getWidth());
		this.dialogueUI.setTextDrawInProgress(true);
		
		//dialogueUI.setNewSpeaker(sceneManager.getCurrentActor());
		
		stage.addActor(dialogueUI);
		//stage.addActor(sharedLineDialogue);
		
		stage.addListener(new InputListener()
		{
			@Override
			public boolean keyDown(InputEvent event, int keycode)
			{
				if(keycode == Keys.SPACE)
				{
					if(!dialogueUI.isTextDrawInProgress())
					{
						sceneManager.stepForward();
							
						if(sceneManager.actorHasChanged())
						{
							dialogueUI.setNewSpeaker(sceneManager.getCurrentActor());
						}
							
						dialogueUI.setTextDrawInProgress(true);
					}
				}
			return true;
			}
		});
		
		this.optionsUI = new OptionsUI();
		optionsUI.setX((stage.getWidth() / 2) - (optionsUI.getWidth() / 2));
		optionsUI.setY((stage.getHeight() / 2) - (optionsUI.getHeight() / 2));
		optionsUI.setVisible(false);
		stage.addActor(optionsUI);
		
		this.optionsDisplayButton = new ImageButton(AssetLoader.OPTIONS_SKIN, "show-options-panel");
		optionsDisplayButton.setX(stage.getWidth() - optionsDisplayButton.getWidth() - 16);
		optionsDisplayButton.setY(stage.getHeight() - optionsDisplayButton.getHeight() - 16);
		stage.addActor(optionsDisplayButton);
		
		optionsDisplayButton.addListener(new ClickListener()
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
				optionsUI.setVisible(true);
			}
		});
		
		studyUI = new StudyUI(sceneManager.getCurrentScene());
		studyUI.setX((this.getStage().getWidth() / 2) - (studyUI.getWidth() / 2));
		studyUI.setY((this.getStage().getHeight() / 2) - (studyUI.getHeight() / 2));
		studyUI.setVisible(false);
		stage.addActor(studyUI);
		
		studyLabel = new Label("Study Dashboard", AssetLoader.STUDY_SKIN, "study-dashboard-overlay-label");
		studyLabel.setAlignment(Align.center);
		studyLabel.setAlignment(Align.top);
		studyLabel.setX((this.getStage().getWidth() / 2) - (studyLabel.getWidth() / 2));
		studyLabel.setY(this.getStage().getHeight() - studyLabel.getHeight());
		//dashboardLabel.setY(Align.top);
		
		showStudyWindowBtn = new ImageButton(AssetLoader.STUDY_SKIN, "pull-down-button");
		showStudyWindowBtn.setX((this.stage.getWidth() / 2) - (showStudyWindowBtn.getWidth() / 2));
		showStudyWindowBtn.setY(this.getStage().getHeight() - showStudyWindowBtn.getHeight() - studyLabel.getHeight());
		
		//hideDashBtn = new ImageButton(AssetLoader.STUDY_SKIN, "exit-dash-button");
		//hideDashBtn.setX((this.getStage().getWidth() - hideDashBtn.getWidth()) - 16);
		//hideDashBtn.setY(this.getStage().getHeight() - hideDashBtn.getHeight() - 16);
		hideStudyWindowBtn = new Button(AssetLoader.STUDY_SKIN, "exit-button");
		hideStudyWindowBtn.setX(studyUI.getRight());
		hideStudyWindowBtn.setY(studyUI.getTop());
		hideStudyWindowBtn.setVisible(false);
		
		//showDashBtn = new ImageButton(AssetLoader.DASHBOARD_SKIN, "show-dash-button");
		stage.addActor(studyLabel);
		stage.addActor(showStudyWindowBtn);
		stage.addActor(hideStudyWindowBtn);
		//showDashBtn.setY(stage.getHeight() / 2, Align.center);
		
		this.addShowStudyUIButtonListeners();
	}
	
	private void addShowStudyUIButtonListeners()
	{
		showStudyWindowBtn.addListener(new ClickListener()
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
				studyUI.setVisible(true);
				hideStudyWindowBtn.setVisible(true);
				showStudyWindowBtn.setVisible(false);
			}
		});
		
		hideStudyWindowBtn.addListener(new ClickListener()
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
				studyUI.setVisible(false);
				hideStudyWindowBtn.setVisible(false);
				showStudyWindowBtn.setVisible(true);
			}
		});
	}
	
	public StudyUI getStudyUI()
	{
		return studyUI;
	}
	
	public void updateStudyUIToNewScene(Scene scene)
	{
		currentSceneLabel.setText(scene.getSceneTitle());
		dialogueUI.setNewSpeaker(sceneManager.getCurrentActor());
		studyUI.updateToNewScene(scene);
	}
	
	public Stage getStage()
	{
		return this.stage;
	}

	@Override
	public void show()
	{
	}

	@Override
	public void render(float delta)
	{
		stage.act(delta);
		stage.draw();
		
		if(dialogueUI.isTextDrawInProgress())
		{
			//Gdx.app.debug(TAG, "newest line in currentLinesArray: " + sceneManager.getCurrentLinesArray());
			dialogueUI.updateDialogueUI(delta, sceneManager.getCurrentTextDisplay());
		}
	}

	@Override
	public void resize(int width, int height)
	{
		viewport.update(width, height);
		//stage.getViewport().update(width, height);
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
		stage.dispose();
	}
	
}
