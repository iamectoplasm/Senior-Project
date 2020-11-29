package com.seniorproject.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.seniorproject.assetmanagement.EmoticonAtlas;

public class PerformerEmote extends Component
{
	private static final String TAG = PerformerEmote.class.getSimpleName();
	
	public float xPos;
	public float yPos;
	
	public boolean hasEmote;
	
	public EmoticonAtlas.Emoticon emote;
	
	public TextureRegion emoticon;
	public Animation<TextureRegion> animatedEmoticon;
	
	public PerformerEmote()
	{
		xPos = 0;
		yPos = 0;
		hasEmote = false;
		
		this.emote = EmoticonAtlas.Emoticon.NONE;
		this.emoticon = EmoticonAtlas.getInstance().getEmoticon(emote);
	}
}
