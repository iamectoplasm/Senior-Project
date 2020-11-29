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
import com.seniorproject.components.Position;
import com.seniorproject.components.PropComponent;
import com.seniorproject.components.Active;
import com.seniorproject.components.BoundingBox;
import com.seniorproject.components.PerformerEmote;
import com.seniorproject.components.DrawableSprite;

public class RenderSystem extends EntitySystem
{
	private static final String TAG = RenderSystem.class.getSimpleName();
	
	ComponentMapper<DrawableSprite> mSprite;
	ComponentMapper<Position> mPosition;
	ComponentMapper<BoundingBox> mBoundingBox;
	ComponentMapper<PerformerEmote> mEmotion;
	
	//Array<Entity> sortedEntities = new Array<Entity>();
	
	Camera camera;
	//SpriteBatch batch;
	Batch batch;
	float delta = 0;
	
	public RenderSystem(Camera camera, Batch batch)
	{
		super(Aspect.all(Active.class,
				DrawableSprite.class,
				Position.class));

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
		if(delta >= 5)
		{
			delta = 0;
		}
		
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
			Position position = mPosition.get(e);
			PerformerEmote emote = mEmotion.get(e);
		
			//Gdx.app.debug(TAG, "Current entity being rendered: " + e.getComponent(Name.class).entityName);
			//Gdx.app.debug(TAG, "\tLocated at: (" + position.currentPosition.x + ", " + position.currentPosition.y + ")");
			
			//debugBoundingBox(e.getId());
			
			batch.setProjectionMatrix(camera.combined);

			//sprite.currentFrame.flip(false, true);
			
			batch.begin();
			batch.draw(sprite.currentFrame,
					position.currentPosition.x + sprite.xOffset,
					position.currentPosition.y + sprite.yOffset,
					//position.currentPosition.x + position.xOffset,
					//position.currentPosition.y + position.yOffset,
					//2,
					//2);
					sprite.drawWidth,
					sprite.drawHeight);
			batch.end();
			
			if(Aspect.all(PerformerEmote.class).build(world).isInterested(e))
			{
				batch.begin();
				batch.draw(emote.emoticon,
						position.currentPosition.x + sprite.xOffset,
						position.currentPosition.y + sprite.yOffset + 2,
						2,
						2);
				batch.end();
			}
			
			/*
			if(emote.emote.isAnimated())
			{
				delta += world.getDelta();
				
				batch.begin();
				//batch.draw(emote.emoticon, position.currentPosition.x, position.currentPosition.y + 2);
				batch.draw(emote.animatedEmoticon.getKeyFrame(delta),
						position.currentPosition.x + position.xOffset,
						position.currentPosition.y + position.yOffset + 2,
						2,
						2);
				batch.end();
			}
			else
			{
			*/
				//batch.begin();
				//batch.draw(emote.emoticon,
				//		position.currentPosition.x + position.xOffset,
				//		position.currentPosition.y + position.yOffset + 2,
				//		2,
				//		2);
				//batch.end();
			//}
		}
	}
	
	public void entityEmoteRender(Bag<Entity> bag)
	{
		
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
