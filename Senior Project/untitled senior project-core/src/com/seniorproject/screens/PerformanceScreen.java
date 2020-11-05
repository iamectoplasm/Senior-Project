package com.seniorproject.screens;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.seniorproject.game.AssetLoader;
import com.seniorproject.game.EntityFactory;
import com.seniorproject.game.FadeOverlay;
import com.seniorproject.game.PerformerManager;
import com.seniorproject.game.SceneManager;
import com.seniorproject.game.SeniorProject;
import com.seniorproject.maps.PerformanceRenderer;
import com.seniorproject.maps.StageMap;
import com.seniorproject.systems.*;

import com.seniorproject.ui.PerformanceHUD;

public class PerformanceScreen implements Screen
{
	private static final String TAG = PerformanceScreen.class.getSimpleName();
	
	private static class VIEWPORT
	{
		static float viewportWidth;
		static float viewportHeight;
		static float virtualWidth;
		static float virtualHeight;
		static float physicalWidth;
		static float physicalHeight;
		static float aspectRatio;
	}
	
	private SeniorProject game;
	private FitViewport viewport;
	
	private Stage stage;
	
	//private OrthographicCamera camera = null;
	public static OrthographicCamera camera = null;
	private OrthographicCamera hudCamera = null;
	
	private World world;

	private PerformanceRenderer performanceRenderer = null;
	private OrthogonalTiledMapRenderer mapRenderer = null;
	private InputMultiplexer multiplexer;
	private StageMap map;
	
	private static PerformanceHUD performanceHUD;
	
	private SceneManager sceneManager;
	
	/*
	 * 10/25/20 hacking in fade overlay to get screen fades up & running
	 */
	private Image fadeOverlay;
	/*
	 * end of bad code
	 */
	
	public PerformanceScreen(SeniorProject currentGame)
	{
		PerformanceScreen.camera = new OrthographicCamera();
		
		/*
		 * 10/25/20 hacking in fade overlay to get screen fades up & running
		 */
		this.stage = new Stage();
		stage.getRoot().setTouchable(Touchable.disabled);
		AssetLoader.loadTextureAsset("backgrounds/transition fade.png");
		this.fadeOverlay = new Image(AssetLoader.getTextureAsset("backgrounds/transition fade.png"));
		fadeOverlay.setSize(800, 600);
		fadeOverlay.setTouchable(Touchable.disabled);
		//this.fadeOverlay = FadeOverlay.getInstance().getOverlay();
		stage.addActor(fadeOverlay);
		/*
		 * end of bad code
		 */
		
		viewport = new FitViewport(4, 3, camera);
		
		camera.setToOrtho(false, viewport.getWorldWidth(), viewport.getWorldHeight());
		
		camera.zoom = 6f;
		
		this.map = new StageMap("maps/test-map-003.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(StageMap.getTiledMap(), StageMap.UNIT_SCALE);
		
		WorldConfiguration config = new WorldConfiguration();

		config.setSystem(new PerformanceSystem(map));
		config.setSystem(new AnimationSystem());
		config.setSystem(new EntitySortSystem());
		config.setSystem(new EmoticonTrackingSystem());
		config.setSystem(new MovementSystem(map));
		config.setSystem(new CollisionSystem(map));
		config.setSystem(new ZSortSystem());
		config.setSystem(new RenderSystem(camera, mapRenderer.getBatch()));

		this.world = new World(config);
		EntityFactory.setWorld(world);
		PerformerManager.setWorld(world);
		
		ShapeRenderer sr = new ShapeRenderer();
		sr.setProjectionMatrix(camera.combined);
		
		performanceRenderer = new PerformanceRenderer(mapRenderer, sr, map, world);
		
		sceneManager = new SceneManager(world);
		
		hudCamera = new OrthographicCamera();
		//hudCamera.setToOrtho(false, VIEWPORT.physicalWidth, VIEWPORT.physicalHeight);
		hudCamera.setToOrtho(false, viewport.getWorldWidth(), viewport.getWorldHeight());
		hudCamera.zoom = 1f;
		
		performanceHUD = new PerformanceHUD(hudCamera, sceneManager);
		//performanceHUD.updateStudyUIToNewScene(sceneManager.getCurrentScene());
		
		multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(performanceHUD.getStage());
	}
	
	public SceneManager getSceneManager()
	{
		return sceneManager;
	}

	@Override
	public void show()
	{
		//Gdx.app.debug(TAG, "In show() method, now clearing fadeOverlay...");
		fadeOverlay.clear();
		
		/*
		 * 10/25/20 hacking in fade overlay to get screen fades up & running
		 */
		//Gdx.app.debug(TAG, "\tAdding fadeOut action to fadeOverlay");
		fadeOverlay.addAction(Actions.fadeOut(0.5f));
		/*
		 * end of bad code
		 */
		
		Gdx.input.setInputProcessor(multiplexer);
		
		camera.position.x = StageMap.getWidth() / 2;
		camera.position.y = StageMap.getHeight() / 2;
		
		mapRenderer.setView(camera);
		performanceHUD.updateStudyUIToNewScene(sceneManager.getCurrentScene());
	}

	@Override
	public void render(float delta)
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		
		performanceRenderer.renderPerformance(delta);
		
		performanceHUD.render(delta);
		
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height)
	{
		viewport.update(width, height);
		//camera.update();
		performanceHUD.resize(width, height);
		//hudCamera.update();
	}
	
