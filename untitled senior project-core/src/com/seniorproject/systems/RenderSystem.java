package com.seniorproject.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.Bag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.seniorproject.components.Position;
import com.seniorproject.components.PerformerSprite;

public class RenderSystem extends EntitySystem
{
	private static final String TAG = RenderSystem.class.getSimpleName();
	
	ComponentMapper<PerformerSprite> mSprite;
	ComponentMapper<Position> mPosition;
	
	//Array<Entity> sortedEntities = new Array<Entity>();
	
	Camera camera;
	//SpriteBatch batch;
	Batch batch;
	
	public RenderSystem(Camera camera, Batch batch)
	{
		super(Aspect.all(PerformerSprite.class,
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
		Bag<Entity> sortedEntities = world.getSystem(EntitySortSystem.class).getSortedEntities();
		
		for(Entity e: sortedEntities)
		{
			PerformerSprite sprite = mSprite.get(e);
			Position position = mPosition.get(e);
		
			//Gdx.app.debug(TAG, "Current entity being rendered: " + e.getComponent(Name.class).entityName);
			//Gdx.app.debug(TAG, "\tLocated at: (" + position.currentPosition.x + ", " + position.currentPosition.y + ")");
			
			//debugBoundingBox(e.getId());
			//camera.unproject(camera.position);
			
			batch.setProjectionMatrix(camera.combined);

			//sprite.currentFrame.flip(false, true);
			
			batch.begin();
			batch.draw(sprite.currentFrame,
					position.currentPosition.x + position.xOffset,
					position.currentPosition.y + position.yOffset,
					sprite.drawWidth,
					sprite.drawHeight);
			batch.end();
		}
	}
}
