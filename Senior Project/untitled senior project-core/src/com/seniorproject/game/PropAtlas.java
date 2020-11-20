package com.seniorproject.game;

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
	
	private TextureRegion dagger;
	private TextureRegion smokeBomb;
	private TextureRegion soliloquyOverlay;
	
	private PropAtlas()
	{
		TextureAtlas atlas;
		atlas = new TextureAtlas(Gdx.files.internal("props/prop-atlas.atlas"));
		
		//Gdx.app.debug(TAG, "Texture Atlas created, contains regions:");
		//for(int i = 0; i < atlas.getRegions().size; i++)
		//{
		//	AtlasRegion temp = atlas.getRegions().get(i);
		//	Gdx.app.debug(TAG, "\t\t" + temp.name + " at coordinates: (" + temp.getRegionX() + ", " + temp.getRegionY() + ")");
		//}
		
		this.dagger = atlas.findRegion("dagger-75");
		this.smokeBomb = atlas.findRegion("disappear-prop");
		this.soliloquyOverlay = atlas.findRegion("soliloquy-overlay");
	}
	
	public TextureRegion getPropTexture(Prop name)
	{
		switch(name)
		{
		case SMOKE_BOMB:
			return this.smokeBomb;
		case GHOST_DAGGER:
			return this.dagger;
		case SOLILOQUY_LIGHTING:
			return this.soliloquyOverlay;
			default:
				return null;
		}
	}
}
