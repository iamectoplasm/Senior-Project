package com.seniorproject.maps;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.utils.Bag;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.seniorproject.enums.StageLayer;
import com.seniorproject.screens.PerformanceScreen;
import com.seniorproject.systems.RenderSystem;
import com.seniorproject.systems.ZSortSystem;

public class PerformanceRenderer
{
	private OrthogonalTiledMapRenderer mapRenderer = null;
	private StageMap map;
	private World world;
	
	private ShapeRenderer shapeRenderer;
	
	public PerformanceRenderer(OrthogonalTiledMapRenderer renderer, ShapeRenderer shapeRenderer, StageMap map, World world)
	{
		//mapRenderer = new OrthogonalTiledMapRenderer(StageMap.getTiledMap(), StageMap.UNIT_SCALE);
		
		this.map = map;
		this.mapRenderer = renderer;
		this.world = world;
		
		this.shapeRenderer = shapeRenderer;
	}
	
	public void renderPerformance(float delta)
	{
		//mapRenderer.render(layers);
		
		world.setDelta(delta);
		world.process();
		
		mapRenderer.getBatch().setProjectionMatrix(PerformanceScreen.camera.combined);
		
		Bag<Entity> backstageEntities = world.getSystem(ZSortSystem.class).getEntitiesByZIndex(StageLayer.BACKSTAGE.getZIndex());
		world.getSystem(RenderSystem.class).entitySubsetRender(backstageEntities);
		
		mapRenderer.getBatch().begin();
		for(int i = 0; i < map.baseLayers.getLayers().getCount(); i++)
		{
			mapRenderer.renderTileLayer((TiledMapTileLayer) map.baseLayers.getLayers().get(i));
		}
		mapRenderer.getBatch().end();
		
		Bag<Entity> upStageLeftEntities = world.getSystem(ZSortSystem.class).getEntitiesByZIndex(StageLayer.UP_STAGE_LEFT.getZIndex());
		world.getSystem(RenderSystem.class).entitySubsetRender(upStageLeftEntities);
		
		mapRenderer.getBatch().begin();
		for(int i = 0; i < map.landingLayers.getLayers().getCount(); i++)
		{
			mapRenderer.renderTileLayer((TiledMapTileLayer) map.landingLayers.getLayers().get(i));
		}
		mapRenderer.getBatch().end();
		
		Bag<Entity> mainStageEntities = world.getSystem(ZSortSystem.class).getEntitiesByZIndex(StageLayer.MAIN_STAGE.getZIndex());
		world.getSystem(RenderSystem.class).entitySubsetRender(mainStageEntities);
		
		mapRenderer.getBatch().begin();
		for(int i = 0; i < map.balconyLayers.getLayers().getCount(); i++)
		{
			mapRenderer.renderTileLayer((TiledMapTileLayer) map.balconyLayers.getLayers().get(i));
		}
		mapRenderer.getBatch().end();
		
		Bag<Entity> balconyEntities = world.getSystem(ZSortSystem.class).getEntitiesByZIndex(StageLayer.BALCONY.getZIndex());
		world.getSystem(RenderSystem.class).entitySubsetRender(balconyEntities);
		
		mapRenderer.getBatch().begin();
		for(int i = 0; i < map.overlayLayers.getLayers().getCount(); i++)
		{
			mapRenderer.renderTileLayer((TiledMapTileLayer) map.overlayLayers.getLayers().get(i));
		}
		mapRenderer.renderImageLayer(map.getLightingLayer());
		mapRenderer.getBatch().end();
		
		//shapeRenderer.begin(ShapeType.Filled);
		//shapeRenderer.setColor(Color.CORAL);
		
		//for(int i = 0; i < map.getStageChangeObjects().size; i++)
		//{
		//	ZPortal temp = map.getStageChangeObjects().get(i);
		//	shapeRenderer.rect(temp.bounds.x, temp.bounds.y, temp.bounds.width, temp.bounds.height);
		//}
		//shapeRenderer.end();
	}
	
}
