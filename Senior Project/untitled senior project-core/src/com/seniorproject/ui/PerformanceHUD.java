package com.seniorproject.ui;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.seniorproject.game.AssetLoader;
import com.seniorproject.game.SceneManager;

public class PerformanceHUD implements Screen
{
	private static final String TAG = PerformanceHUD.class.getSimpleName();
	
	private Stage stage;
	private Viewport viewport;
	private Camera camera;
	private SceneManager sceneManager;
	
	private DialogueUI dialogueUI;
	private StudyUI dashboardUI;
	
	private ImageButton showDashBtn;
	private ImageButton hideDashBtn;
	
	private MoveToAction moveOnstage;
	private MoveToAction moveOffstage;
	
	private DialogueUI sharedLineDialogue;
	
	public PerformanceHUD(Camera camera, final SceneManager sceneManager)
	{
		this.camera = camera;
		//this.viewport = new ScreenViewport(this.camera);
		this.viewport = new FitViewport(800, 600, this.camera);
		this.stage = new Stage(viewport);
		this.sceneManager = sceneManager;
		
		this.dialogueUI = new DialogueUI();
		this.dialogueUI.setVisible(false);
		this.dialogueUI.setMovable(true);
		this.dialogueUI.setResizable(true);
		this.dialogueUI.setX(stage.getWidth() - dialogueUI.getWidth());
		
		dialogueUI.setNewSpeaker(sceneManager.getCurrentActor());
		//dialogueUI.setDialogue(sceneManager.getCurrentLineText());
		
		//this.sharedLineDialogue = new DialogueUI();
		//this.sharedLineDialogue.setVisible(false);
		//this.sharedLineDialogue.setMovable(true);
		//this.sharedLineDialogue.setResizable(true);
		//this.sharedLineDialogue.setX(stage.getWidth() - dialogueUI.getWidth());
		//this.sharedLineDialogue.setY(dialogueUI.getHeight());
		
		stage.addActor(dialogueUI);
		//stage.addActor(sharedLineDialogue);
		
		stage.addListener(new InputListener()
		{
			@Override
			public boolean keyDown(InputEvent event, int keycode)
			{
				if(keycode == Keys.SPACE)
				{
					if(!dialogueUI.isVisible())
					{
						dialogueUI.setVisible(true);
						dialogueUI.setTextDrawInProgress(true);
					}
					else
					{
						if(!dialogueUI.isTextDrawInProgress())
						{
							//sceneManager.stepForward();
							sceneManager.stepForward();
							
							//if(sceneManager.lineIsShared())
							//{
							//	sharedLineDialogue.setNewSpeaker(dialogueUI.getCurrentActor());
							//	sharedLineDialogue.setCurrentLines(dialogueUI.getCurrentLines());
							//	
							//	dialogueUI.setNewSpeaker(sceneManager.getCurrentActor());
							//	dialogueUI.setTextDrawInProgress(true);
							//	sharedLineDialogue.setVisible(true);
							//}
							//else
							//{
							//	sharedLineDialogue.setVisible(false);
								if(sceneManager.actorHasChanged())
								{
									dialogueUI.setNewSpeaker(sceneManager.getCurrentActor());
								}
								
								dialogueUI.setTextDrawInProgress(true);
							//}
						}
						
						//sceneManager.stepForward();
					
						//dialogueUI.setDialogue(sceneManager.getCurrentLineText());
						//dialogueUI.updateTextDisplay();
					}
				}
			return true;
			}
		});
		
		dashboardUI = new StudyUI(sceneManager.getCurrentScene());
		stage.addActor(dashboardUI);
		dashboardUI.setPosition(-dashboardUI.getWidth(), stage.getHeight() / 2, Align.center);
		//dashboardUI.setPosition(-300f, stage.getHeight() / 2, Align.center);
		dashboardUI.setVisible(false);
		
		moveOnstage = new MoveToAction();
	    moveOnstage.setPosition(0f, stage.getHeight() / 2, Align.center);
	    moveOnstage.setDuration(5f);
	    dashboardUI.addAction(moveOnstage);
		
		showDashBtn = new ImageButton(AssetLoader.DASHBOARD_SKIN, "show-dash-button");
		stage.addActor(showDashBtn);
		showDashBtn.setY(stage.getHeight() / 2, Align.center);
		
		hideDashBtn = new ImageButton(AssetLoader.DASHBOARD_SKIN, "hide-dash-button");
		stage.addActor(hideDashBtn);
		hideDashBtn.setX(dashboardUI.getWidth());
		hideDashBtn.setY(stage.getHeight() / 2, Align.center);
		hideDashBtn.setVisible(false);
		
		this.addDashboardArrowListeners();
	}
	
	private void addDashboardArrowListeners()
	{
		showDashBtn.addListener(new ClickListener()
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
				dashboardUI.setVisible(true);
				hideDashBtn.setVisible(true);
				showDashBtn.setVisible(false);
			}
		});
		
		hideDashBtn.addListener(new ClickListener()
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
				dashboardUI.setVisible(false);
				hideDashBtn.setVisible(false);
				showDashBtn.setVisible(true);
			}
		});
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
