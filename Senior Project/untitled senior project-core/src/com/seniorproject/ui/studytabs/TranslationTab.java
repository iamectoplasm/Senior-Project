package com.seniorproject.ui.studytabs;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.seniorproject.configs.StudyConfig;
import com.seniorproject.game.AssetLoader;

public class TranslationTab extends Window
{
	private static final String TAG = TranslationTab.class.getSimpleName();
	
	private String displayText;
	private Label text;

	public TranslationTab()
	{
		super("", AssetLoader.STUDY_SKIN);
		this.setSize(416, 320);
		
		Label tabTitleLabel = new Label("Translation into modern English", AssetLoader.STUDY_SKIN, "tab-title-label");
		this.add(tabTitleLabel).center().top();
		
		this.row();
		
		/*
		Label shakespeareText = new Label("", AssetLoader.STUDY_SKIN);
		shakespeareText.setWrap(true);
		Table shakespeareTextLayout = new Table();
		//shakespeareTextLayout.debug();
		shakespeareTextLayout.add(shakespeareText).width(152);
		shakespeareTextLayout.getCell(shakespeareText).fill();
		shakespeareTextLayout.getCell(shakespeareText).padLeft(4).padTop(4).padRight(16);
		ScrollPane shakespeareScrollPane = new ScrollPane(shakespeareTextLayout, AssetLoader.STUDY_SKIN, "blue-scrollpane");
		
		//this.add(shakespeareText).padLeft(16).padTop(16).padBottom(16).width(191).height(224);
		this.add(shakespeareScrollPane).padLeft(16).padTop(16).padBottom(16).width(191).height(224);
		this.getCell(shakespeareScrollPane).fillX();
		this.getCell(shakespeareScrollPane).fillY();
		
		
		Label translation = new Label("", AssetLoader.STUDY_SKIN);
		translation.setWrap(true);
		Table translationLayout = new Table();
		//shakespeareTextLayout.debug();
		translationLayout.add(translation).width(152);
		translationLayout.getCell(translation).fill();
		translationLayout.getCell(translation).padLeft(4).padTop(4).padRight(16);
		ScrollPane translationScrollPane = new ScrollPane(translationLayout, AssetLoader.STUDY_SKIN, "cream-scrollpane");
		
		this.add(translationScrollPane).padRight(16).padTop(16).padBottom(16).width(191).height(224);
		this.getCell(translationScrollPane).fillX();
		this.getCell(translationScrollPane).fillY();
		
		this.getCell(tabTitleLabel).colspan(2);
		
		this.pack();
		*/
		
		this.text = new Label(displayText, AssetLoader.STUDY_SKIN);
		text.setWrap(true);
		Table textLayout = new Table();
		//shakespeareTextLayout.debug();
		textLayout.add(text).width(304);
		textLayout.getCell(text).fill();
		textLayout.getCell(text).padLeft(4).padTop(4).padRight(16);
		ScrollPane textScrollPane = new ScrollPane(textLayout, AssetLoader.STUDY_SKIN, "white-scrollpane");
		
		this.add(textScrollPane).padRight(16).padTop(16).padBottom(16).height(224);
		this.getCell(textScrollPane).expand(true, true);
		
		this.pack();
	}
	
	public void resetTextLabel()
	{
		displayText = "";
		this.text.setText(displayText);
	}
	
	public void updateText(StudyConfig config)
	{
		resetTextLabel();
		
		Array<String> studyText = config.getStudyText();
		
		for(int i = 0; i < studyText.size; i++)
		{
			displayText = displayText + "\n" + studyText.get(i);
		}
		
		this.text.setText(displayText);
	}

}
