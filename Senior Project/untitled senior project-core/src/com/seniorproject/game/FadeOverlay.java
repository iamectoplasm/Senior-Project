package com.seniorproject.game;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class FadeOverlay
{
	private static FadeOverlay instance;
	
	public static FadeOverlay getInstance()
	{
		if(instance == null)
		{
			instance = new FadeOverlay();
		}
		return instance;
	}
	
	Image overlay;
	
	private FadeOverlay()
	{
		AssetLoader.loadTextureAsset("backgrounds/transition fade.png");
		this.overlay = new Image(AssetLoader.getTextureAsset("backgrounds/transition fade.png"));
		overlay.setSize(800, 600);
		overlay.setTouchable(Touchable.disabled);
	}
	
	public Image getOverlay()
	{
		return this.overlay;
	}
}
