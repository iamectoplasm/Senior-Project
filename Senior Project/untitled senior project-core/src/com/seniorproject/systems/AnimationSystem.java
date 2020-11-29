package com.seniorproject.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IntervalIteratingSystem;
import com.seniorproject.components.*;
import com.seniorproject.components.MovementDirection.Direction;
import com.seniorproject.enums.AnimationType;
import com.seniorproject.enums.State;

public class AnimationSystem extends IntervalIteratingSystem
{
	ComponentMapper<MovementDirection> mDirection;
	ComponentMapper<MovementState> mMovementState;
	ComponentMapper<MovementAnimation> mAnimation;
	ComponentMapper<DrawableSprite> mSprite;
	ComponentMapper<Velocity> mVelocity;

	public AnimationSystem()
	{
		super(Aspect.all(Active.class,
				MovementDirection.class,
				Velocity.class,
				MovementState.class,
				MovementAnimation.class,
				DrawableSprite.class),
				(1/60f));
	}

	@Override
	protected void process(int entityId)
	{
		updateAnimations(entityId);
	}
	
	float delta = 0;

	protected void updateAnimations(int entityId)
	{
		MovementAnimation movementAnim = mAnimation.get(entityId);
		DrawableSprite sprite = mSprite.create(entityId);
		
		if(mMovementState.get(entityId).currentState == State.DISAPPEAR)
		{
			delta += world.getDelta();
			sprite.currentFrame = movementAnim.otherAnimations.get(AnimationType.DISAPPEAR_ANIM).getKeyFrame(delta);
		}
		else
		{
		
		Direction currentDir = mDirection.get(entityId).currentDirection;

		switch (currentDir)
		{
		case DOWN:
			MovementAnimation.WalkAnimation downAnimation = movementAnim.animations.get(AnimationType.WALK_DOWN);
			
			if(mMovementState.get(entityId).moveInProgress)
			{
				sprite.currentFrame = downAnimation.walk(world.getDelta());
			}
			else if(mMovementState.get(entityId).refaceInProgress)
			{
				sprite.currentFrame = downAnimation.reface(world.getDelta());
			}
			else
			{
				sprite.currentFrame = downAnimation.getNextStandingFrame();
			}
			
			break;

		case UP:
			MovementAnimation.WalkAnimation upAnimation = movementAnim.animations.get(AnimationType.WALK_UP);
			
			if(mMovementState.get(entityId).moveInProgress)
			{
				sprite.currentFrame = upAnimation.walk(world.getDelta());
			}
			else if(mMovementState.get(entityId).refaceInProgress)
			{
				sprite.currentFrame = upAnimation.reface(world.getDelta());
			}
			else
			{
				sprite.currentFrame = upAnimation.getNextStandingFrame();
			}
			
			break;

		case LEFT:
			MovementAnimation.WalkAnimation leftAnimation = movementAnim.animations.get(AnimationType.WALK_LEFT);
			
			if(mMovementState.get(entityId).moveInProgress)
			{
				sprite.currentFrame = leftAnimation.walk(world.getDelta());
			}
			else if(mMovementState.get(entityId).refaceInProgress)
			{
				sprite.currentFrame = leftAnimation.reface(world.getDelta());
			}
			else
			{
				sprite.currentFrame = leftAnimation.getNextStandingFrame();
			}
			
			break;

		case RIGHT:
			MovementAnimation.WalkAnimation rightAnimation = movementAnim.animations.get(AnimationType.WALK_RIGHT);
			
			if(mMovementState.get(entityId).moveInProgress)
			{
				sprite.currentFrame = rightAnimation.walk(world.getDelta());
			}
			else if(mMovementState.get(entityId).refaceInProgress)
			{
				sprite.currentFrame = rightAnimation.reface(world.getDelta());
			}
			else
			{
				sprite.currentFrame = rightAnimation.getNextStandingFrame();
			}
			
			break;

		default:
			mSprite.get(entityId).currentFrame = movementAnim.animations.get(AnimationType.WALK_DOWN)
					.getNextStandingFrame();
			break;
		}
		}
	}
}
