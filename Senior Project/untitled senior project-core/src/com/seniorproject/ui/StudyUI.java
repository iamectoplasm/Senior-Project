package com.seniorproject.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.seniorproject.configs.StudyConfig;
import com.seniorproject.game.AssetLoader;
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
	
	private TranslationTab translation;
	private DefinitionsTab definitions;
	private AnalysisTab analysis;
	private TextTab fullText;
	
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
		
		translation = new TranslationTab();
		definitions = new DefinitionsTab();
		analysis = new AnalysisTab();
		fullText = new TextTab();
		
		studyWindow.add(translation);
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
				studyWindow.add(translation);
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
				studyWindow.add(definitions);
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
				studyWindow.add(analysis);
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
				studyWindow.add(fullText);
			}
		});
	}
	
	public void updateToNewScene(Scene newScene)
	{
		StudyConfig text = newScene.getStudyConfigs().get("fulltext");
		fullText.updateText(text);
		
		StudyConfig definitions = newScene.getStudyConfigs().get("breakdown");
		this.definitions.updateText(definitions);
		
		StudyConfig analysisConfig = newScene.getStudyConfigs().get("analysis");
		this.analysis.updateText(analysisConfig);
		
		StudyConfig translationConfig = newScene.getStudyConfigs().get("translation");
		this.translation.updateText(translationConfig);
	}

}
