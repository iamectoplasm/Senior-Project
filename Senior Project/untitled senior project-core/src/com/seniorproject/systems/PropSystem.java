package com.seniorproject.systems;

import com.artemis.Aspect;
import com.artemis.systems.IntervalIteratingSystem;
import com.seniorproject.components.Active;
import com.seniorproject.components.StagePosition;
import com.seniorproject.components.Position;
import com.seniorproject.components.PropComponent;
import com.seniorproject.components.ActionsQueue;

public class PropSystem extends IntervalIteratingSystem
{
	private final static String TAG = PerformanceSystem.class.getSimpleName();

	public PropSystem()
	{
		super(Aspect.all(Active.class,
				Position.class,
				PropComponent.class),
				(1/60f));
	}

	@Override
	protected void process(int entityId)
	{
		
	}

}
