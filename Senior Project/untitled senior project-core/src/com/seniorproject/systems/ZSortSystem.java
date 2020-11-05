package com.seniorproject.systems;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.Bag;
import com.seniorproject.components.Active;
import com.seniorproject.components.MapPosition;

public class ZSortSystem extends EntitySystem
{
	
	public ZSortSystem()
	{
		super(Aspect.all(Active.class,
				MapPosition.class));
	}

	@Override
	protected void processSystem()
	{
		// TODO Auto-generated method stub
		
	}
	
	public Bag<Entity> getEntitiesByZIndex(int index)
	{	
		Bag<Entity> bag = new Bag<Entity>();
		
		for(int i = 0; i < getEntities().size(); i++)
		{
			Entity e = getEntities().get(i);
			if(e.getComponent(MapPosition.class).mapZIndex == index)
			{
				bag.add(e);
			}
		}
		
		return bag;
	}

}
