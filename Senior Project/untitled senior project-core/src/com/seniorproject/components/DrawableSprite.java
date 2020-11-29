package com.seniorproject.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DrawableSprite extends Component
{
	public TextureRegion currentFrame = new TextureRegion();
	
	public int drawWidth;
	public int drawHeight;
	
	public float xOffset = 0;
	public float yOffset = 0;

	public DrawableSprite()
	{

	}
}