	public static PerformanceHUD getPerformanceHUD()
	{
		return performanceHUD;
	}
	
	public Image getFadeOverlay()
	{
		return this.fadeOverlay;
	}
	
	public Stage getStage()
	{
		return stage;
	}
	
	public StageMap getStageMap()
	{
		return map;
	}

	@Override
	public void pause()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide()
	{
		//Gdx.app.debug(TAG, "In hide() method, clearing fadeOverlay...");
		fadeOverlay.clear();
		/*
		 * 10/25/20 hacking in fade overlay to get screen fades up & running
		 */
		//Gdx.app.debug(TAG, "\tAdding fadeIn action to fadeOverlay");
		fadeOverlay.addAction(Actions.fadeIn(.5f));
		/*
		 * end of bad code
		 */
		//sceneManager.getCurrentScene().deactivateEntities();
	}

	@Override
	public void dispose()
	{
		// TODO Auto-generated method stub
		
	}
	
	public void fadeOutOfScreen()
	{
		fadeOverlay.toFront();
		fadeOverlay.addAction(Actions.fadeIn(0.5f));
	}
	
	public void fadeToNewScreen(final Screen newScreen)
	{
		SequenceAction performanceFadeOut = new SequenceAction();
		Action fadeIn = Actions.fadeIn(0.5f);
		fadeIn.setActor(fadeOverlay);
		
		performanceFadeOut.addAction(fadeIn);
		performanceFadeOut.addAction(Actions.run(new Runnable()
		{
			@Override
			public void run()
			{
				game.setScreen(newScreen);
				
				fadeOverlay.clear();
			}
		}));
		
		stage.getRoot().addAction(performanceFadeOut);
	}
	
	private void setupViewport(int width, int height)
	{
		// Make the viewport a percentage of the total display area
		VIEWPORT.virtualWidth = width;
		VIEWPORT.virtualHeight = height;

		// Current viewport dimensions
		VIEWPORT.viewportWidth = VIEWPORT.virtualWidth;
		VIEWPORT.viewportHeight = VIEWPORT.virtualHeight;

		// Pixel dimensions of display
		VIEWPORT.physicalWidth = Gdx.graphics.getWidth();
		VIEWPORT.physicalHeight = Gdx.graphics.getHeight();

		// Aspect ratio for current viewport
		VIEWPORT.aspectRatio = (VIEWPORT.virtualWidth / VIEWPORT.virtualHeight);

		// Update viewport if there could be skewing??
		if (VIEWPORT.physicalWidth / VIEWPORT.physicalHeight >= VIEWPORT.aspectRatio)
		{
			// Letterbox left and right
			VIEWPORT.viewportWidth = VIEWPORT.viewportHeight * (VIEWPORT.physicalWidth / VIEWPORT.physicalHeight);
			VIEWPORT.viewportHeight = VIEWPORT.virtualHeight;
		}
		else
		{
			// Letterbox above and below
			VIEWPORT.viewportWidth = VIEWPORT.virtualWidth;
			VIEWPORT.viewportHeight = VIEWPORT.viewportWidth * (VIEWPORT.physicalHeight / VIEWPORT.physicalWidth);
		}

		Gdx.app.debug(TAG, "WorldRenderer, virtual: (" + VIEWPORT.virtualWidth + ", " + VIEWPORT.virtualHeight + ")");
		Gdx.app.debug(TAG,
				"WorldRenderer, viewport: (" + VIEWPORT.viewportWidth + ", " + VIEWPORT.viewportHeight + ")");
		Gdx.app.debug(TAG,
				"WorldRenderer, physical: (" + VIEWPORT.physicalWidth + ", " + VIEWPORT.physicalHeight + ")");
	}
}
