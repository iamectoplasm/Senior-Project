package com.seniorproject.components;

import com.artemis.Component;
import com.badlogic.gdx.utils.Queue;
import com.seniorproject.scripting.ActionsConfig.Action;

public class Actions extends Component
{
	public Queue<Action> actionQueue;
	
	//public int indexOfActionInProgress = 0;
	public Action currentAction = null;
	
	public boolean actionBegan = false;
	public boolean actionInProgress = false;
	public boolean actionEnded = false;
	
	public Actions()
	{
		actionQueue = new Queue<Action>();
	}
	
	//public void resetActionsArray()
	//{
	//	actionQueue.clear();
	//}
}
