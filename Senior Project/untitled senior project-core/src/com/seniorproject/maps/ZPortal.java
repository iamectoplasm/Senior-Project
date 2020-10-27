package com.seniorproject.maps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.seniorproject.enums.StageLayer;

public class ZPortal
{
	public static final String TAG = ZPortal.class.getSimpleName();

	Rectangle bounds;
	
	float x;
	float y;
	float width;
	float height;
	
	StageLayer fromLayer;
	StageLayer toLayer;
	String debugName;
	
	boolean changeHandled = false;
	
	public ZPortal(MapObject object)
	{
		this.fromLayer = StageLayer.valueOf(object.getProperties().get("fromLayer", String.class));
		this.toLayer = StageLayer.valueOf(object.getProperties().get("toLayer", String.class));
		
		this.x = object.getProperties().get("x", Float.class);
		this.x = x / 16;
		this.y = object.getProperties().get("y", Float.class);
		this.y = y / 16;
		this.width = object.getProperties().get("width", Float.class);
		this.width = width / 16;
		this.height = object.getProperties().get("height", Float.class);
		this.height = height / 16;
		
		this.debugName = object.getName();

		this.bounds = new Rectangle(x, y, width, height);
		Gdx.app.debug(TAG, "New ZPortal object " + object.getName() + " created with values: " + bounds.toString());
		Gdx.app.debug(TAG, "\tThis portal takes you from " + fromLayer + " to " + toLayer);
	}
	
	public Rectangle getPortalBounds()
	{
		return this.bounds;
	}
	
	public StageLayer fromLayer()
	{
		return this.fromLayer;
	}
	
	public StageLayer toLayer()
	{
		return this.toLayer;
	}
	
	public boolean checkPortalActivation(Rectangle playerBoundingBox)
	{
		if(bounds.overlaps(playerBoundingBox))
		{
			return true;
		}
		
		return false;
	}
	
	public String getName()
	{
		return debugName;
	}
}
