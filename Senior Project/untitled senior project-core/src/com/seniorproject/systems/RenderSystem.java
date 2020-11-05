package com.seniorproject.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.Bag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.seniorproject.components.MapPosition;
import com.seniorproject.components.Active;
import com.seniorproject.components.BoundingBox;
import com.seniorproject.components.PerformerEmote;
import com.seniorproject.components.DrawableSprite;

public class RenderSystem extends EntitySystem
{
	private static final String TAG = RenderSystem.class.getSimpleName();
	
	ComponentMapper<DrawableSprite> mSprite;
	ComponentMapper<MapPosition> mPosition;
	ComponentMapper<BoundingBox> mBoundingBox;
	ComponentMapper<PerformerEmote> mEmotion;
	
	//Array<Entity> sortedEntities = new Array<Entity>();
	
	Camera camera;
	//SpriteBatch batch;
	Batch batch;
	
	public RenderSystem(Camera camera, Batch batch)
	{
		super(Aspect.all(Active.class,
				DrawableSprite.class,
				MapPosition.class,
				PerformerEmote.class
				));

		this.camera = camera;
		this.batch = batch;
	}

	@Override
	protected void begin()
	{
	}
	
	@Override
	protected void processSystem()
	{
//		Bag<Entity> sortedEntities = world.getSystem(EntitySortSystem.class).getSortedEntities();
//		
//		for(Entity e: sortedEntities)
//		{
//			PerformerSprite sprite = mSprite.get(e);
//			Position position = mPosition.get(e);
//		
//			//Gdx.app.debug(TAG, "Current entity being rendered: " + e.getComponent(Name.class).entityName);
//			//Gdx.app.debug(TAG, "\tLocated at: (" + position.currentPosition.x + ", " + position.currentPosition.y + ")");
//			
//			debugBoundingBox(e.getId());
//			//camera.unproject(camera.position);
//			
//			batch.setProjectionMatrix(camera.combined);
//
//			//sprite.currentFrame.flip(false, true);
//			
//			batch.begin();
//			batch.draw(sprite.currentFrame,
//					position.currentPosition.x + position.xOffset,
//					position.currentPosition.y + position.yOffset,
//					sprite.drawWidth,
//					sprite.drawHeight);
//			batch.end();
//		}
	}
	
	public void entitySubsetRender(Bag<Entity> bag)
	{
		Bag<Entity> sortedEntities = world.getSystem(EntitySortSystem.class).getSortedEntities(bag);
		
		for(Entity e: sortedEntities)
		{
			DrawableSprite sprite = mSprite.get(e);
			MapPosition position = mPosition.get(e);
			PerformerEmote emote = mEmotion.get(e);
		
			//Gdx.app.debug(TAG, "Current entity being rendered: " + e.getComponent(Name.class).entityName);
			//Gdx.app.debug(TAG, "\tLocated at: (" + position.currentPosition.x + ", " + position.currentPosition.y + ")");
			
			//debugBoundingBox(e.getId());
			
			batch.setProjectionMatrix(camera.combined);

			//sprite.currentFrame.flip(false, true);
			
			batch.begin();
			batch.draw(sprite.currentFrame,
					position.currentPosition.x + position.xOffset,
					position.currentPosition.y + position.yOffset,
					2,
					2);
					//sprite.drawWidth,
					//sprite.drawHeight);
			
			//batch.draw(emote.emoticon, position.currentPosition.x, position.currentPosition.y + 2);
			batch.draw(emote.emoticon,
					position.currentPosition.x + position.xOffset,
					position.currentPosition.y + position.yOffset + 2,
					2,
					2);
			batch.end();
		}
	}
	
	protected void debugBoundingBox(int entityId)
	{
		//HomeMap map = mLocation.get(entityId);
		BoundingBox box = mBoundingBox.get(entityId);
		
		//Camera camera = map.mapManager.getCamera();
		
		// Used to graphically debug boundingboxes
		box.shapeRenderer.setProjectionMatrix(camera.combined);
		
		box.shapeRenderer.begin(ShapeType.Filled);
		box.shapeRenderer.setColor(Color.CYAN);
		
		box.shapeRenderer.rect(box.boundingBox.x,
							   box.boundingBox.y,
							   box.boundingBox.width,
							   box.boundingBox.height);
		box.shapeRenderer.end();
	}
}
