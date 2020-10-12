package com.seniorproject.scripting;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;
import com.seniorproject.components.MovementAnimation;
import com.seniorproject.components.MovementAnimation.AnimationType;

public class AnimationConfig
{
	private Array<AnimationData> animationData;
	private TextureRegion animationTexture;

	/*
	 * = = = = = = = = = = = = = = = = = = = =
	 * 
	 * - AnimationConfig constructor
	 * 
	 * = = = = = = = = = = = = = = = = = = = =
	 */
	public AnimationConfig()
	{
		//this.animationTexture = animationTexture;
		this.animationData = new Array<AnimationData>();
	}
	
	public static class AnimationData
	{
		private MovementAnimation.AnimationType animationType;
		private Array<GridPoint2> gridPoints;
		
		public AnimationData()
		{
			animationType = AnimationType.WALK_DOWN;
			gridPoints = new Array<GridPoint2>();
		}
		
		public MovementAnimation.AnimationType getAnimationType()
		{
			return animationType;
		}
		
		public Array<GridPoint2> getGridPoints()
		{
			return gridPoints;
		}
		
		public void setGridPoints(Array<GridPoint2> gridPoints)
		{
			this.gridPoints = gridPoints;
		}

		public void setAnimationType(AnimationType animationType)
		{
			this.animationType = animationType;
		}
	}

	/*
	 * = = = = = = = = = = = = = = = = = = = =
	 * 
	 * - AnimationConfig getters
	 * 
	 * = = = = = = = = = = = = = = = = = = = =
	 */
	public Array<AnimationData> getAnimationData()
	{
		return this.animationData;
	}
	
	public TextureRegion getAnimationTexture()
	{
		return this.animationTexture;
	}
}
