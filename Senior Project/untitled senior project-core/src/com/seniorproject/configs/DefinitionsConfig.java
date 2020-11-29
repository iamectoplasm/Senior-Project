package com.seniorproject.configs;

import com.badlogic.gdx.utils.Array;

public class DefinitionsConfig extends StudyConfig
{
	private static final String TAG = DefinitionsConfig.class.getSimpleName();
	
	private boolean doesDisplayInfoChange;
	private Array<DefinitionLineInfo> definitionsTabInfo;
	
	public DefinitionsConfig()
	{
		this.definitionsTabInfo = new Array<DefinitionLineInfo>();
	}
	
	public boolean doesDisplayInfoChange()
	{
		return doesDisplayInfoChange;
	}

	public Array<DefinitionLineInfo> getDefinitionTabInfo()
	{
		return definitionsTabInfo;
	}
	
	public static class DefinitionLineInfo
	{
		private int lineID;
		private Array<Definition> definitions;
		
		public DefinitionLineInfo()
		{
			definitions = new Array<Definition>();
		}
		
		public int getLineID()
		{
			return lineID;
		}

		public Array<Definition> getDefinitions()
		{
			return definitions;
		}
	}
	
	public static class Definition
	{
		private String term;
		private String definition;
		
		public Definition()
		{
			this.term = "";
			this.definition = "";
		}

		public String getTerm()
		{
			return term;
		}

		public String getDefinition()
		{
			return definition;
		}
	}
}
