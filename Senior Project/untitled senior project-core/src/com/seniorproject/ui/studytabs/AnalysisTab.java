package com.seniorproject.ui.studytabs;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Array;
import com.seniorproject.assetmanagement.AssetLoader;
import com.seniorproject.configs.AnalysisConfig;
import com.seniorproject.configs.AnalysisConfig.LineAnalysis;
import com.seniorproject.configs.StudyConfig;
import com.seniorproject.configs.TranslationConfig;
import com.seniorproject.configs.TranslationConfig.Translation;

public class AnalysisTab extends Window implements StudyTab
{
	private AnalysisConfig config;
	private int lineId;
	
	private ScrollPane textScrollPane;
	private String displayText;
	private Label text;

	public AnalysisTab()
	{
		super("", AssetLoader.STUDY_SKIN);
		
		this.setSize(416, 320);
		
		Label tabTitleLabel = new Label("Analysis Tab", AssetLoader.STUDY_SKIN, "tab-title-label");
		this.add(tabTitleLabel).center().top();
		
		this.row();
		
		this.text = new Label(displayText, AssetLoader.STUDY_SKIN);
		text.setWrap(true);
		Table textLayout = new Table();
		textLayout.add(text).width(304);
		textLayout.getCell(text).fill();
		textLayout.getCell(text).padLeft(4).padTop(4).padRight(16);
		textScrollPane = new ScrollPane(textLayout, AssetLoader.STUDY_SKIN, "white-scrollpane");
		
		this.add(textScrollPane).padRight(16).padTop(16).padBottom(16).height(224);
		this.getCell(textScrollPane).expand(true, true);
		
		this.pack();
	}
	
	public void resetTextLabel()
	{
		this.displayText = "";
		this.text.setText(displayText);
	}
	
	public void updateToNewScene(AnalysisConfig config)
	{
		resetTextLabel();
		
		this.config = config;
		this.lineId = 1;
		
		LineAnalysis lineAnalysis = config.getLineAnalysisInfo().get(lineId - 1);
		
		Array<String> analyses = lineAnalysis.getText();
		
		for(int i = 0; i < analyses.size; i++)
		{
			this.displayText = displayText + lineAnalysis.getText().get(i) + "\n";
		}
		
		this.text.setText(displayText + "\n");
	}
	
	public void stepForward()
	{
		lineId++;
		
		if(lineId < config.getLineAnalysisInfo().size)
		{
			LineAnalysis lineAnalysis = config.getLineAnalysisInfo().get(lineId - 1);
			
			Array<String> analyses = lineAnalysis.getText();
			
			for(int i = 0; i < analyses.size; i++)
			{
				this.displayText = displayText + lineAnalysis.getText().get(i) + "\n";
			}
			
			this.text.setText(displayText + "\n");
		}
		text.layout();
		textScrollPane.setScrollPercentY(100);
		//shakespeareScrollPane.scrollTo(0, 0, 0, 0);
	}

}
