package com.seniorproject.configs;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;
import com.seniorproject.enums.AnimationType;

public class AnimationConfig
{
	private Array<AnimationData> walkAnimationData;
	private Array<AnimationData> specialAnimationData;
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
		this.walkAnimationData = new Array<AnimationData>();
		this.specialAnimationData = new Array<AnimationData>();
	}
	
	public static class AnimationData
	{
		private AnimationType animationType;
		private Array<GridPoint2> gridPoints;
		
		public AnimationData()
		{
			animationType = AnimationType.WALK_DOWN;
			gridPoints = new Array<GridPoint2>();
		}
		
		public AnimationType getAnimationType()
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
	public Array<AnimationData> getWalkAnimationData()
	{
		return this.walkAnimationData;
	}
	
	public Array<AnimationData> getSpecialAnimationData()
	{
		return this.specialAnimationData;
	}
	
	public TextureRegion getAnimationTexture()
	{
		return this.animationTexture;
	}
}
