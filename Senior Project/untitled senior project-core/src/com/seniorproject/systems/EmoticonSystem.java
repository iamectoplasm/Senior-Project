package com.seniorproject.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IntervalIteratingSystem;
import com.seniorproject.components.Active;
import com.seniorproject.components.PerformerEmote;
import com.seniorproject.components.PerformerSprite;
import com.seniorproject.components.Position;

public class EmoticonSystem extends IntervalIteratingSystem
{
	public static final String TAG = CollisionSystem.class.getSimpleName();

	protected ComponentMapper<Position> mPosition;
	protected ComponentMapper<PerformerSprite> mSprite;
	protected ComponentMapper<PerformerEmote> mEmotion;

	public EmoticonSystem()
	{
		super(Aspect.all(Active.class,
				Position.class,
				PerformerSprite.class,
				PerformerEmote.class), (1/60f));
	}

	@Override
	protected void process(int entityId)
	{
		updateRenderPosition(entityId);
	}
	
	protected void updateRenderPosition(int entityId)
	{
		PerformerEmote emoticon = mEmotion.get(entityId);
		Position position = mPosition.get(entityId);
		PerformerSprite sprite = mSprite.get(entityId);
		
		emoticon.xPos = position.currentPosition.x;
		emoticon.yPos = position.currentPosition.y + sprite.drawHeight;
	}

}
