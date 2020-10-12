package com.seniorproject.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IntervalIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.seniorproject.components.MovementDirection;
import com.seniorproject.components.Actions;
import com.seniorproject.components.MovementState;
import com.seniorproject.components.Name;
import com.seniorproject.components.Position;
import com.seniorproject.components.Velocity;
import com.seniorproject.components.MovementState.State;
import com.seniorproject.maps.StageMap;
import com.seniorproject.scripting.ActionsConfig.Action;

public class PerformanceSystem extends IntervalIteratingSystem
{
	private final static String TAG = PerformanceSystem.class.getSimpleName();
	
	private StageMap map;
	
	ComponentMapper<MovementDirection> mDirection;
	ComponentMapper<Position> mPosition;
	ComponentMapper<Velocity> mVelocity;
	ComponentMapper<MovementState> mMovementState;
	ComponentMapper<Actions> mLineActions;
	
	ComponentMapper<Name> mName;

	public PerformanceSystem(StageMap map)
	{
		super(Aspect.all(MovementDirection.class,
				 Position.class,
				 Velocity.class,
				 MovementState.class,
				 Actions.class),
		(1/60f));
		
		this.map = map;
	}
	
	public void updateCharacterActions(int entityId, Array<Action> actions)
	{
		Actions lineActions = mLineActions.get(entityId);
		
		//lineActions.actionInProgress = false;
		
		//lineActions.resetActionsArray();
		
		for(int i = 0; i < actions.size; i++)
		{
			lineActions.actionQueue.addLast(actions.get(i));
		}
	}

	@Override
	protected void process(int entityId)
	{
		MovementDirection direction = mDirection.get(entityId);
		Position position = mPosition.get(entityId);
		MovementState movementState = mMovementState.get(entityId);
		Actions actions = mLineActions.get(entityId);
		
		Name name = mName.get(entityId);
		
		if(!actions.actionQueue.isEmpty())
		{
			if(!actions.actionInProgress)
			{
				actions.actionInProgress = true;
				
				actions.currentAction = actions.actionQueue.removeFirst();
				
				//Gdx.app.debug(TAG, "Now executing action " + actions.currentAction.getAction() + " " + actions.currentAction.getDirection() + " for actor " + name.name);
				
				if(actions.currentAction.getAction() == MovementState.State.WALK)
				{
					direction.currentDirection = actions.currentAction.getDirection();
					
					Vector2 normalizedDest = StageMap.normalizePosition(actions.currentAction.getDestination());
					position.actionDestination = new Vector2(normalizedDest);
					movementState.moveRequested = true;
					
					//Gdx.app.debug(TAG, "\t\tTo position (" + actions.currentAction.getDestination().x + ", " + actions.currentAction.getDestination().y + ")");
				}
				else if(actions.currentAction.getAction() == MovementState.State.REFACE)
				{
					direction.refaceDirection = actions.currentAction.getDirection();
					movementState.refaceRequested = true;
				}
				else
				{
					//Gdx.app.debug(TAG, "Okay weird thing happened here, neither a reface nor a walk, setting state to IDLE...");
					movementState.currentState = State.IDLE;
				}
			}
			else
			{
				if(position.hasReachedDestTile() && !movementState.refaceInProgress)
				{
					actions.actionEnded = true;
					
					//
					position.zHasChanged = false;
					//
					//Gdx.app.debug(TAG, "Have now ended action " + actions.currentAction.getAction() + " " + actions.currentAction.getDirection() + " for actor " + name.name);
					
					if(actions.actionQueue.notEmpty())
					{
						actions.actionInProgress = false;
					}
				}
			}
		}
	}

}
