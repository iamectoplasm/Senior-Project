package com.seniorproject.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EmoticonAtlas
{
	private static final String TAG = EmoticonAtlas.class.getSimpleName();
	
	private static EmoticonAtlas instance;
	
	public static EmoticonAtlas getInstance()
	{
		if(instance == null)
		{
			instance = new EmoticonAtlas();
		}
		
		return instance;
	}
	
	public static enum Emoticon
	{
		CONFUSION,
		DRUNK,
		NONE,
		POISON,
		SINGING,
		SMILE,
		SPEECH,
		SURPRISE,
		THINKING;
	}
	
	private TextureRegion confusion;
	private TextureRegion drunk1;
	private TextureRegion drunk2;
	private TextureRegion none;
	private TextureRegion poison1;
	private TextureRegion poison2;
	private TextureRegion singing1;
	private TextureRegion singing2;
	private TextureRegion smile;
	private TextureRegion speech;
	private TextureRegion surprise;
	private TextureRegion thinking;
	
	private EmoticonAtlas()
	{
		TextureAtlas atlas;
		atlas = new TextureAtlas(Gdx.files.internal("characters/emoticons/emoticons-atlas.atlas"));
		
		//Gdx.app.debug(TAG, "Texture Atlas created, contains regions:");
		//for(int i = 0; i < atlas.getRegions().size; i++)
		//{
		//	AtlasRegion temp = atlas.getRegions().get(i);
		//	Gdx.app.debug(TAG, "\t\t" + temp.name + " at coordinates: (" + temp.getRegionX() + ", " + temp.getRegionY() + ")");
		//}
		
		this.confusion = atlas.findRegion("confusion");
		this.drunk1 = atlas.findRegion("drunk", 1);
		this.drunk2 = atlas.findRegion("drunk", 2);
		this.none = atlas.findRegion("none");
		this.poison1 = atlas.findRegion("poison", 1);
		this.poison2 = atlas.findRegion("poison", 2);
		this.singing1 = atlas.findRegion("singing", 1);
		this.singing2 = atlas.findRegion("singing", 2);
		this.smile = atlas.findRegion("smile");
		this.speech = atlas.findRegion("speech");
		this.surprise = atlas.findRegion("surprise");
		this.thinking = atlas.findRegion("thinking");
	}
	
	public TextureRegion getEmoticon(Emoticon emote)
	{
		switch(emote)
		{
		case CONFUSION:
			return this.confusion;
		case DRUNK:
			return this.drunk1;
		case POISON:
			return this.poison1;
		case SINGING:
			return this.singing1;
		case SMILE:
			return this.smile;
		case SPEECH:
			return this.speech;
		case SURPRISE:
			return this.surprise;
		case THINKING:
			return this.thinking;
		default:
			return this.none;
		}
	}
}
