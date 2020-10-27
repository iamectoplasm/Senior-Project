package com.seniorproject.configs;

import com.badlogic.gdx.utils.Array;

public class ScriptConfig
{
	private static final String TAG = ScriptConfig.class.getSimpleName();
	
	//private Array<String> cast;
	private SceneIntro sceneIntro;
	private Array<Line> script;
	
	public static class Line
	{
		private int lineID;
		private boolean sharedLine;
		private Array<LineText> line;
		
		public Line()
		{
			lineID = 0;
			sharedLine = false;
			line = new Array<LineText>();
		}
		
		public int getLineID()
		{
			return lineID;
		}
		
		public boolean isSharedLine()
		{
			return sharedLine;
		}
		
		public Array<LineText> getLineText()
		{
			return line;
		}
		
		public void setLineID(int lineID)
		{
			this.lineID = lineID;
		}
		
		public void setSharedLine(boolean sharedLine)
		{
			this.sharedLine = sharedLine;
		}
		
		public void setText(Array<LineText> line)
		{
			this.line = line;
		}
	}
	
	public static class SceneIntro
	{
		private String title;
		private String backgroundImage;
		private String setting;
		private Array<String> description;
		
		public SceneIntro()
		{
			this.title = "";
			this.backgroundImage = "";
			this.setting = "";
			this.description = new Array<String>();
		}
		
		public String getTitle()
		{
			return title;
		}
		
		public String getBackground()
		{
			return backgroundImage;
		}
		
		public String getSetting()
		{
			return setting;
		}
		
		public Array<String> getDescriptionArray()
		{
			return description;
		}
	}
	
	public static class LineText
	{
		private String actor;
		private String text;
		
		public LineText()
		{
			this.actor = "no actor";
			this.text = "no text";
		}
		
		public String getActor()
		{
			return this.actor;
		}
		
		public String getText()
		{
			return this.text;
		}
		
		public void setActor(String actor)
		{
			this.actor = actor;
		}
		
		public void setText(String text)
		{
			this.text = text;
		}
	}
	
	public ScriptConfig()
	{
		//cast = new Array<String>();
		sceneIntro = new SceneIntro();
		script = new Array<Line>();
	}
	
	//public Array<String> getCast()
	//{
	//	return cast;
	//}
	
	public SceneIntro getSceneIntro()
	{
		return sceneIntro;
	}
	
	public Array<Line> getScript()
	{
		return script;
	}
	
	//public void setCast(Array<String> cast)
	//{
	//	this.cast = cast;
	//}
	
	public void setSceneIntro(SceneIntro sceneIntro)
	{
		this.sceneIntro = sceneIntro;
	}
	
	public void setScript(Array<Line> script)
	{
		this.script = script;
	}
}
