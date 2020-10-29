package com.seniorproject.components;

import com.artemis.Component;
import com.badlogic.gdx.utils.Queue;
import com.seniorproject.configs.PerformConfig.ActionToPerform;

public class StageDirections extends Component
{
	public Queue<ActionToPerform> stageDirectionQueue;
	
	//public int indexOfActionInProgress = 0;
	public ActionToPerform currentStageDirection = null;
	
	public boolean actionBegan = false;
	public boolean actionInProgress = false;
	public boolean actionEnded = false;
	
	public StageDirections()
	{
		stageDirectionQueue = new Queue<ActionToPerform>();
	}
	
	public void resetActionsArray()
	{
		stageDirectionQueue.clear();
		currentStageDirection = null;
		actionBegan = false;
		actionInProgress = false;
		actionEnded = false;
	}
}
