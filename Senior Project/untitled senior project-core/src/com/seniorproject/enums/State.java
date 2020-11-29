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
	
	static public State getRandomNext()
	{
		// Ignore IMMOBILE which should be last state
		return State.values()[MathUtils.random(State.values().length - 3)];
	}
}
