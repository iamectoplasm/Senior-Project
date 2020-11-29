package com.seniorproject.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IntervalIteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.seniorproject.components.MovementDirection;
import com.seniorproject.assetmanagement.EmoticonAtlas;
import com.seniorproject.components.ActionsQueue;
import com.seniorproject.components.Active;
import com.seniorproject.components.DrawableSprite;
import com.seniorproject.components.PerformerEmote;
import com.seniorproject.components.MovementState;
import com.seniorproject.components.Name;
import com.seniorproject.components.Position;
import com.seniorproject.components.Velocity;
import com.seniorproject.configs.PerformConfig.ActionToPerform;
import com.seniorproject.enums.State;
import com.seniorproject.maps.StageMap;

public class PerformanceSystem extends IntervalIteratingSystem
{
	//private final static String TAG = PerformanceSystem.class.getSimpleName();
	
	ComponentMapper<MovementDirection> mDirection;
	ComponentMapper<Position> mPosition;
	ComponentMapper<Velocity> mVelocity;
	ComponentMapper<MovementState> mMovementState;
	ComponentMapper<ActionsQueue> mStageDirections;
	ComponentMapper<PerformerEmote> mEmotion;
	
	ComponentMapper<DrawableSprite> mSprite;
	
	ComponentMapper<Name> mName;

	public PerformanceSystem()
	{
		super(Aspect.all(Active.class,
				MovementDirection.class,
				Position.class,
				Velocity.class,
				MovementState.class,
				ActionsQueue.class,
				PerformerEmote.class),
				(1/60f));
	}
	
	public void updateCharacterEmotes(int entityId, EmoticonAtlas.Emoticon emote)
	{
		PerformerEmote performerEmote = mEmotion.get(entityId);
		
		performerEmote.hasEmote = true;
		performerEmote.emote = emote;
		//if(emote.isAnimated())
		//{
		//	performerEmote.animatedEmoticon = EmoticonAtlas.getInstance().getAnimatedEmoticon(emote);
		//}
		//else
		//{
			performerEmote.emoticon = EmoticonAtlas.getInstance().getEmoticon(emote);
		//}
	}
	
	public void resetCharacterEmotes(int entityId)
	{
		PerformerEmote performerEmote = mEmotion.get(entityId);
		
		performerEmote.hasEmote = false;
		performerEmote.emote = EmoticonAtlas.Emoticon.NONE;
		performerEmote.emoticon = EmoticonAtlas.getInstance().getEmoticon(performerEmote.emote);
	}
	
	public void updateCharacterActions(int entityId, Array<ActionToPerform> stageDirections)
	{
		ActionsQueue directions = mStageDirections.get(entityId);
		
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
		ActionsQueue stageDirections = mStageDirections.get(entityId);
		
		//DrawableSprite sprite = mSprite.get(entityId);
		
		//Name name = mName.get(entityId);
		
		if(!stageDirections.stageDirectionQueue.isEmpty())
		{
			if(!stageDirections.actionInProgress)
			{
				stageDirections.actionInProgress = true;
				stageDirections.currentStageDirection = stageDirections.stageDirectionQueue.removeFirst();
				//Gdx.app.debug(TAG, "Now executing action " +
				//		stageDirections.currentStageDirection.getAction() + " " +
				//		stageDirections.currentStageDirection.getDirection() +
				//		" for actor " + name.name);
				
				switch(stageDirections.currentStageDirection.getAction())
				{
				case WALK:
					direction.currentDirection = stageDirections.currentStageDirection.getDirection();
					
					Vector2 normalizedDest = StageMap.getPositionNormalized(stageDirections.currentStageDirection.getDestination());
					position.actionDestination = new Vector2(normalizedDest);
					movementState.moveRequested = true;
					
					
					//Gdx.app.debug(TAG, "\t\tTo position (" +
					//		stageDirections.currentStageDirection.getDestination().x + ", " +
					//		stageDirections.currentStageDirection.getDestination().y + ")");
					
					break;
					
				case REFACE:
					direction.refaceDirection = stageDirections.currentStageDirection.getDirection();
					movementState.refaceRequested = true;
					
					//Gdx.app.debug(TAG, "\t\tTo direction " + stageDirections.currentStageDirection.getDirection());
					
					break;
					
				case DISAPPEAR:
					movementState.currentState = State.DISAPPEAR;
					break;
					
				default:
					movementState.currentState = State.IDLE;
					break;
				}
			}
			else
			{
				if(position.hasReachedDestTile() && !movementState.refaceInProgress && !movementState.moveInProgress)
				//if(position.hasReachedDestTile())
				{
					stageDirections.actionEnded = true;
					
					position.zHasChanged = false;
					
					//Gdx.app.debug(TAG, "Have now ended action " +
					//		stageDirections.currentStageDirection.getAction() + " " +
					//		stageDirections.currentStageDirection.getDirection() + " for actor " +
					//		name.name);
					
					//if(stageDirections.stageDirectionQueue.notEmpty())
					//{
					stageDirections.actionInProgress = false;
					//}
				}
			}
		}
	}
}
