package com.seniorproject.maps;

import com.artemis.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.seniorproject.game.AssetLoader;

public class StageMap
{
	private static final String TAG = StageMap.class.getSimpleName();
	public static final float UNIT_SCALE = 1 / 16f;

	// Map layers
	protected final static String BACKSTAGE_LAYER = "backstage";
	protected final static String BASE_LAYERS = "base layers";
	protected final static String STAGE_LAYER = "stage";
	protected final static String VARIABLE_LAYERS = "variable layers";
	protected final static String UPPER_STAGE_LAYER = "upper stage";
	protected final static String OVERLAY_LAYERS = "overlay layers";
	
	protected final static String STAIRS_OBJECT_LAYER = "stairs";
	protected final static String STAGE_CHANGE_LAYER = "stage change triggers";

	protected Json json;

	// Class variables
	private static TiledMap tiledMap = null;

	private MapLayer backstageLayer;
	private MapLayer stageLayer;
	private MapLayer upperStageLayer;
	
	private MapGroupLayer baseLayers;
	private MapGroupLayer variableLayers;
	private MapGroupLayer overlayLayers;
	
	private MapLayer stairsLayer;
	private MapLayer stageChangeLayer;

	protected Array<Entity> mapEntities;
	
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

		backstageLayer = tiledMap.getLayers().get(BACKSTAGE_LAYER);
		if (backstageLayer == null)
		{
			Gdx.app.debug(TAG, "No backstage layer found for map " + fullMapPath);
		}
		
		baseLayers = (MapGroupLayer) tiledMap.getLayers().get(BASE_LAYERS);
		if (baseLayers == null)
		{
			Gdx.app.debug(TAG, "Base layers not found for map " + fullMapPath);
		}
		
		stageLayer = tiledMap.getLayers().get(STAGE_LAYER);
		if (stageLayer == null)
		{
			Gdx.app.debug(TAG, "No stage layer found for map " + fullMapPath);
		}
		
		variableLayers = (MapGroupLayer) tiledMap.getLayers().get(VARIABLE_LAYERS);
		if (variableLayers == null)
		{
			Gdx.app.debug(TAG, "Variable layers not found for map " + fullMapPath);
		}
		
		upperStageLayer = tiledMap.getLayers().get(UPPER_STAGE_LAYER);
		if (upperStageLayer == null)
		{
			Gdx.app.debug(TAG, "No upper stage layer found for map " + fullMapPath);
		}
		
		overlayLayers = (MapGroupLayer) tiledMap.getLayers().get(OVERLAY_LAYERS);
		if (overlayLayers == null)
		{
			Gdx.app.debug(TAG, "Overlay layers not found for map " + fullMapPath);
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
	
	public static Vector2 normalizePosition(Vector2 original)
	{
		Vector2 posWithFlippedY = new Vector2(original.x, StageMap.mapHeight - 1 - original.y);
		return posWithFlippedY;
	}

	public MapLayer getStageChangeLayer()
	{
		return stageChangeLayer;
	}
}
