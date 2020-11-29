package com.seniorproject.assetmanagement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.seniorproject.enums.Prop;

public class PropAtlas
{
private static final String TAG = PropAtlas.class.getSimpleName();
	
	private static PropAtlas instance;
	
	public static PropAtlas getInstance()
	{
		if(instance == null)
		{
			instance = new PropAtlas();
		}
		
		return instance;
	}
	
	private TextureRegion campTable;
	
	private TextureRegion dagger;
	private TextureRegion soliloquyOverlay;
	
	private TextureRegion diningTable;
	private TextureRegion diningChair;
	
	private PropAtlas()
	{
		TextureAtlas atlas;
		atlas = new TextureAtlas(Gdx.files.internal("props/prop-atlas.atlas"));
		
		this.campTable = atlas.findRegion("camp-table");
		
		this.dagger = atlas.findRegion("dagger-75");
		this.soliloquyOverlay = atlas.findRegion("soliloquy-overlay");
		
		this.diningTable = atlas.findRegion("dining-table");
		this.diningChair = atlas.findRegion("dining-chair");
		
		Gdx.app.debug(TAG, "dining-table region width: " + diningTable.getRegionWidth() + " height: " + diningTable.getRegionHeight());
		Gdx.app.debug(TAG, "dining-chair region width: " + diningChair.getRegionWidth() + " height: " + diningChair.getRegionHeight());
	}
	
	public TextureRegion getPropTexture(Prop name)
	{
		switch(name)
		{
		case CAMP_TABLE:
			return this.campTable;
		case GHOST_DAGGER:
			return this.dagger;
		case SOLILOQUY_LIGHTING:
			return this.soliloquyOverlay;
		case DINING_TABLE:
			return this.diningTable;
		case DINING_CHAIR:
			return this.diningChair;
			default:
				return null;
		}
	}
	
	public int getPropRegionWidth(Prop prop)
	{
		TextureRegion propRegion = getPropTexture(prop);
		return propRegion.getRegionWidth();
	}
	
	public int getPropRegionHeight(Prop prop)
	{
		TextureRegion propRegion = getPropTexture(prop);
		return propRegion.getRegionHeight();
	}
}
