package com.seniorproject.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.seniorproject.assetmanagement.AssetLoader;
import com.seniorproject.configs.AnalysisConfig;
import com.seniorproject.configs.DefinitionsConfig;
import com.seniorproject.configs.StudyConfig;
import com.seniorproject.configs.TranslationConfig;
import com.seniorproject.game.Scene;
import com.seniorproject.ui.studytabs.AnalysisTab;
import com.seniorproject.ui.studytabs.DefinitionsTab;
import com.seniorproject.ui.studytabs.TextTab;
import com.seniorproject.ui.studytabs.TranslationTab;

public class StudyUI extends Window
{
	private static final String TAG = StudyUI.class.getSimpleName();
	
	private Scene currentScene;
	
	private ImageButton translationButton;
	private ImageButton definitionsButton;
	private ImageButton analysisButton;
	private ImageButton fullTextButton;
	private ButtonGroup<ImageButton> tabButtons;
	
	private Window studyWindow;
	
	private TranslationTab translationTab;
	private DefinitionsTab definitionsTab;
	private AnalysisTab analysisTab;
	private TextTab fullTextTab;
	
	@SuppressWarnings("unchecked")
	public StudyUI(Scene scene)
	{
		super("", AssetLoader.STUDY_SKIN);
		//this.debugAll();
		//this.currentScene = scene;
		
		Table tabTable = new Table();
		
		translationButton = new ImageButton(AssetLoader.STUDY_SKIN, "tab-button-1");
		definitionsButton = new ImageButton(AssetLoader.STUDY_SKIN, "tab-button-2");
		analysisButton = new ImageButton(AssetLoader.STUDY_SKIN, "tab-button-3");
		fullTextButton = new ImageButton(AssetLoader.STUDY_SKIN, "tab-button-4");
		
		tabButtons = new ButtonGroup<ImageButton>();
		tabButtons.add(translationButton);
		tabButtons.add(definitionsButton);
		tabButtons.add(analysisButton);
		tabButtons.add(fullTextButton);
		
		tabButtons.getButtons().get(0).setChecked(true);
		tabButtons.setMaxCheckCount(1);
		tabButtons.setUncheckLast(true);
		
		setupTabButtonListeners(scene);
		
		tabTable.add(translationButton).padTop(32).padBottom(2);
		tabTable.row();
		tabTable.add(definitionsButton).padTop(2).padBottom(2);
		tabTable.row();
		tabTable.add(analysisButton).padTop(2).padBottom(2);
		tabTable.row();
		tabTable.add(fullTextButton).padTop(2).padBottom(32);
		
		studyWindow = new Window("", AssetLoader.STUDY_SKIN, "frame-background");
		
		//titleLabel = new Label("Title", AssetLoader.STUDY_SKIN, "tab-title-label");
		//studyWindow.add(titleLabel).align(Align.left);
		//studyWindow.row();
		
		this.add();
		this.add().width(416);
		
		this.getCells().get(1).setActor(studyWindow);
		this.getCells().get(0).setActor(tabTable).padRight(-4);
		
		this.getCell(studyWindow).fillY();
		this.getCell(studyWindow).fillX();
		this.pack();
		
		translationTab = new TranslationTab();
		definitionsTab = new DefinitionsTab();
		analysisTab = new AnalysisTab();
		fullTextTab = new TextTab();
		
		studyWindow.add(translationTab);
	}
	
	private void setupTabButtonListeners(final Scene scene)
	{
		translationButton.addListener(new ClickListener()
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
				studyWindow.clearChildren();
				studyWindow.add(translationTab);
			}
		});
		
		definitionsButton.addListener(new ClickListener()
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
				studyWindow.clearChildren();
				studyWindow.add(definitionsTab);
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
				return true;
			}
			
			@Override
			public void touchUp(InputEvent event,
                    float x,
                    float y,
                    int pointer,
                    int button)
			{
				studyWindow.clearChildren();
				studyWindow.add(analysisTab);
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
				return true;
			}
			
			@Override
			public void touchUp(InputEvent event,
                    float x,
                    float y,
                    int pointer,
                    int button)
			{
				studyWindow.clearChildren();
				studyWindow.add(fullTextTab);
			}
		});
	}
	
	public void updateStudyTabs()
	{
		definitionsTab.stepForward();
		translationTab.stepForward();
		analysisTab.stepForward();
	}
	
	public void updateToNewScene(Scene newScene)
	{
		StudyConfig text = newScene.getStudyConfigs().get("fulltext");
		fullTextTab.updateText(text);
		
		//StudyConfig definitions = newScene.getStudyConfigs().get("definitions");
		DefinitionsConfig definitions = (DefinitionsConfig) newScene.getStudyConfigs().get("definitions");
		this.definitionsTab.updateToNewScene(definitions);
		
		//StudyConfig analysisConfig = newScene.getStudyConfigs().get("analysis");
		AnalysisConfig analysisConfig = (AnalysisConfig) newScene.getStudyConfigs().get("analysis");
		this.analysisTab.updateToNewScene(analysisConfig);
		
		//StudyConfig translationConfig = newScene.getStudyConfigs().get("translation");
		TranslationConfig translationConfig = (TranslationConfig) newScene.getStudyConfigs().get("translation");
		this.translationTab.updateToNewScene(translationConfig);
	}

}
