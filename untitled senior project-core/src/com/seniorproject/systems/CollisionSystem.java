package com.seniorproject.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.IntervalIteratingSystem;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.seniorproject.components.BoundingBox;
import com.seniorproject.components.Direction;
import com.seniorproject.components.Position;
import com.seniorproject.maps.StageMap;

public class CollisionSystem extends IntervalIteratingSystem
{
	public static final String TAG = CollisionSystem.class.getSimpleName();

	protected ComponentMapper<Position> mPosition;
	protected ComponentMapper<Direction> mDirection;
	protected ComponentMapper<BoundingBox> mBox;
	
	protected StageMap map;

	public CollisionSystem(StageMap map)
	{
		super(Aspect.all(Position.class,
						 Direction.class,
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
	}

	protected void updateBoundingBoxPosition(int entityId)
	{
		BoundingBox box = mBox.get(entityId);
		Position position = mPosition.get(entityId);
		Direction direction = mDirection.get(entityId);
		
		box.xPos = position.currentPosition.x;
		box.yPos = position.currentPosition.y;
		box.boundingBox.set(box.xPos, box.yPos, 1, 1);
		box.boundingBoxCenter = box.boundingBox.getCenter(new Vector2());
	}

	public boolean checkForMapCollision(int entityId)
	{
		BoundingBox box = mBox.get(entityId);
		Position position = mPosition.get(entityId);
		
		float xDest = position.destinationPosition.x;
		float yDest = position.destinationPosition.y;
		
		TiledMapTileLayer collisions = (TiledMapTileLayer) map.getStageChangeLayer();
		Cell destinationCell = collisions.getCell((int) xDest, (int) yDest);

		if (destinationCell != null)
		{
			box.xPos = position.startingPosition.x;
			box.yPos = position.startingPosition.y;
			box.boundingBox.setPosition(box.xPos, box.yPos);
			return true;
		}
		else
			return false;
	}
}
