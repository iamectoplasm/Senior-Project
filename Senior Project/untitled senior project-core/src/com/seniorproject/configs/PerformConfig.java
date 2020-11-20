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
	
	private SceneSetup sceneSetup;
	private Array<ActionsForLine> lineActions;
	
	/*
	 * = = = = = = = = = = = = = = = = = = = =
	 * 
	 * - PerformConfig Constructor
	 * 
	 * = = = = = = = = = = = = = = = = = = = =
	 */
	
	public PerformConfig()
	{
		//this.sceneSetup = new Array<SceneSetup>();
		this.sceneSetup = new SceneSetup();
		this.lineActions = new Array<ActionsForLine>();
	}
	
	public SceneSetup getSetup()
	{
		return sceneSetup;
	}

	public Array<ActionsForLine> getActionsForLine()
	{
		return lineActions;
	}
	
	/*
	 * = = = = = = = = = = = = = = = = = = = =
	 * 
	 * - Setup Classes
	 * 
	 * = = = = = = = = = = = = = = = = = = = =
	 */
	
	public static class SceneSetup
	{
		private Array<ActorSetup> actorSetup;
		private Array<PropSetup> propSetup;
		
		public SceneSetup()
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
		{
			return actor;
		}

		public Vector2 getStartPosition()
		{
			return startPosition;
		}
		
		public StageLayer getStageLayer()
		{
			return stageLayer;
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
	
	/*
	 * = = = = = = = = = = = = = = = = = = = =
	 * 
	 * - Actions per line classes
	 * 
	 * = = = = = = = = = = = = = = = = = = = =
	 */
	public static class ActionsForLine
	{
		private int lineID;
		private Array<SharedLineActions> sharedLineActions;
		private Array<ActionsForPerformer> actorActions;
		private Array<ActionsForProp> propActions;
		
		public ActionsForLine()
		{
			this.actorActions = new Array<ActionsForPerformer>();
			this.propActions = new Array<ActionsForProp>();
		}
		
		public int getLineID()
		{
			return lineID;
		}
		
		public Array<SharedLineActions> getSharedLineActions()
		{
			return sharedLineActions;
		}
		
		public Array<ActionsForPerformer> getActionsForPerformer()
		{
			return actorActions;
		}
		
		public Array<ActionsForProp> getActionsForProp()
		{
			return propActions;
		}
	}
	
	public static class SharedLineActions
	{
		private int sharedIndex;
		private Array<ActionsForPerformer> actorActions;
		private Array<ActionsForProp> propActions;
		
		public SharedLineActions()
		{
			this.actorActions = new Array<ActionsForPerformer>();
			this.propActions = new Array<ActionsForProp>();
		}
		
		public int getSharedIndex()
		{
			return sharedIndex;
		}

		public Array<ActionsForPerformer> getActions()
		{
			return actorActions;
		}

		public Array<ActionsForProp> getPropActions()
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
			this.emote = EmoticonAtlas.Emoticon.NONE;
			this.actions = new Array<ActionToPerform>();
		}
		
		public CharacterName getActor()
		{
			return actor;
		}
		
		public EmoticonAtlas.Emoticon getEmoticon()
		{
			return emote;
		}
		
		public Array<ActionToPerform> getActions()
		{
			return actions;
		}
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
		{
			return prop;
		}
		
		public Array<ActionForProp> getActions()
		{
			return actions;
		}
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
		{
			return action;
		}
		
		public MovementDirection.Direction getDirection()
		{
			return direction;
		}
		
		public Vector2 getDestination()
		{
			return destination;
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
