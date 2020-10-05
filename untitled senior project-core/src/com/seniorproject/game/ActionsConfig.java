package com.seniorproject.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.seniorproject.components.Direction;
import com.seniorproject.components.MovementState;
import com.seniorproject.game.EntityFactory.PerformerName;

public class ActionsConfig
{
	private static final String TAG = ActionsConfig.class.getSimpleName();
	
	private Array<Setup> sceneSetup;
	private Array<LineAction> lineActions;
	
	public ActionsConfig()
	{
		this.sceneSetup = new Array<Setup>();
		this.lineActions = new Array<LineAction>();
	}
	
	public Array<Setup> getSceneSetup()
	{ return sceneSetup; }

	public void setSceneSetup(Array<Setup> sceneSetup)
	{ this.sceneSetup = sceneSetup; }

	public Array<LineAction> getLineActions()
	{ return lineActions; }

	public void setLineActions(Array<LineAction> actionsList)
	{ this.lineActions = actionsList; }
	
	public static class Setup
	{
		private PerformerName actor;
		private Vector2 startPosition;
		
		public Setup()
		{
			actor = PerformerName.SERGEANT;
			startPosition = new Vector2(0, 0);
		}
		
		public PerformerName getActor()
		{ return actor; }

		public void setActor(PerformerName actor)
		{ this.actor = actor; }

		public Vector2 getStartPosition()
		{ return startPosition; }

		public void setStartPosition(Vector2 startPosition)
		{ this.startPosition = startPosition; }
	}
	
	public static class LineAction
	{
		private int lineID;
		private Array<ActorAction> actorActions;
		
		public LineAction()
		{
			this.actorActions = new Array<ActorAction>();
		}
		
		public int getLineID()
		{ return lineID; }
		
		public void setLineID(int lineID)
		{ this.lineID = lineID; }
		
		public Array<ActorAction> getActorActions()
		{ return actorActions; }
		
		public void setActorActions(Array<ActorAction> actions)
		{ this.actorActions = actions; }
		
	}
	
	public static class ActorAction
	{
		private PerformerName actor;
		private Array<Action> actions;
		
		public ActorAction()
		{
			this.actor = PerformerName.SERGEANT;
			this.actions = new Array<Action>();
		}
		
		public PerformerName getActor()
		{ return actor; }
		
		public void setActor(PerformerName actor)
		{ this.actor = actor; }
		
		public Array<Action> getActions()
		{ return actions; }
		
		public void setActions(Array<Action> actions)
		{ this.actions = actions; }
	}
	
	public static class Action
	{
		private MovementState.State action;
		private int tiles;
		private Direction.Directions direction;
		private Vector2 destination;
		
		public Action()
		{
			this.action = MovementState.State.IDLE;
			this.direction = Direction.Directions.DOWN;
			this.destination = new Vector2();
		}
		
		public MovementState.State getAction()
		{ return action; }
		
		public void setAction(MovementState.State action)
		{ this.action = action; }
		
		public int getTiles()
		{ return tiles; }
		
		public void setTiles(int tiles)
		{ this.tiles = tiles; }
		
		public Direction.Directions getDirection()
		{ return direction; }
		
		public void setDirection(Direction.Directions direction)
		{ this.direction = direction; }
		
		public Vector2 getDestination()
		{
			return destination;
		}
		
		public void setDestination(Vector2 destination)
		{
			this.destination = destination;
		}
	}
}
