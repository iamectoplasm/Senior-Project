package com.seniorproject.ui.studytabs;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.seniorproject.configs.StudyConfig;
import com.seniorproject.game.AssetLoader;

public class TextTab extends Window
{
	private static final String TAG = TextTab.class.getSimpleName();
	
	private String displayText;
	private Label text;

	public TextTab()
	{
		super("", AssetLoader.STUDY_SKIN);
		
		this.displayText = "";
		
		this.setSize(416, 320);
		
		Label tabTitleLabel = new Label("Full Text", AssetLoader.STUDY_SKIN, "tab-title-label");
		tabTitleLabel.setAlignment(Align.center, Align.center);
		this.add(tabTitleLabel).center().top();
		
		this.row();
		
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
