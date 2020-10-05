package com.seniorproject.game;

import com.badlogic.gdx.utils.Array;

public class ScriptConfig
{
	private static final String TAG = ScriptConfig.class.getSimpleName();
	
	private Array<String> cast;
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
		cast = new Array<String>();
		script = new Array<Line>();
	}
	
	public Array<String> getCast()
	{
		return cast;
	}
	
	public Array<Line> getScript()
	{
		return script;
	}
	
	public void setCast(Array<String> cast)
	{
		this.cast = cast;
	}
	
	public void setScript(Array<Line> script)
	{
		this.script = script;
	}
}
