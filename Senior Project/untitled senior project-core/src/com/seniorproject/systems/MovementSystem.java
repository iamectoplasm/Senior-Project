package com.seniorproject.systems;

import java.math.BigDecimal;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IntervalIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.seniorproject.components.MovementDirection;
import com.seniorproject.components.MovementDirection.Direction;
import com.seniorproject.components.MovementState;
import com.seniorproject.components.MovementState.State;
import com.seniorproject.components.Position;
import com.seniorproject.components.Velocity;
import com.seniorproject.maps.StageMap;

public class MovementSystem extends IntervalIteratingSystem
{
	private final static String TAG = MovementSystem.class.getSimpleName();
	
	ComponentMapper<MovementDirection> mDirection;
	ComponentMapper<Position> mPosition;
	ComponentMapper<Velocity> mVelocity;
	ComponentMapper<MovementState> mMovementState;
	
	private StageMap map;

	public MovementSystem(StageMap map)
	{
		super(Aspect.all(MovementDirection.class,
						 Position.class,
						 Velocity.class,
						 MovementState.class),
				(1/60f));
		
		this.map = map;
	}

	protected void calculateNextPosition(int entityId)
	{
		MovementDirection direction = mDirection.get(entityId);
		Position position = mPosition.get(entityId);
		MovementState movementState = mMovementState.get(entityId);
		
		position.setStartPosition();

		float destX = position.cellX + direction.currentDirection.getDX();
		float destY = position.cellY + direction.currentDirection.getDY();

		position.setDestPosition(new Vector2(destX, destY));

		movementState.moveInProgress = true;
		// position.get(entityId).destinationPosition.set(destX, destY);
		// updateBoundingBoxPosition();
	}

	protected void move(int entityId)
	{
		MovementDirection direction = mDirection.get(entityId);
		Position position = mPosition.get(entityId);
		MovementState movementState = mMovementState.get(entityId);
		
		Direction currentDirection = direction.currentDirection;
		Vector2 currentPositionLocal = position.currentPosition;
		Vector2 nextPosition = position.destinationPosition;
		Vector2 startPosition = position.startingPosition;
		
		movementState.stateTime += world.getDelta();

		float alpha = calculateLerpAlpha(movementState.stateTime, entityId);
		// Gdx.app.debug(TAG, "Current lerp alpha: " + alpha);

		currentPositionLocal.set(position.startingPosition.cpy().lerp(position.destinationPosition, alpha));

		switch (currentDirection)
		{
		case RIGHT:
			// MathUtils.clamp(currentPositionLocal.x, startPosition.x, nextPosition.x);
			position.setCurrentPosition(currentPositionLocal);

			if (currentPositionLocal.x >= nextPosition.x)
			{
				completeMove(entityId);
			}
			break;

		case LEFT:
			if (currentPositionLocal.x >= nextPosition.x)
			{
				//MathUtils.clamp(currentPositionLocal.x, nextPosition.x, startPosition.x);
				position.setCurrentPosition(currentPositionLocal);
			}
			// if(currentPositionLocal.x <= nextPosition.x)
			else
			{
				completeMove(entityId);
			}
			break;

		case UP:
			// MathUtils.clamp(currentPositionLocal.y, startPosition.y, nextPosition.y);
			position.setCurrentPosition(currentPositionLocal);
			
			if (currentPositionLocal.y >= nextPosition.y)
			{
				completeMove(entityId);
			}
			break;

		case DOWN:
			if (currentPositionLocal.y >= nextPosition.y)
			{
				MathUtils.clamp(currentPositionLocal.y, nextPosition.y, startPosition.y);
				position.setCurrentPosition(currentPositionLocal);
			}
			// if(currentPositionLocal.y <= nextPosition.y)
			else
			{
				completeMove(entityId);
			}
			break;
		default:
			break;
		}
	}

	private void completeMove(int entityId)
	{
		Position position = mPosition.get(entityId);
		MovementState movementState = mMovementState.get(entityId);

		position.snapToCurrentToCell();

		movementState.resetStateTime();
		//movementState.tilesCount -= 1;
		//Gdx.app.debug(TAG, "TilesCount decreased, should now be " + movementState.tilesCount);
		movementState.moveInProgress = false;
	}
	
	private void reface(int entityId)
	{
		MovementState movementState = mMovementState.get(entityId);
		MovementDirection direction = mDirection.get(entityId);
		
		movementState.stateTime += world.getDelta();
		
		//if(movementState.stateTime >= State.refaceTime)
		if(movementState.stateTime >= .15f)
		{
			direction.currentDirection = direction.refaceDirection;
			
			movementState.stateTime = 0;
			movementState.refaceRequested = false;
			movementState.refaceInProgress = false;
			//movementState.currentState = State.IDLE;
		}
	}

	@Override
	protected void process(int entityId)
	{
		MovementState movementState = mMovementState.get(entityId);
		Position position = mPosition.get(entityId);
		
		if(movementState.refaceRequested)
		{
			movementState.refaceInProgress = true;
			movementState.currentState = State.REFACE;
			reface(entityId);
		}
		
		if(movementState.moveRequested && !movementState.moveInProgress && !position.hasReachedDestTile())
		{
			calculateNextPosition(entityId);
			//if(world.getSystem(CollisionSystem.class).checkForMapCollision(entityId) ||
			//	world.getSystem(CollisionSystem.class).checkForEntityCollision(entityId))
			//{
			//	cancelMove(entityId);
			//}
		}
		else if(movementState.moveInProgress)
		{
			movementState.currentState = State.WALK;
			move(entityId);
		}
	}
	
	public void cancelMove(int entityId)
	{
		Position position = mPosition.get(entityId);
		MovementState state = mMovementState.get(entityId);
		
		state.currentState = MovementState.State.IDLE;
		state.moveRequested = false;
		state.moveInProgress = false;

		position.resetAllToStarting();
	}

	private float calculateLerpAlpha(float currentTime, int entityId)
	{
		Velocity velocity = mVelocity.get(entityId);
		
		float alpha = (currentTime/ velocity.velocity);
		Float roundedAlpha = new BigDecimal(alpha).setScale(3, BigDecimal.ROUND_HALF_UP).floatValue();
		return roundedAlpha;
	}

}
