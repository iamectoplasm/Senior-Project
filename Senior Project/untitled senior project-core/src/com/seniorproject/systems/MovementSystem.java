package com.seniorproject.systems;

import java.math.BigDecimal;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IntervalIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.seniorproject.components.Active;
import com.seniorproject.components.MovementDirection;
import com.seniorproject.components.MovementDirection.Direction;
import com.seniorproject.components.MovementState;
import com.seniorproject.components.StagePosition;
import com.seniorproject.components.Velocity;
import com.seniorproject.enums.State;
import com.seniorproject.maps.StageMap;

public class MovementSystem extends IntervalIteratingSystem
{
	private final static String TAG = MovementSystem.class.getSimpleName();
	
	ComponentMapper<MovementDirection> mDirection;
	ComponentMapper<StagePosition> mPosition;
	ComponentMapper<Velocity> mVelocity;
	ComponentMapper<MovementState> mMovementState;

	public MovementSystem()
	{
		super(Aspect.all(Active.class,
				MovementDirection.class,
				StagePosition.class,
				Velocity.class,
				MovementState.class),
				(1/60f));
	}

	protected void calculateNextPosition(int entityId)
	{
		MovementDirection direction = mDirection.get(entityId);
		StagePosition position = mPosition.get(entityId);
		MovementState movementState = mMovementState.get(entityId);
		
		/*
		Gdx.app.debug(TAG, "Now in calulateNextPosition() method for entity " + entityId);
		*/
		
		position.setStartPosition();
		
		/*
		Gdx.app.debug(TAG, "\t\tstartPosition for entity " + entityId + " set to: (" +
				position.startingPosition.x + ", " +
				position.startingPosition.y + ")");
		*/

		float destX = position.cellX + direction.currentDirection.getDX();
		float destY = position.cellY + direction.currentDirection.getDY();

		position.setDestPosition(new Vector2(destX, destY));
		
		/*
		Gdx.app.debug(TAG, "\t\tdestPosition for entity " + entityId + " set to: (" +
				position.destinationPosition.x + ", " +
				position.destinationPosition.y + ")");
		*/

		movementState.moveInProgress = true;
		
		/*
		Gdx.app.debug(TAG, "\t\tmoveInProgress for entity " + entityId + " now set to TRUE");
		
		Gdx.app.debug(TAG, "Exiting calulateNextPosition() method for entity " + entityId);
		*/
	}

	protected void move(int entityId)
	{
		MovementDirection direction = mDirection.get(entityId);
		StagePosition position = mPosition.get(entityId);
		MovementState movementState = mMovementState.get(entityId);
		
		Direction currentDirection = direction.currentDirection;
		Vector2 currentPositionLocal = position.currentPosition;
		Vector2 nextPosition = position.destinationPosition;
		Vector2 startPosition = position.startingPosition;
		
		/*
		Gdx.app.debug(TAG, "Now in move() method for entity " + entityId);
		*/
		
		movementState.stateTime += world.getDelta();

		float alpha = calculateLerpAlpha(movementState.stateTime, entityId);
		
		currentPositionLocal.set(position.startingPosition.cpy().lerp(position.destinationPosition, alpha));
		
		/*
		Gdx.app.debug(TAG, "\t\tcurrentPositionLocal is now: (" +
				currentPositionLocal.x + ", " +
				currentPositionLocal.y + ")");
		*/

		switch (currentDirection)
		{
		case RIGHT:
			MathUtils.clamp(currentPositionLocal.x, startPosition.x, nextPosition.x);
			position.setCurrentPosition(currentPositionLocal);

			if (currentPositionLocal.x >= nextPosition.x)
			{
				completeMove(entityId);
			}
			break;

		case LEFT:
			//if (currentPositionLocal.x >= nextPosition.x)
			//{
			//MathUtils.clamp(currentPositionLocal.x, nextPosition.x, startPosition.x);
				position.setCurrentPosition(currentPositionLocal);
			//}
			if(currentPositionLocal.x <= nextPosition.x)
			//else
			{
				completeMove(entityId);
			}
			break;

		case UP:
			MathUtils.clamp(currentPositionLocal.y, startPosition.y, nextPosition.y);
			position.setCurrentPosition(currentPositionLocal);
			
			if (currentPositionLocal.y >= nextPosition.y)
			{
				completeMove(entityId);
			}
			break;

		case DOWN:
			//if (currentPositionLocal.y >= nextPosition.y)
			//{
			//MathUtils.clamp(currentPositionLocal.y, nextPosition.y, startPosition.y);
				position.setCurrentPosition(currentPositionLocal);
			//}
			if(currentPositionLocal.y <= nextPosition.y)
			//else
			{
				completeMove(entityId);
			}
			break;
		default:
			break;
		}
		/*
		Gdx.app.debug(TAG, "Exiting move() method for entity " + entityId);
		System.out.println("");
		*/
	}

	private void completeMove(int entityId)
	{
		StagePosition position = mPosition.get(entityId);
		MovementState movementState = mMovementState.get(entityId);
		
		/*
		Gdx.app.debug(TAG, "Now in completeMove() method for entity " + entityId);
		*/
		
		position.cellX = (int) position.destinationPosition.x;
		position.cellY = (int) position.destinationPosition.y;
		
		position.snapToCurrentToCell();
		
		/*
		Gdx.app.debug(TAG, "\t\tposition has been snapped to current cell, is now: (" +
				position.cellX + ", " +
				position.cellY + ")");
		*/
		
		movementState.resetStateTime();
		
		movementState.moveInProgress = false;
		
		/*
		Gdx.app.debug(TAG, "\t\tmoveInProgress for entity " + entityId + " now set to FALSE");
		
		Gdx.app.debug(TAG, "Exiting completeMove() method for entity " + entityId);
		*/
	}
	
	private void reface(int entityId)
	{
		MovementState movementState = mMovementState.get(entityId);
		MovementDirection direction = mDirection.get(entityId);
		
		movementState.stateTime += world.getDelta();
		
		//if(movementState.stateTime >= State.refaceTime)
		//if(movementState.stateTime >= .15f)
		if(movementState.stateTime >= .2f)
		{
			direction.currentDirection = direction.refaceDirection;
			
			movementState.resetStateTime();
			movementState.refaceRequested = false;
			movementState.refaceInProgress = false;
			//movementState.currentState = State.IDLE;
		}
	}

	@Override
	protected void process(int entityId)
	{
		MovementState movementState = mMovementState.get(entityId);
		StagePosition position = mPosition.get(entityId);
		
		if(movementState.refaceRequested)
		{
			movementState.refaceInProgress = true;
			movementState.currentState = State.REFACE;
			reface(entityId);
		}
		
		else if(movementState.moveRequested && !movementState.moveInProgress && !position.hasReachedDestTile())
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
	
	/*
	public void cancelMove(int entityId)
	{
		Position position = mPosition.get(entityId);
		MovementState state = mMovementState.get(entityId);
		
		state.currentState = MovementState.State.IDLE;
		state.moveRequested = false;
		state.moveInProgress = false;

		position.resetAllToStarting();
	}
	*/

	private float calculateLerpAlpha(float currentTime, int entityId)
	{
		Velocity velocity = mVelocity.get(entityId);
		
		//Gdx.app.debug(TAG, "In calculateLerpAlpha, current time is " + currentTime);
		
		//float alpha = (currentTime/ velocity.velocity);
		float alpha = (currentTime/ velocity.speed.getDuration());
		Float roundedAlpha = new BigDecimal(alpha).setScale(3, BigDecimal.ROUND_HALF_UP).floatValue();
		return roundedAlpha;
	}

}
