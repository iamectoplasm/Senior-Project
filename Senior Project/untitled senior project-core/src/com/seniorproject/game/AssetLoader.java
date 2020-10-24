package com.seniorproject.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.BaseTmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TmxMapLoader.Parameters;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.seniorproject.components.MovementAnimation;
import com.seniorproject.components.Velocity;
import com.seniorproject.components.MovementAnimation.Animation;

public class AssetLoader
{
	private static final String TAG = AssetLoader.class.getSimpleName();
	
	private final static AssetManager assetManager = new AssetManager();
	private static InternalFileHandleResolver filePathResolver = new InternalFileHandleResolver();
	
	public final static String INTRO_TEXTURE_ATLAS_PATH = "skins/introui.atlas";
	public final static String INTRO_SKIN_PATH = "skins/introui.json";
	public static TextureAtlas INTRO_TEXTUREATLAS = new TextureAtlas(INTRO_TEXTURE_ATLAS_PATH);
	public static Skin INTRO_SKIN = new Skin(Gdx.files.internal(INTRO_SKIN_PATH), INTRO_TEXTUREATLAS);
	
	public final static String SELECT_TEXTURE_ATLAS_PATH = "skins/sceneselectui.atlas";
	public final static String SELECT_SKIN_PATH = "skins/sceneselectui.json";
	public static TextureAtlas SELECT_TEXTUREATLAS = new TextureAtlas(SELECT_TEXTURE_ATLAS_PATH);
	public static Skin SELECT_SKIN = new Skin(Gdx.files.internal(SELECT_SKIN_PATH), SELECT_TEXTUREATLAS);
	
	public final static String DIALOGUE_TEXTURE_ATLAS_PATH = "skins/dialogeui.atlas";
	public final static String DIALOGUE_SKIN_PATH = "skins/dialogeui.json";
	public static TextureAtlas DIALOGUE_TEXTUREATLAS = new TextureAtlas(DIALOGUE_TEXTURE_ATLAS_PATH);
	public static Skin DIALOGUE_SKIN = new Skin(Gdx.files.internal(DIALOGUE_SKIN_PATH), DIALOGUE_TEXTUREATLAS);
	
	public final static String DASHBOARD_TEXTURE_ATLAS_PATH = "skins/dashboardui.atlas";
	public final static String DASHBOARD_SKIN_PATH = "skins/dashboardui.json";
	public static TextureAtlas DASHBOARD_TEXTUREATLAS = new TextureAtlas(DASHBOARD_TEXTURE_ATLAS_PATH);
	public static Skin DASHBOARD_SKIN = new Skin(Gdx.files.internal(DASHBOARD_SKIN_PATH), DASHBOARD_TEXTUREATLAS);
	
	/*
	 * = = = = = = = = = = = = = = = = = = = =
	 * 
	 * - General asset loading methods
	 * 
	 * = = = = = = = = = = = = = = = = = = = =
	 */
	public static int numberAssetsQueued()
	{
		return assetManager.getQueuedAssets();
	}

	public static boolean updateAssetLoading()
	{
		return assetManager.update();
	}

	public static float loadCompleted()
	{
		return assetManager.getProgress();
	}

	public static boolean isAssetLoaded(String fileName)
	{
		return assetManager.isLoaded(fileName);
	}

	public static void unloadAsset(String assetFilenamePath)
	{
		boolean assetManagerIsLoaded = assetManager.isLoaded(assetFilenamePath);
		// Once the asset manager is done loading
		if (assetManagerIsLoaded)
		{
			assetManager.unload(assetFilenamePath);
		}
		else
		{
			Gdx.app.debug(TAG, "Asset is not loaded; Nothing to unload at " + assetFilenamePath);
		}
	}
	
	/*
	 * = = = = = = = = = = = = = = = = = = = =
	 * 
	 * - Map Assets
	 * 
	 * = = = = = = = = = = = = = = = = = = = =
	 */
	public static void loadMapAsset(String mapFilenamePath)
	{
		if (mapFilenamePath == null || mapFilenamePath.isEmpty())
		{
			return;
		}
		
		if(assetManager.isLoaded(mapFilenamePath))
		{
			return;
		}

		Gdx.app.debug(TAG, "Resolved path: " + filePathResolver.resolve(mapFilenamePath));
		boolean mapExists = filePathResolver.resolve(mapFilenamePath).exists();
		if (mapExists)
		{
			TmxMapLoader.Parameters param = new Parameters();
			//param.flipY = false;
			//param.convertObjectToTileSpace = true;
			
			TmxMapLoader mapLoader = new TmxMapLoader(filePathResolver);
			
			assetManager.setLoader(TiledMap.class, mapLoader);

			assetManager.load(mapFilenamePath, TiledMap.class, param);

			// Until we add in a loading screen, block until we load map
			assetManager.finishLoadingAsset(mapFilenamePath);
			Gdx.app.debug(TAG, "Map loaded: " + mapFilenamePath);
		}
		else
		{
			Gdx.app.debug(TAG, "Map does not exist: " + mapFilenamePath);
		}
	}
	
	public static TiledMap getMapAsset(String mapFilenamePath)
	{
		TiledMap map = null;
		
		// Once the asset manager is done loading
		boolean mapIsLoaded = assetManager.isLoaded(mapFilenamePath);
		if(mapIsLoaded)
		{
			map = assetManager.get(mapFilenamePath, TiledMap.class);
		}
		
		else
		{
			Gdx.app.debug(TAG, "Map is not loaded: " + mapFilenamePath);
		}

		return map;
	}
	
	/*
	 * = = = = = = = = = = = = = = = = = = = =
	 * 
	 * - Texture Assets
	 * 
	 * = = = = = = = = = = = = = = = = = = = =
	 */
	public static void loadTextureAsset(String textureFilenamePath)
	{
		if (textureFilenamePath == null || textureFilenamePath.isEmpty())
		{
			return;
		}

		// Load asset
		boolean textureExists = filePathResolver.resolve(textureFilenamePath).exists();
		if(textureExists)
		{
			assetManager.setLoader(Texture.class, new TextureLoader(filePathResolver));

			assetManager.load(textureFilenamePath, Texture.class);

			// Until we add a loading screen, block until we load the map
			assetManager.finishLoadingAsset(textureFilenamePath);
		}
		else
		{
			Gdx.app.debug(TAG, "Texture does not exist: " + textureFilenamePath);
		}
	}

	public static Texture getTextureAsset(String textureFilenamePath)
	{
		Texture texture = null;

		// Once the asset manager is done loading
		boolean textureIsLoaded = assetManager.isLoaded(textureFilenamePath);
		if(textureIsLoaded)
		{
			texture = assetManager.get(textureFilenamePath, Texture.class);
		}
		else
		{
			Gdx.app.debug(TAG, "Texture is not loaded: " + textureFilenamePath);
		}

		return texture;
	}
}
