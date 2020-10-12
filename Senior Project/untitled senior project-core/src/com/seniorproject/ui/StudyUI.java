package com.seniorproject.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.seniorproject.game.AssetLoader;
import com.seniorproject.game.Scene;
import com.seniorproject.scenemeta.SceneStudy;
import com.seniorproject.scenemeta.StudyConfig;

public class StudyUI extends Window
{
	private static final String TAG = StudyUI.class.getSimpleName();
	
	private Scene currentScene;
	
	private ImageButton breakdownButton;
	private ImageButton translationButton;
	private ImageButton analysisButton;
	private ImageButton fullTextButton;
	private ButtonGroup<ImageButton> tabButtons;
	
	private Window studyWindow;
	private Label infoLabel;

	public StudyUI(Scene scene)
	{
		super("", AssetLoader.DASHBOARD_SKIN);
		this.debugAll();
		
		this.currentScene = scene;
		
		breakdownButton = new ImageButton(AssetLoader.DASHBOARD_SKIN, "tab-button");
		translationButton = new ImageButton(AssetLoader.DASHBOARD_SKIN, "tab-button");
		analysisButton = new ImageButton(AssetLoader.DASHBOARD_SKIN, "tab-button");
		fullTextButton = new ImageButton(AssetLoader.DASHBOARD_SKIN, "tab-button");
		
		tabButtons = new ButtonGroup<ImageButton>();
		tabButtons.add(breakdownButton);
		tabButtons.add(translationButton);
		tabButtons.add(analysisButton);
		tabButtons.add(fullTextButton);
		
		tabButtons.setMaxCheckCount(1);
		tabButtons.setUncheckLast(true);
		
		//this.add().padRight(10);
		this.add().padRight(20);
		
		this.add(breakdownButton, translationButton, analysisButton, fullTextButton);
		
		//this.add().padRight(10);
		this.add().padRight(20);
		
		this.setupTabButtonListeners();
		
		Gdx.app.debug(TAG, "dashboardUI width: " + this.getWidth());
		
		this.row();
		
		studyWindow = new Window("", AssetLoader.DASHBOARD_SKIN, "dashboard");
		
		infoLabel = new Label("", AssetLoader.DASHBOARD_SKIN, "textbox");
		infoLabel.setWrap(true);
		infoLabel.setText(currentScene.getStudyConfigs().get("breakdown").getStudyText().first());
		ScrollPane textScroll = new ScrollPane(infoLabel);
		textScroll.setScrollbarsVisible(true);
		
		studyWindow.add(textScroll).height(250);
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
				infoLabel.setText("");
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
					infoLabel.setText(infoLabel.getText() + breakdown.getStudyText().get(i));
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
				infoLabel.setText("");
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
					infoLabel.setText(infoLabel.getText() + translation.getStudyText().get(i));
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
				infoLabel.setText("");
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
					infoLabel.setText(infoLabel.getText() + analysis.getStudyText().get(i));
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
				infoLabel.setText("");
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
					infoLabel.setText(infoLabel.getText() + "\n" + fullText.getStudyText().get(i));
				}
			}
		});
	}

}
