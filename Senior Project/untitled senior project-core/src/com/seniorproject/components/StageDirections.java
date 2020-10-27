package com.seniorproject.components;

import com.artemis.Component;
import com.badlogic.gdx.utils.Queue;
import com.seniorproject.configs.BlockingConfig.Actions;

public class StageDirections extends Component
{
	public Queue<Actions> stageDirectionQueue;
	
	//public int indexOfActionInProgress = 0;
	public Actions currentStageDirection = null;
	
	public boolean actionBegan = false;
	public boolean actionInProgress = false;
	public boolean actionEnded = false;
	
	public StageDirections()
	{
		stageDirectionQueue = new Queue<Actions>();
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
