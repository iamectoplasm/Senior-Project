package com.seniorproject.components;

import com.artemis.Component;
import com.badlogic.gdx.math.MathUtils;

public class Direction extends Component
{
	public Directions currentDirection = Directions.DOWN;
	public Directions refaceDirection = null;

	public static enum Directions {
		UP(0, 1), DOWN(0, -1), RIGHT(1, 0), LEFT(-1, 0);

		private int dx, dy;

		private Directions(int dx, int dy) {
			this.dx = dx;
			this.dy = dy;
		}

		public int getDX() {
			return dx;
		}

		public int getDY() {
			return dy;
		}

		static public Directions getRandomNext() {
			return Directions.values()[MathUtils.random(Directions.values().length - 1)];
		}

		public Directions getOpposite() {
			if (this == LEFT)
				return RIGHT;
			else if (this == RIGHT)
				return LEFT;
			else if (this == UP)
				return DOWN;
			else {
				return UP;
			}
		}
	}
}
