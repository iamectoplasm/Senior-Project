package com.seniorproject.enums;

public enum StageLayer
{
	BACKSTAGE(0),
	UP_STAGE_LEFT(1),
	MAIN_STAGE(2),
	BALCONY(3);
	
	private int zIndex;
	
	private StageLayer(int index)
	{
		this.zIndex = index;
	}
	
	public int getZIndex()
	{
		return zIndex;
	}
}
