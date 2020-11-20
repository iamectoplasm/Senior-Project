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
	private TextureRegion soliloquyOverlay;
	
	private PropAtlas()
	{
		TextureAtlas atlas;
		atlas = new TextureAtlas(Gdx.files.internal("props/prop-atlas.atlas"));
		
		this.dagger = atlas.findRegion("dagger-75");
		this.soliloquyOverlay = atlas.findRegion("soliloquy-overlay");
	}
	
	public TextureRegion getPropTexture(Prop name)
	{
		switch(name)
		{
		case GHOST_DAGGER:
			return this.dagger;
		case SOLILOQUY_LIGHTING:
			return this.soliloquyOverlay;
			default:
				return null;
		}
	}
}
