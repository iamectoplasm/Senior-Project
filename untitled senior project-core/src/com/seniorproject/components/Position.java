package com.seniorproject.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;

public class Position extends Component
{
	// private int cellX = 0;
	// private int cellY = 0;
	public int cellX;
	public int cellY;

	public Vector2 startingPosition = new Vector2(0, 0);
	public Vector2 currentPosition = new Vector2(0, 0);
	public Vector2 destinationPosition = new Vector2(0, 0);
	
	public Vector2 actionDestination = new Vector2(0, 0);
	
	public float xOffset = -(1/2f);
	public float yOffset = (1/4f);

	public void setStartPosition()
	{
		this.startingPosition = new Vector2(cellX, cellY);
	}

	public void setCurrentPosition(Vector2 current)
	{
		cellX = (int) current.x;
		cellY = (int) current.y;
		this.currentPosition = roundVector(current);
		// cellX = (int) newPosition.x;
		// cellY = (int) newPosition.y;
	}

	public void resetAllToStarting()
	{
		currentPosition.set(startingPosition);
		destinationPosition.set(startingPosition);
		cellX = (int) startingPosition.x;
		cellY = (int) startingPosition.y;
	}

	public void snapToCurrentToCell()
	{
		this.currentPosition = new Vector2(cellX, cellY);
		startingPosition.set(currentPosition);
		destinationPosition.set(currentPosition);
	}

	public void setDestPosition(Vector2 dest)
	{
		this.destinationPosition = roundVector(dest);
	}
	
	public Vector2 roundVector(Vector2 vector)
	{
		float roundedX = (float) (Math.round(vector.x * Math.pow(10, 2)) / Math.pow(10, 2));
		float roundedY = (float) (Math.round(vector.y * Math.pow(10, 2)) / Math.pow(10, 2));

		return new Vector2(roundedX, roundedY);
	}
	
	public boolean hasReachedDestTile()
	{
		int currentX = (int) currentPosition.x;
		int currentY = (int) currentPosition.y;
		
		int destX = (int) actionDestination.x;
		int destY = (int) actionDestination.y;
		
		return (destX == currentX) && (destY == currentY);
	}

	// public int getCellX()
	// {
	// return this.cellX;
	// }

	// public int getCellY()
	// {
	// return this.cellY;
	// }
}
