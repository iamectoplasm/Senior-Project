package com.seniorproject.enums;

public enum SceneFiles
{
	ACT1SCENE1(	"scenes/act 1/scripts/1-1 script.json",
				"scenes/act 1/actions/1-1 actions.json",
				"scene study/act 1/analysis/1-1 analysis.json",
				"scene study/act 1/breakdown/1-1 breakdown.json",
				"scene study/act 1/text/1-1 fulltext.json",
				"scene study/act 1/translation/1-1 translation.json"),
	
	ACT1SCENE2(	"scenes/act 1/scripts/1-2 script.json",
				"scenes/act 1/actions/1-2 actions.json",
				"scene study/act 1/analysis/1-2 analysis.json",
				"scene study/act 1/breakdown/1-2 breakdown.json",
				"scene study/act 1/text/1-2 fulltext.json",
				"scene study/act 1/translation/1-2 translation.json"),
	
	ACT1SCENE3(	"scenes/act 1/scripts/1-3 script.json",
				"scenes/act 1/actions/1-3 actions.json",
				"scene study/act 1/analysis/1-3 analysis.json",
				"scene study/act 1/breakdown/1-3 breakdown.json",
				"scene study/act 1/text/1-3 fulltext.json",
				"scene study/act 1/translation/1-3 translation.json"),
	
	ACT1SCENE4(	"scenes/act 1/scripts/1-4 script.json",
				"scenes/act 1/actions/1-4 actions.json",
				"scene study/act 1/analysis/1-4 analysis.json",
				"scene study/act 1/breakdown/1-4 breakdown.json",
				"scene study/act 1/text/1-4 fulltext.json",
				"scene study/act 1/translation/1-4 translation.json"),
	
	ACT1SCENE5(	"scenes/act 1/scripts/1-5 script.json",
				"scenes/act 1/actions/1-5 actions.json",
				"scene study/act 1/analysis/1-5 analysis.json",
				"scene study/act 1/breakdown/1-5 breakdown.json",
				"scene study/act 1/text/1-5 fulltext.json",
				"scene study/act 1/translation/1-5 translation.json"),
	
	ACT1SCENE6(	"scenes/act 1/scripts/1-6 script.json",
				"scenes/act 1/actions/1-6 actions.json",
				"scene study/act 1/analysis/1-6 analysis.json",
				"scene study/act 1/breakdown/1-6 breakdown.json",
				"scene study/act 1/text/1-6 fulltext.json",
				"scene study/act 1/translation/1-6 translation.json"),
	
	ACT1SCENE7(	"scenes/act 1/scripts/1-7 script.json",
				"scenes/act 1/actions/1-7 actions.json",
				"scene study/act 1/analysis/1-7 analysis.json",
				"scene study/act 1/breakdown/1-7 breakdown.json",
				"scene study/act 1/text/1-7 fulltext.json",
				"scene study/act 1/translation/1-7 translation.json");
	
	private String scriptFile;
	private String actionsFile;
	private String analysisFile;
	private String breakdownFile;
	private String fullTextFile;
	private String translationFile;
	
	private SceneFiles(String scriptFile,
					   String actionsFile,
					   String analysisFile,
					   String breakdownFile,
					   String fullTextFile,
					   String translationFile)
	{
		this.scriptFile = scriptFile;
		this.actionsFile = actionsFile;
		this.analysisFile = analysisFile;
		this.breakdownFile = breakdownFile;
		this.fullTextFile = fullTextFile;
		this.translationFile = translationFile;
	}
	
	public String getScriptFilePath()
	{
		return scriptFile;
	}
	
	public String getActionsFilePath()
	{
		return actionsFile;
	}
	
	public String getAnalysisFilePath()
	{
		return analysisFile;
	}
	
	public String getBreakdownFilePath()
	{
		return breakdownFile;
	}
	
	public String getFullTextFilePath()
	{
		return fullTextFile;
	}
	
	public String getTranslationFilePath()
	{
		return translationFile;
	}
}
