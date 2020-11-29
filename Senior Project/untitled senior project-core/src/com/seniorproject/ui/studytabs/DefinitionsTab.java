package com.seniorproject.ui.studytabs;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Array;
import com.seniorproject.assetmanagement.AssetLoader;
import com.seniorproject.configs.DefinitionsConfig;
import com.seniorproject.configs.DefinitionsConfig.Definition;
import com.seniorproject.configs.StudyConfig;

public class DefinitionsTab extends Window
{
	private DefinitionsConfig config;
	private int currentLineIndex;
	
	private String displayText;
	private Label text;

	public DefinitionsTab()
	{
		super("", AssetLoader.STUDY_SKIN);
		
		this.setSize(416, 320);
		
		Label tabTitleLabel = new Label("Definitions tab", AssetLoader.STUDY_SKIN, "tab-title-label");
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
	
	public void updateToNewScene(DefinitionsConfig config)
	{
		resetTextLabel();
		
		this.config = config;
		this.currentLineIndex = 1;
		
		Array<Definition> definitions = config.getDefinitionTabInfo().get(currentLineIndex - 1).getDefinitions();
		
		if(definitions.isEmpty())
		{
			return;
		}
		
		for(int i = 0; i < definitions.size; i++)
		{
			displayText = displayText + "Term: " + definitions.get(i).getTerm() + "\n" +
					"Definition:\n" + definitions.get(i).getDefinition() + "\n\n";
		}
		
		this.text.setText(displayText);
		
		//Array<String> studyText = config.getStudyText();
		//
		//for(int i = 0; i < studyText.size; i++)
		//{
		//	displayText = displayText + "\n" + studyText.get(i);
		//}
		
		//this.text.setText(displayText);
	}
	
	public void stepForward()
	{
		currentLineIndex++;
		
		if(currentLineIndex < config.getDefinitionTabInfo().size)
		{
			Array<Definition> definitions = config.getDefinitionTabInfo().get(currentLineIndex - 1).getDefinitions();
		
			for(int i = 0; i < definitions.size; i++)
			{
				displayText = displayText + "Term: " + definitions.get(i).getTerm() + "\n" +
						"Definition:\n" + definitions.get(i).getDefinition() + "\n\n";
			}
		
			this.text.setText(displayText);
		}
	}

}
