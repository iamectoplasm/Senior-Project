package com.seniorproject.components;

import com.artemis.Component;
import com.seniorproject.enums.Prop;

public class PropComponent extends Component
{
	public Prop prop;
	
	public PropAction currentAction;
	
	public static enum PropAction
	{
		SET_OVERLAY,
		CLEAR_OVERLAY,
		PLAY_ANIMATION,
		SHOW,
		HIDE;
	}
}
