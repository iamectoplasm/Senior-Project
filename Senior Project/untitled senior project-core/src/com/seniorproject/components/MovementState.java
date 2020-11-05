package com.seniorproject.components;

import com.artemis.Component;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.seniorproject.enums.State;

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
	
	public void resetAll()
	{
		currentState = State.IDLE;
		moveRequested = false;
		moveInProgress = false;
		refaceRequested = false;
		refaceInProgress = false;
		stateTime = 0.0f;
	}
}
