package com.seniorproject.game;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.seniorproject.components.Actions;
import com.seniorproject.components.Active;
import com.seniorproject.components.MovementState;
import com.seniorproject.components.Name;
import com.seniorproject.components.Position;
import com.seniorproject.enums.CharacterName;

public class PerformerManager
{
	private static final String TAG = PerformerManager.class.getSimpleName();
	
	private static World world;
	
	private static Array<Entity> performers;
	
	private static PerformerManager instance = null;
	public static PerformerManager getInstance()
	{
		if (instance == null)
		{
			instance = new PerformerManager();
		}

		return instance;
	}
	
	private PerformerManager()
	{
		performers = new Array<Entity>();
		
		for(int i = 0; i < CharacterName.values().length; i++)
		{
			performers.add(EntityFactory.getPerformerEntity(CharacterName.values()[i]));
			Gdx.app.debug(TAG, "Creating performer " + performers.get(performers.size - 1).getComponent(Name.class).name);
		}
	}
	
	public static void setWorld(World setWorld)
	{
		world = setWorld;
	}
	
	public static Entity activatePerformer(CharacterName name)
	{
		for(int i = 0; i < performers.size; i++)
		{
			if(performers.get(i).getComponent(Name.class).name.equals(name.name()))
			{
				performers.get(i).edit().add(new Active());
				performers.get(i).getComponent(Actions.class).resetActionsArray();
				performers.get(i).getComponent(MovementState.class).resetAll();
				return performers.get(i);
			}
		}
		
		return null;
	}
	
	public static void deactivatePerformer(CharacterName name)
	{
		for(int i = 0; i < performers.size; i++)
		{
			if(performers.get(i).getComponent(Name.class).name.equals(name.name()))
			{
				performers.get(i).edit().remove(Active.class);
				//performers.get(i).getComponent(Actions.class).resetActionsArray();
				//performers.get(i).getComponent(MovementState.class).resetAll();
			}
		}
	}
}
