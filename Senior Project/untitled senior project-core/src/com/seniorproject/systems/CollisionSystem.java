package com.seniorproject.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IntervalIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.seniorproject.components.Active;
import com.seniorproject.components.BoundingBox;
import com.seniorproject.components.MovementDirection;
import com.seniorproject.components.Name;
import com.seniorproject.components.Position;
import com.seniorproject.enums.StageLayer;
import com.seniorproject.maps.StageMap;
import com.seniorproject.maps.StairsTrigger;
import com.seniorproject.maps.ZPortal;

public class CollisionSystem extends IntervalIteratingSystem
{
	public static final String TAG = CollisionSystem.class.getSimpleName();

	protected ComponentMapper<Position> mPosition;
	protected ComponentMapper<MovementDirection> mDirection;
	protected ComponentMapper<BoundingBox> mBox;
	
	protected StageMap map;

	public CollisionSystem(StageMap map)
	{
		super(Aspect.all(Active.class,
				Position.class,
				MovementDirection.class,
				BoundingBox.class), (1/60f));
		
		this.map = map;
	}

	public void initBoundingBox(int entityId)
	{
		BoundingBox box = mBox.get(entityId);
		Position position = mPosition.get(entityId);
		
		box.shapeRenderer = new ShapeRenderer();

		box.xPos = position.currentPosition.x;
		box.yPos = position.currentPosition.y;

		box.boundingBox = new Rectangle(box.xPos, box.yPos, 1, 1);
		
		box.boundingBoxCenter = box.boundingBox.getCenter(new Vector2());
	}
	
	@Override
	protected void process(int entityId)
	{
		updateBoundingBoxPosition(entityId);
		
		checkForMapCollision(entityId);
	}

	protected void updateBoundingBoxPosition(int entityId)
	{
		BoundingBox box = mBox.get(entityId);
		Position position = mPosition.get(entityId);
		//MovementDirection direction = mDirection.get(entityId);
		
		box.xPos = position.currentPosition.x;
		box.yPos = position.currentPosition.y;
		box.boundingBox.set(box.xPos, box.yPos, 1, 1);
	}

	public boolean checkForMapCollision(int entityId)
	{
		BoundingBox box = mBox.get(entityId);
		Position position = mPosition.get(entityId);
		MovementDirection direction = mDirection.get(entityId);
		
		Array<ZPortal> portals = map.getStageChangeObjects();
		
		for(int i = 0; i < portals.size; i++)
		{
			ZPortal portal = portals.get(i);
			
			boolean entityEligibleToChangeZIndex = portal.fromLayer().getZIndex() == position.mapZIndex && !position.zHasChanged;
			
			if(entityEligibleToChangeZIndex)
			{
				if(portal.getPortalBounds().overlaps(box.boundingBox))
				{
					StageLayer fromLayer = portal.fromLayer();
					StageLayer toLayer = portal.toLayer();
					Gdx.app.debug(TAG, "Collision occurring at " + portal.getName() + " for entity " + world.getEntity(entityId).getComponent(Name.class).name);
					Gdx.app.debug(TAG, "\t\tfromLayer: " + fromLayer);
					Gdx.app.debug(TAG, "\t\ttoLayer: " + toLayer);
					position.mapZIndex = toLayer.getZIndex();
					position.zHasChanged = true;
					return true;
				}
			}
		}
		
		Array<StairsTrigger> stairsTriggers = map.getStairsTriggers();
		
		for(int i = 0; i < stairsTriggers.size; i++)
		{
			StairsTrigger trigger = stairsTriggers.get(i);
			
			if(box.boundingBox.contains(trigger.getStairTriggerBounds()))
			{
				boolean matchesActivationDirection = direction.currentDirection == trigger.getActivationDirection();
				
				if(matchesActivationDirection)
				{
					Gdx.app.debug(TAG, "Collision occurring at " + trigger.getName() + " for entity " + world.getEntity(entityId).getComponent(Name.class).name);
					Gdx.app.debug(TAG, "\t\tFrom direction: " + trigger.getActivationDirection());
					Gdx.app.debug(TAG, "\t\tYShift: " + trigger.getYShift());
					position.destinationPosition.y = position.destinationPosition.y + trigger.getYShift();
					return true;
				}
			}
		}
		
		return false;
	}
}
