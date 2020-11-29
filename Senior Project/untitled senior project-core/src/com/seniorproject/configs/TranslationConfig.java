package com.seniorproject.configs;

import com.badlogic.gdx.utils.Array;

public class TranslationConfig extends StudyConfig
{
	private boolean doesDisplayInfoChange;
	private Array<Translation> translationTabInfo;
	
	public TranslationConfig()
	{
		translationTabInfo = new Array<Translation>();
	}
	
	public Array<Translation> getTranslationTabInfo()
	{
		return translationTabInfo;
	}
	
	public static class Translation
	{
		private int lineID;
		private Array<String> text;
		private Array<String> translation;
		
		public Translation()
		{
			this.text = new Array<String>();
			this.translation = new Array<String>();
		}

		public int getLineID()
		{
			return lineID;
		}

		public Array<String> getText()
		{
			return text;
		}

		public Array<String> getTranslation()
		{
			return translation;
		}
	}
}
