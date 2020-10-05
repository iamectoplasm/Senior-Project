package com.seniorproject.components;

import java.util.Hashtable;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;
import com.seniorproject.game.AnimationConfig;

public class MovementAnimation extends Component
{
	public Hashtable<AnimationType, Animation> animations = new Hashtable<AnimationType, Animation>();
	//public float animationTimer = 0.0f;
	public boolean playWalkAnim = false;
	
	public int frameWidth = 32;
	public int frameHeight = 32;

	public static enum AnimationType
	{
		WALK_LEFT, WALK_RIGHT, WALK_UP, WALK_DOWN,
		
		REFACE_LEFT, REFACE_RIGHT, REFACE_UP, REFACE_DOWN
	}
	
	public static class Animation
	{
		private int currentFrameIndex;
		private Array<TextureRegion> frames;

		private float walkFrameDuration;
		private float refaceFrameDuration;
		private float frameTime;
		
		public Animation(Array<TextureRegion> keyFrames, float velocity)
		{
			frames = keyFrames;
			currentFrameIndex = 0;

			this.walkFrameDuration = velocity / 2;
			//this.refaceFrameDuration = velocity / 4;
			this.refaceFrameDuration = .15f;
			frameTime = 0;
		}
		
		public TextureRegion getCurrentFrame()
		{
			return frames.get(currentFrameIndex);
		}

		public float getWalkFrameDuration()
		{
			return walkFrameDuration;
		}
		
		public float getRefaceFrameDuration()
		{
			return refaceFrameDuration;
		}

		public int getCurrentFrameIndex()
		{
			return currentFrameIndex;
		}

		public TextureRegion getNextFrame()
		{
			TextureRegion nextFrame = null;

			if (currentFrameIndex == 3)
				currentFrameIndex = 0;
			else
				currentFrameIndex += 1;

			// Gdx.app.debug(TAG, "getNextFrame called: " + currentFrameIndex);
			nextFrame = frames.get(currentFrameIndex);
			return nextFrame;
		}

		public TextureRegion getFrameAtIndex(int index)
		{
			if (index > 3 || index < 0)
			{
				return frames.get(currentFrameIndex);
			}
			currentFrameIndex = index;
			return frames.get(currentFrameIndex);
		}

		public TextureRegion getNextStandingFrame()
		{
			resetFrameTime();

			if (currentFrameIndex == 0)
			{
				return frames.get(0);
			}
			else if (currentFrameIndex == 2)
			{
				return frames.get(2);
			}
			else
			{
				return getNextFrame();
			}
		}

		public TextureRegion getNextWalkingFrame()
		{
			if (currentFrameIndex == 1)
				return frames.get(1);
			else if (currentFrameIndex == 3)
				return frames.get(3);
			else
				return getNextFrame();
		}

		public boolean characterIsStanding()
		{
			if (currentFrameIndex % 2 == 0)
			{
				return true;
			}
			else
				return false;
		}

		public boolean characterIsWalking()
		{
			if (currentFrameIndex % 2 == 1)
			{
				return true;
			}
			
			else
				return false;
		}

		public void resetFrameTime()
		{
			frameTime = 0;
		}

		public float getFrameTime()
		{
			return frameTime;
		}

		public TextureRegion walk(float delta)
		{
			accumulateDelta(delta);

			if (frameTime >= walkFrameDuration)
			{
				resetFrameTime();
				return getNextFrame();
			}

			return getCurrentFrame();
		}
		
		public TextureRegion reface(float delta)
		{
			accumulateDelta(delta);
			
			if(frameTime >= refaceFrameDuration)
			{
				resetFrameTime();
				return getNextFrame();
			}
			
			return getNextWalkingFrame();
		}

		private void accumulateDelta(float delta)
		{
			frameTime = frameTime + delta;
		}
	}
}
