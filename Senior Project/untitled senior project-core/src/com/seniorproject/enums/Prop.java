package com.seniorproject.enums;

public enum Prop
{
	CAMP_TABLE(PropType.FURNITURE),
	GHOST_DAGGER(PropType.OBJECT),
	SOLILOQUY_LIGHTING(PropType.LIGHTING),
	DINING_TABLE(PropType.FURNITURE),
	DINING_CHAIR(PropType.FURNITURE);
	
	private PropType type;
	
	private Prop(PropType type)
	{
		this.type = type;
	}
	
	public PropType getType()
	{
		return type;
	}
	
	public static PropType getPropType(Prop prop)
	{
		switch(prop)
		{
		case CAMP_TABLE:
			return PropType.FURNITURE;
		case GHOST_DAGGER:
			return PropType.OBJECT;
		case SOLILOQUY_LIGHTING:
			return PropType.LIGHTING;
		case DINING_TABLE:
			return PropType.FURNITURE;
		case DINING_CHAIR:
			return PropType.FURNITURE;
			default:
			return null;
		}
		
			
	}
	
	public static enum PropType
	{
		// a LIGHTING prop type is one that will be created as an overlay to go over the map-- ie overlay
		// to show night-time, overlay to show fog, overlay to focus on one speaker with a soliloquy
		LIGHTING,
				
		// an OBJECT prop is going to be one that is drawn on the scene2d stage, can just be a small object or something,
		// but that can move around the stage and be animated
		OBJECT,
				
		// a FURNITURE object will be an object that gets drawn onto the stage and stays there for the whole scene,
		// that gets organized along with the actors so that they can walk in front of/around it
		FURNITURE
	}
}
