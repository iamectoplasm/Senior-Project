package com.seniorproject.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;

public class Position extends Component
{
	public Vector2 position;
	public float x;
	public float y;
	
	public Position()
	{
		position = new Vector2();
		x = 0;
		y = 0;
	}
}
