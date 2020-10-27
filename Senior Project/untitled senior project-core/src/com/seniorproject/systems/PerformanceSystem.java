package com.seniorproject.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IntervalIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.seniorproject.components.MovementDirection;
import com.seniorproject.components.StageDirections;
import com.seniorproject.components.Active;
import com.seniorproject.components.MovementState;
import com.seniorproject.components.Name;
import com.seniorproject.components.Position;
import com.seniorproject.components.Velocity;
import com.seniorproject.components.MovementState.State;
import com.seniorproject.configs.BlockingConfig.Actions;
import com.seniorproject.maps.StageMap;

public class PerformanceSystem extends IntervalIteratingSystem
{
	private final static String TAG = PerformanceSystem.class.getSimpleName();
	
	private StageMap map;
	
	ComponentMapper<MovementDirection> mDirection;
	ComponentMapper<Position> mPosition;
	ComponentMapper<Velocity> mVelocity;
	ComponentMapper<MovementState> mMovementState;
	ComponentMapper<StageDirections> mStageDirections;
	
	ComponentMapper<Name> mName;

	public PerformanceSystem(StageMap map)
	{
		super(Aspect.all(Active.class,
				MovementDirection.class,
				Position.class,
				Velocity.class,
				MovementState.class,
				StageDirections.class),
				(1/60f));
		
		this.map = map;
	}
	
	public void updateCharacterActions(int entityId, Array<Actions> stageDirections)
	{
		StageDirections directions = mStageDirections.get(entityId);
		
		//lineActions.actionInProgress = false;
		
		//lineActions.resetActionsArray();
		
		for(int i = 0; i < stageDirections.size; i++)
		{
			directions.stageDirectionQueue.addLast(stageDirections.get(i));
		}
	}

	@Override
	protected void process(int entityId)
	{
		MovementDirection direction = mDirection.get(entityId);
		Position position = mPosition.get(entityId);
		MovementState movementState = mMovementState.get(entityId);
		StageDirections stageDirections = mStageDirections.get(entityId);
		
		Name name = mName.get(entityId);
		
		if(!stageDirections.stageDirectionQueue.isEmpty())
		{
			if(!stageDirections.actionInProgress)
			{
				stageDirections.actionInProgress = true;
				stageDirections.currentStageDirection = stageDirections.stageDirectionQueue.removeFirst();
				//Gdx.app.debug(TAG, "Now executing action " + actions.currentAction.getAction() + " " + actions.currentAction.getDirection() + " for actor " + name.name);
				
				switch(stageDirections.currentStageDirection.getAction())
				{
				case WALK:
					direction.currentDirection = stageDirections.currentStageDirection.getDirection();
					
					Vector2 normalizedDest = StageMap.normalizePosition(stageDirections.currentStageDirection.getDestination());
					position.actionDestination = new Vector2(normalizedDest);
					movementState.moveRequested = true;
					
					Gdx.app.debug(TAG, "\t\tTo position (" +
							stageDirections.currentStageDirection.getDestination().x + ", " +
							stageDirections.currentStageDirection.getDestination().y + ")");
					break;
					
				case REFACE:
					direction.refaceDirection = stageDirections.currentStageDirection.getDirection();
					movementState.refaceRequested = true;
					break;
					
				default:
					movementState.currentState = State.IDLE;
					break;
				}
			}
			else
			{
				if(position.hasReachedDestTile() && !movementState.refaceInProgress)
				{
					stageDirections.actionEnded = true;
					
					//
					position.zHasChanged = false;
					//
					//Gdx.app.debug(TAG, "Have now ended action " + actions.currentAction.getAction() + " " + actions.currentAction.getDirection() + " for actor " + name.name);
					
					if(stageDirections.stageDirectionQueue.notEmpty())
					{
						stageDirections.actionInProgress = false;
					}
				}
			}
		}
	}
}
