package com.seniorproject.maps;

import com.artemis.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.seniorproject.game.AssetLoader;

public class StageMap
{
	private static final String TAG = StageMap.class.getSimpleName();
	public static final float UNIT_SCALE = 1 / 16f;

	protected Json json;

	// Class variables
	private static TiledMap tiledMap = null;

	private MapLayer backstageLayer;
	private MapLayer upStageLeftLayer;
	private MapLayer mainStageLayer;
	private MapLayer balconyLayer;
	
	MapGroupLayer baseLayers;
	MapGroupLayer landingLayers;
	MapGroupLayer balconyLayers;
	MapGroupLayer overlayLayers;
	
	private MapLayer stairsLayer;
	private MapLayer stageChangeLayer;

	protected Array<Entity> mapEntities;
	
	protected Array<ZPortal> stageChangeTriggers;
	protected Array<StairsTrigger> stairsTriggers;
	
	private static int mapWidth;
	private static int mapHeight;
	
	public StageMap(String fullMapPath)
	{
		if (fullMapPath == null || fullMapPath.isEmpty())
		{
			Gdx.app.debug(TAG, "Map is invalid");
			return;
		}

		AssetLoader.loadMapAsset(fullMapPath);
		if (AssetLoader.isAssetLoaded(fullMapPath))
		{
			tiledMap = AssetLoader.getMapAsset(fullMapPath);
		}
		else
		{
			Gdx.app.debug(TAG, "Map not loaded: " + fullMapPath);
			return;
		}
		
		StageMap.mapWidth = tiledMap.getProperties().get("width", Integer.class);
		StageMap.mapHeight = tiledMap.getProperties().get("height", Integer.class);
		
		Gdx.app.debug(TAG, "mapWidth: " + mapWidth);
		Gdx.app.debug(TAG, "mapHeight: " + mapHeight);

		backstageLayer = tiledMap.getLayers().get(StageLayers.BACKSTAGE_LAYER);
		if (backstageLayer == null)
		{
			Gdx.app.debug(TAG, "No backstage layer found for map " + fullMapPath);
		}
		
		baseLayers = (MapGroupLayer) tiledMap.getLayers().get(StageLayers.BASE_LAYERS);
		if (baseLayers == null)
		{
			Gdx.app.debug(TAG, "Base layers not found for map " + fullMapPath);
		}
		
		upStageLeftLayer = tiledMap.getLayers().get(StageLayers.UP_STAGE_LEFT_LAYER);
		if (upStageLeftLayer == null)
		{
			Gdx.app.debug(TAG, "No up stage left layer found for map " + fullMapPath);
		}
		
		landingLayers = (MapGroupLayer) tiledMap.getLayers().get(StageLayers.LANDING_LAYERS);
		if (landingLayers == null)
		{
			Gdx.app.debug(TAG, "Landing layers not found for map " + fullMapPath);
		}
		
		mainStageLayer = tiledMap.getLayers().get(StageLayers.MAIN_STAGE_LAYER);
		if (mainStageLayer == null)
		{
			Gdx.app.debug(TAG, "No main stage layer found for map " + fullMapPath);
		}
		
		balconyLayers = (MapGroupLayer) tiledMap.getLayers().get(StageLayers.BALCONY_LAYERS);
		if (balconyLayers == null)
		{
			Gdx.app.debug(TAG, "Balcony layers not found for map " + fullMapPath);
		}
		
		balconyLayer = tiledMap.getLayers().get(StageLayers.BALCONY_LAYER);
		if (balconyLayer == null)
		{
			Gdx.app.debug(TAG, "No upper stage layer found for map " + fullMapPath);
		}
		
		overlayLayers = (MapGroupLayer) tiledMap.getLayers().get(StageLayers.OVERLAY_LAYERS);
		if (overlayLayers == null)
		{
			Gdx.app.debug(TAG, "Overlay layers not found for map " + fullMapPath);
		}
		
		stairsLayer = tiledMap.getLayers().get(StageLayers.STAIRS_OBJECT_LAYER);
		
		stairsTriggers = new Array<StairsTrigger>();
		//Gdx.app.debug(TAG, "Now creating stairsTrigger array:");
		for(MapObject object: stairsLayer.getObjects())
		{
			if(object instanceof RectangleMapObject)
			{
				//Gdx.app.debug(TAG, "\t\tAdding new object, object is " + object.getName());
				stairsTriggers.add(new StairsTrigger(object));
			}
		}
		
		stageChangeLayer = tiledMap.getLayers().get(StageLayers.Z_CHANGE_LAYER);
		
		stageChangeTriggers = new Array<ZPortal>();
		//Gdx.app.debug(TAG, "Now creating stageChangeTrigger array:");
		for(MapObject object: stageChangeLayer.getObjects())
		{
			if(object instanceof RectangleMapObject)
			{
				//Gdx.app.debug(TAG, "\t\tAdding new object, object is " + object.getName());
				stageChangeTriggers.add(new ZPortal(object));
			}
		}
	}
	
	/*
	 * = = = = = = = = = = = = = = = = = = = =
	 * 
	 * - Map & layer getters
	 * 
	 * = = = = = = = = = = = = = = = = = = = =
	 */
	public static TiledMap getTiledMap()
	{
		return tiledMap;
	}
	
	public static int getWidth()
	{
		return mapWidth;
	}
	
	public static int getHeight()
	{
		return mapHeight;
	}
	
	public static Vector2 getPositionNormalized(Vector2 original)
	{
		Vector2 posWithFlippedY = new Vector2(original.x, StageMap.mapHeight - 1 - original.y);
		return posWithFlippedY;
	}

	public MapLayer getStageChangeLayer()
	{
		return stageChangeLayer;
	}
	
	public Array<ZPortal> getStageChangeObjects()
	{
		return stageChangeTriggers;
	}
	
	public MapLayer getStairsLayer()
	{
		return stairsLayer;
	}
	
	public Array<StairsTrigger> getStairsTriggers()
	{
		return stairsTriggers;
	}
}
