package com.seniorproject.components;

import com.artemis.Component;

public class Velocity extends Component
{
	//public float velocity = 0.5f;
	public Speed speed;
	
	public static enum Speed
	{
		SLOW(0.5F),
		MED(0.25f),
		FAST(0.15f);
		
		float duration;
		
		private Speed(float duration)
		{
			this.duration = duration;
		}
		
		public float getDuration()
		{
			return duration;
		}
	}
}
