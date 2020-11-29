package com.seniorproject.configs;

import com.badlogic.gdx.utils.Array;

public class AnalysisConfig extends StudyConfig
{
	private boolean doesDisplayInfoChange;
	private Array<LineAnalysis> analysisTabInfo;
	
	public AnalysisConfig()
	{
		analysisTabInfo = new Array<LineAnalysis>();
	}
	
	public Array<LineAnalysis> getLineAnalysisInfo()
	{
		return analysisTabInfo;
	}
	
	public static class LineAnalysis
	{
		private int lineID;
		private Array<String> text;
		
		public LineAnalysis()
		{
			this.text = new Array<String>();
		}

		public int getLineID()
		{
			return lineID;
		}

		public Array<String> getText()
		{
			return text;
		}
	}
}
