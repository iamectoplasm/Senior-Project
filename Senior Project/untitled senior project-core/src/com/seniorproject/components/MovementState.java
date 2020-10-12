package com.seniorproject.components;

import com.artemis.Component;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class MovementState extends Component
{
	public State currentState = State.IDLE;
	public boolean moveRequested = false;
	public boolean moveInProgress = false;
	
	public boolean refaceRequested = false;
	public boolean refaceInProgress = false;
	
	//public int tilesCount = 0;

	public float stateTime = 0.0f;
	
	public boolean pauseMovement = false;
	
	public void resetStateTime()
	{
		stateTime = 0;
	}
	
	public static enum State
	{
		IDLE,
		WALK,
		REFACE,
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
}
