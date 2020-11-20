package com.seniorproject.configs;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.seniorproject.components.MovementDirection;
import com.seniorproject.components.MovementState;
import com.seniorproject.components.PropComponent;
import com.seniorproject.enums.CharacterName;
import com.seniorproject.enums.Prop;
import com.seniorproject.enums.StageLayer;
import com.seniorproject.enums.State;
import com.seniorproject.game.EmoticonAtlas;;

public class PerformConfig
{
	//private static final String TAG = ActionsConfig.class.getSimpleName();
	
	//private Array<SceneSetup> sceneSetup;
	private Setup sceneSetup;
	private Array<ActionsForLine> lineActions;
	
	public PerformConfig()
	{
		//this.sceneSetup = new Array<SceneSetup>();
		this.sceneSetup = new Setup();
		this.lineActions = new Array<ActionsForLine>();
	}
	
	public Setup getSetup()
	{
		return sceneSetup;
	}
	
	//public Array<SceneSetup> getSceneSetup()
	//{ return sceneSetup; }

	//public void setSceneSetup(Array<SceneSetup> sceneSetup)
	//{ this.sceneSetup = sceneSetup; }

	public Array<ActionsForLine> getActionsForLine()
	{ return lineActions; }

	public void setActionsForLine(Array<ActionsForLine> blockingList)
	{ this.lineActions = blockingList; }
	
	public static class Setup
	{
		private Array<ActorSetup> actorSetup;
		private Array<PropSetup> propSetup;
		
		public Setup()
		{
			actorSetup = new Array<ActorSetup>();
			propSetup = new Array<PropSetup>();
		}
		
		public Array<ActorSetup> getActorSetup()
		{
			return actorSetup;
		}
		
		public Array<PropSetup> getPropSetup()
		{
			return propSetup;
		}
	}
	
	public static class ActorSetup
	{
		private CharacterName actor;
		private Vector2 startPosition;
		private StageLayer stageLayer;
		
		public ActorSetup()
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
	
	public static class PropSetup
	{
		private Prop prop;
		
		public PropSetup()
		{
			prop = null;
		}
		
		public Prop getProp()
		{
			return prop;
		}
	}
	
	public static class ActionsForLine
	{
		private int lineID;
		private Array<ActionsForPerformer> actorActions;
		private Array<ActionsForProp> propActions;
		
		public ActionsForLine()
		{
			this.actorActions = new Array<ActionsForPerformer>();
			this.propActions = new Array<ActionsForProp>();
		}
		
		public int getLineID()
		{ return lineID; }
		
		public void setLineID(int lineID)
		{ this.lineID = lineID; }
		
		public Array<ActionsForPerformer> getActionsForPerformer()
		{ return actorActions; }
		
		public void setActiondForPerformer(Array<ActionsForPerformer> blocking)
		{ this.actorActions = blocking; }
		
		public Array<ActionsForProp> getActionsForProp()
		{
			return propActions;
		}
		
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
	
	public static class ActionsForProp
	{
		private Prop prop;
		private Array<ActionForProp> actions;
		
		public ActionsForProp()
		{
			this.prop = null;
			this.actions = new Array<ActionForProp>();
		}
		
		public Prop getProp()
		{ return prop; }
		
		public Array<ActionForProp> getActions()
		{ return actions; }
	}
	
	public static class ActionToPerform
	{
		private State action;
		private MovementDirection.Direction direction;
		private Vector2 destination;
		
		public ActionToPerform()
		{
			this.action = State.IDLE;
			this.direction = MovementDirection.Direction.DOWN;
			this.destination = new Vector2();
		}
		
		public State getAction()
		{ return action; }
		
		public void setAction(State action)
		{ this.action = action; }
		
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
	
	public static class ActionForProp
	{
		private PropComponent.PropAction action;
		private Vector2 location;
		
		public ActionForProp()
		{
			this.action = null;
			this.location = new Vector2();
		}
		
		public PropComponent.PropAction getAction()
		{ return action; }
		
		public Vector2 getLocation()
		{
			return location;
		}
	}
}
