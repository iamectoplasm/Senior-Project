package com.seniorproject.enums;

import com.badlogic.gdx.math.MathUtils;

public enum State
{
	IDLE,
	WALK,
	REFACE,
	BOW,
	DISAPPEAR,
	IMMOBILE; // IMMOBILE should always be last
	
	//public float walkTime = 0.25f;
	public static float refaceTime = 0.15f;
	
	static public State getRandomNext()
	{
		// Ignore IMMOBILE which should be last state
		return State.values()[MathUtils.random(State.values().length - 3)];
	}
	
	public float getRefaceTime()
	{
		return refaceTime;
	}
}
