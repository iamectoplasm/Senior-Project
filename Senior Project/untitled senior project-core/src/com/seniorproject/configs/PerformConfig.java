package com.seniorproject.configs;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.seniorproject.components.MovementDirection;
import com.seniorproject.components.MovementState;
import com.seniorproject.enums.CharacterName;
import com.seniorproject.enums.StageLayer;
import com.seniorproject.game.EmoticonAtlas;;

public class PerformConfig
{
	//private static final String TAG = ActionsConfig.class.getSimpleName();
	
	private Array<Setup> sceneSetup;
	private Array<ActionsForLine> lineActions;
	
	public PerformConfig()
	{
		this.sceneSetup = new Array<Setup>();
		this.lineActions = new Array<ActionsForLine>();
	}
	
	public Array<Setup> getSceneSetup()
	{ return sceneSetup; }

	public void setSceneSetup(Array<Setup> sceneSetup)
	{ this.sceneSetup = sceneSetup; }

	public Array<ActionsForLine> getActionsForLine()
	{ return lineActions; }

	public void setActionsForLine(Array<ActionsForLine> blockingList)
	{ this.lineActions = blockingList; }
	
	public static class Setup
	{
		private CharacterName actor;
		private Vector2 startPosition;
		private StageLayer stageLayer;
		
		public Setup()
		{
			actor = CharacterName.SERGEANT;
			startPosition = new Vector2(0, 0);
			stageLayer = StageLayer.MAIN_STAGE;
		}
		
		public CharacterName getActor()
		{ return actor; }

		public void setActor(CharacterName actor)
		{ this.actor = actor; }

		public Vector2 getStartPosition()
		{ return startPosition; }

		public void setStartPosition(Vector2 startPosition)
		{ this.startPosition = startPosition; }
		
		public StageLayer getStageLayer()
		{
			return stageLayer;
		}
		
		public void setStageLayer(StageLayer layer)
		{
			this.stageLayer = layer;
		}
	}
	
	public static class ActionsForLine
	{
		private int lineID;
		private Array<ActionsForPerformer> actorActions;
		
		public ActionsForLine()
		{
			this.actorActions = new Array<ActionsForPerformer>();
		}
		
		public int getLineID()
		{ return lineID; }
		
		public void setLineID(int lineID)
		{ this.lineID = lineID; }
		
		public Array<ActionsForPerformer> getActionsForPerformer()
		{ return actorActions; }
		
		public void setActiondForPerformer(Array<ActionsForPerformer> blocking)
		{ this.actorActions = blocking; }
		
	}
	
	public static class ActionsForPerformer
	{
		private CharacterName actor;
		private EmoticonAtlas.Emoticon emote;
		private Array<ActionToPerform> actions;
		
		public ActionsForPerformer()
		{
			this.actor = CharacterName.SERGEANT;
			this.emote = null;
			this.actions = new Array<ActionToPerform>();
		}
		
		public CharacterName getActor()
		{ return actor; }
		
		public void setActor(CharacterName actor)
		{ this.actor = actor; }
		
		public Array<ActionToPerform> getActions()
		{ return actions; }
		
		public void setActions(Array<ActionToPerform> directions)
		{ this.actions = directions; }
		
		public EmoticonAtlas.Emoticon getEmoticon()
		{
			return emote;
		}
		
		public void setEmoticon(EmoticonAtlas.Emoticon emoticon)
		{
			this.emote = emoticon;
		}
		
		//public TextureRegion getEmoticonTexture()
		//{
		//	return emote.getIcon();
		//}
	}
	
	public static class ActionToPerform
	{
		private MovementState.State action;
		private int tiles;
		private MovementDirection.Direction direction;
		private Vector2 destination;
		
		public ActionToPerform()
		{
			this.action = MovementState.State.IDLE;
			this.direction = MovementDirection.Direction.DOWN;
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
		
		public MovementDirection.Direction getDirection()
		{ return direction; }
		
		public void setDirection(MovementDirection.Direction direction)
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
