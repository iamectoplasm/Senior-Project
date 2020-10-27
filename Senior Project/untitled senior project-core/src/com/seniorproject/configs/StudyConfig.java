package com.seniorproject.configs;

import com.badlogic.gdx.utils.Array;

public class StudyConfig
{
	private static final String TAG = StudyConfig.class.getSimpleName();
	
	private boolean doesDisplayInfoChange;
	private Array<String> studyText;
	
	public StudyConfig()
	{
		doesDisplayInfoChange = false;
		studyText = new Array<String>();
	}

	public boolean isDoesDisplayInfoChange()
	{
		return doesDisplayInfoChange;
	}

	public void setDoesDisplayInfoChange(boolean doesDisplayInfoChange)
	{
		this.doesDisplayInfoChange = doesDisplayInfoChange;
	}

	public Array<String> getStudyText()
	{
		return studyText;
	}

	public void setStudyText(Array<String> studyText)
	{
		this.studyText = studyText;
	}
}
