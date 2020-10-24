package com.seniorproject.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;
import com.seniorproject.game.AssetLoader;
import com.seniorproject.game.Scene;
import com.seniorproject.scenemeta.StudyConfig;

public class StudyUI extends Window
{
	private static final String TAG = StudyUI.class.getSimpleName();
	
	private Scene currentScene;
	
	//private ImageButton breakdownButton;
	//private ImageButton translationButton;
	//private ImageButton analysisButton;
	//private ImageButton fullTextButton;
	//private ButtonGroup<ImageButton> tabButtons;
	
	private ImageTextButton breakdownButton;
	private ImageTextButton translationButton;
	private ImageTextButton analysisButton;
	private ImageTextButton fullTextButton;
	private ButtonGroup<ImageTextButton> tabButtons;
	
	private Window studyWindow;
	private Label titleLabel;
	private Label studyInfoLabel;

	public StudyUI(Scene scene)
	{
		super("", AssetLoader.DASHBOARD_SKIN);
		//this.debugAll();
		
		this.currentScene = scene;
		
		breakdownButton = new ImageTextButton("Scene\nBreakdown", AssetLoader.DASHBOARD_SKIN, "tab-button");
		translationButton = new ImageTextButton("Translation", AssetLoader.DASHBOARD_SKIN, "tab-button");
		analysisButton = new ImageTextButton("Analysis", AssetLoader.DASHBOARD_SKIN, "tab-button");
		fullTextButton = new ImageTextButton("Text", AssetLoader.DASHBOARD_SKIN, "tab-button");
		
		translationButton.setWidth(breakdownButton.getWidth());
		analysisButton.setWidth(breakdownButton.getWidth());
		fullTextButton.setWidth(breakdownButton.getWidth());
		
		tabButtons = new ButtonGroup<ImageTextButton>();
		tabButtons.add(breakdownButton);
		tabButtons.add(translationButton);
		tabButtons.add(analysisButton);
		tabButtons.add(fullTextButton);
		
		tabButtons.setMaxCheckCount(1);
		tabButtons.setUncheckLast(true);
		
		//this.add().padRight(10);
		this.add().padRight(20);
		
		this.add(breakdownButton, translationButton, analysisButton, fullTextButton);
		this.getCell(breakdownButton).expand().fill();
		this.getCell(translationButton).setActorWidth(breakdownButton.getWidth());
		this.getCell(translationButton).expand().fill();
		this.getCell(analysisButton).expand().fill();
		this.getCell(analysisButton).setActorWidth(breakdownButton.getWidth());
		this.getCell(fullTextButton).expand().fill();
		this.getCell(fullTextButton).setActorWidth(breakdownButton.getWidth());
		
		//this.add().padRight(10);
		this.add().padRight(20);
		
		this.setupTabButtonListeners();
		
		Gdx.app.debug(TAG, "dashboardUI width: " + this.getWidth());
		
		this.row();
		
		//studyWindow = new Window("", AssetLoader.DASHBOARD_SKIN, "dashboard");
		studyWindow = new Window("", AssetLoader.DASHBOARD_SKIN, "wood-dashboard");
		
		titleLabel = new Label("", AssetLoader.DASHBOARD_SKIN, "title-label");
		titleLabel.setText("Scene Breakdown");
		
		studyWindow.add(titleLabel).pad(16, 16, 8, 8).align(Align.left);
		studyWindow.row();
		
		//infoLabel = new Label("", AssetLoader.DASHBOARD_SKIN, "textbox");
		//infoLabel.setWrap(true);
		//infoLabel.setText(currentScene.getStudyConfigs().get("breakdown").getStudyText().first());
		studyInfoLabel = new Label("", AssetLoader.DASHBOARD_SKIN);
		studyInfoLabel.setWrap(true);
		studyInfoLabel.setFontScale(1f);
		
		// Set up the scene breakdown
		StudyConfig breakdown = currentScene.getStudyConfigs().get("breakdown");
		for(int i = 0; i < breakdown.getStudyText().size; i++)
		{
			studyInfoLabel.setText(studyInfoLabel.getText() + "\n" + breakdown.getStudyText().get(i));
		}
		
		ScrollPane textScroll = new ScrollPane(studyInfoLabel, AssetLoader.DASHBOARD_SKIN, "text-display");
		textScroll.setScrollbarsVisible(true);
		
		studyWindow.add(textScroll).height(250).pad(16, 16, 16, 16);
		studyWindow.getCell(textScroll).expand().fill();
		
		this.add(studyWindow);
		this.getCell(studyWindow).colspan(6);
		this.getCell(studyWindow).expand().fill();
		this.getCell(studyWindow).setActorHeight(200);
		this.pack();
	}
	
	private void setupTabButtonListeners()
	{
		breakdownButton.addListener(new ClickListener()
		{
			@Override
			public boolean touchDown(InputEvent event,
                    float x,
                    float y,
                    int pointer,
                    int button)
			{
				studyInfoLabel.setText("");
				return true;
			}
			
			@Override
			public void touchUp(InputEvent event,
                    float x,
                    float y,
                    int pointer,
                    int button)
			{
				StudyConfig breakdown = currentScene.getStudyConfigs().get("breakdown");
				
				for(int i = 0; i < breakdown.getStudyText().size; i++)
				{
					studyInfoLabel.setText(studyInfoLabel.getText() + "\n" + breakdown.getStudyText().get(i));
				}
			}
		});
		
		translationButton.addListener(new ClickListener()
		{
			@Override
			public boolean touchDown(InputEvent event,
                    float x,
                    float y,
                    int pointer,
                    int button)
			{
				studyInfoLabel.setText("");
				return true;
			}
			
			@Override
			public void touchUp(InputEvent event,
                    float x,
                    float y,
                    int pointer,
                    int button)
			{
				StudyConfig translation = currentScene.getStudyConfigs().get("translation");
				
				for(int i = 0; i < translation.getStudyText().size; i++)
				{
					studyInfoLabel.setText(studyInfoLabel.getText() + "\n" + translation.getStudyText().get(i));
				}
			}
		});
		
		analysisButton.addListener(new ClickListener()
		{
			@Override
			public boolean touchDown(InputEvent event,
                    float x,
                    float y,
                    int pointer,
                    int button)
			{
				studyInfoLabel.setText("");
				return true;
			}
			
			@Override
			public void touchUp(InputEvent event,
                    float x,
                    float y,
                    int pointer,
                    int button)
			{
				StudyConfig analysis = currentScene.getStudyConfigs().get("analysis");
				
				for(int i = 0; i < analysis.getStudyText().size; i++)
				{
					studyInfoLabel.setText(studyInfoLabel.getText() + "\n" + analysis.getStudyText().get(i));
				}
			}
		});
		
		fullTextButton.addListener(new ClickListener()
		{
			@Override
			public boolean touchDown(InputEvent event,
                    float x,
                    float y,
                    int pointer,
                    int button)
			{
				studyInfoLabel.setText("");
				return true;
			}
			
			@Override
			public void touchUp(InputEvent event,
                    float x,
                    float y,
                    int pointer,
                    int button)
			{
				StudyConfig fullText = currentScene.getStudyConfigs().get("fulltext");
				
				for(int i = 0; i < fullText.getStudyText().size; i++)
				{
					studyInfoLabel.setText(studyInfoLabel.getText() + "\n" + fullText.getStudyText().get(i));
				}
			}
		});
	}

}
