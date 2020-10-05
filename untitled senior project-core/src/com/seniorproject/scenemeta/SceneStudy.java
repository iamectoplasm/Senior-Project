package com.seniorproject.scenemeta;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.seniorproject.game.ScriptConfig;

public class SceneStudy
{
	private StudyConfig analysisConfig;
	private StudyConfig breakdownConfig;
	private StudyConfig scriptConfig;
	private StudyConfig translationConfig;
	
	public static enum StudyFiles
	{
		ACT1SCENE6("meta/1-6 analysis.json",
				   "meta/1-6 breakdown.json",
				   "meta/1-6 full script.json",
				   "meta/1-6 translation.json");
		
		private String analysisFile;
		private String breakdownFile;
		private String scriptFile;
		private String translationFile;
		
		private StudyFiles(String analysisFile,
				String breakdownFile,
				String scriptFile,
				String translationFile)
		{
			this.analysisFile = analysisFile;
			this.breakdownFile = breakdownFile;
			this.scriptFile = scriptFile;
			this.translationFile = translationFile;
		}
		
		public String getAnalysisFilePath()
		{
			return analysisFile;
		}
		
		public String getBreakdownFilePath()
		{
			return breakdownFile;
		}
		
		public String getScriptFilePath()
		{
			return scriptFile;
		}
		
		public String getTranslationFilePath()
		{
			return translationFile;
		}
	}
	
	public SceneStudy(StudyFiles sceneStudyFiles)
	{
		Json tempJson = new Json();
		this.analysisConfig = tempJson.fromJson(StudyConfig.class,
				Gdx.files.internal(sceneStudyFiles.getAnalysisFilePath()).read());
		
		this.breakdownConfig = tempJson.fromJson(StudyConfig.class,
				Gdx.files.internal(sceneStudyFiles.getBreakdownFilePath()).read());
		
		this.scriptConfig = tempJson.fromJson(StudyConfig.class,
				Gdx.files.internal(sceneStudyFiles.getScriptFilePath()).read());
		
		this.translationConfig = tempJson.fromJson(StudyConfig.class,
				Gdx.files.internal(sceneStudyFiles.getTranslationFilePath()).read());
	}
}
