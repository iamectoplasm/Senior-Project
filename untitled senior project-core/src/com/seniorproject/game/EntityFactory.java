package com.seniorproject.game;

import java.util.Hashtable;

import com.artemis.Archetype;
import com.artemis.ArchetypeBuilder;
import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.seniorproject.components.*;
import com.seniorproject.components.MovementAnimation.Animation;
import com.seniorproject.components.MovementAnimation.AnimationType;
import com.seniorproject.game.AnimationConfig.AnimationData;

/**
 * The EntityFactory class that instantiates and returns the Entity objects
 * specified by the EntityType enum passed into the static factory method
 * getEntity().
 */
public class EntityFactory
{
	private static final String TAG = EntityFactory.class.getSimpleName();
	
	public static World world;

	private static EntityFactory instance = null;
	private Hashtable<String, String> entities;
	
	public static EntityFactory getInstance()
	{
		if (instance == null)
		{
			instance = new EntityFactory();
		}

		return instance;
	}
	
	public Hashtable<String, String> getEntitiesTable()
	{
		return entities;
	}

	public static enum EntityType
	{
		PERFORMER(new ArchetypeBuilder()
				.add(Direction.class)
				.add(MovementAnimation.class)
				.add(MovementState.class)
				.add(Name.class)
				.add(Position.class)
				.add(PerformerSprite.class)
				.add(Velocity.class)
				.add(Actions.class)
				.build(world));

		private Archetype archetype;

		private EntityType(Archetype setArchetype)
		{
			this.archetype = setArchetype;
		}
	}

	public static enum PerformerName
	{
		ANGUS, BANQUO, DONALBAIN, DUNCAN, LADY_MACBETH,
		LENNOX, MACBETH, MACDUFF, MALCOLM, ROSS, SERGEANT;
	}
	
	public static String ANGUS_SPRITESHEET = "characters/sprites/angus.png";
	public static String BANQUO_SPRITESHEET = "characters/sprites/banquo.png";
	public static String DONALBAIN_SPRITESHEET = "characters/sprites/donalbain.png";
	public static String DUNCAN_SPRITESHEET = "characters/sprites/duncan.png";
	public static String LADY_MACBETH_SPRITESHEET = "characters/sprites/lady-macbeth.png";
	public static String LENNOX_SPRITESHEET = "characters/sprites/lennox.png";
	public static String MACBETH_SPRITESHEET = "characters/sprites/macbeth.png";
	public static String MACDUFF_SPRITESHEET = "characters/sprites/macduff.png";
	public static String MALCOLM_SPRITESHEET = "characters/sprites/malcolm.png";
	public static String ROSS_SPRITESHEET = "characters/sprites/ross.png";
	public static String SERGEANT_SPRITESHEET = "characters/sprites/sergeant.png";

	private EntityFactory()
	{
		entities = new Hashtable<String, String>();

		entities.put(PerformerName.ANGUS.name(), ANGUS_SPRITESHEET);
		entities.put(PerformerName.BANQUO.name(), BANQUO_SPRITESHEET);
		entities.put(PerformerName.DONALBAIN.name(), DONALBAIN_SPRITESHEET);
		entities.put(PerformerName.DUNCAN.name(), DUNCAN_SPRITESHEET);
		entities.put(PerformerName.LADY_MACBETH.name(), LADY_MACBETH_SPRITESHEET);
		entities.put(PerformerName.LENNOX.name(), LENNOX_SPRITESHEET);
		entities.put(PerformerName.MACBETH.name(), MACBETH_SPRITESHEET);
		entities.put(PerformerName.MACDUFF.name(), MACDUFF_SPRITESHEET);
		entities.put(PerformerName.MALCOLM.name(), MALCOLM_SPRITESHEET);
		entities.put(PerformerName.ROSS.name(), ROSS_SPRITESHEET);
		entities.put(PerformerName.SERGEANT.name(), SERGEANT_SPRITESHEET);
	}
	
	public static Entity getPerformerEntity(PerformerName name)
	{
		Entity entity = null;
		
		entity = world.createEntity(EntityType.PERFORMER.archetype);
			
		entity.getComponent(Name.class).name = name.toString();
		
		Gdx.app.debug(TAG, "Name of entity being created: " + name.toString());
		
		String spriteSheetPath = EntityFactory.getInstance().getEntitiesTable().get(name.toString());
		AssetLoader.loadTextureAsset(spriteSheetPath);
		Texture spriteSheet = AssetLoader.getTextureAsset(spriteSheetPath);
		
		if(spriteSheet == null)
		{
			Gdx.app.debug(TAG, "spriteSheet for entity " + name.toString() + " is null :(");
		}
		
		entity.getComponent(MovementAnimation.class).animations = loadAnimations(spriteSheet);
		entity.getComponent(PerformerSprite.class).currentFrame = entity.getComponent(MovementAnimation.class).animations.get(AnimationType.WALK_DOWN).getNextStandingFrame();
		entity.getComponent(Velocity.class).velocity = .25f;
		
		return entity;
	}

	public static void setWorld(World setWorld)
	{
		world = setWorld;
	}
	
	private static Hashtable<AnimationType, Animation> loadAnimations(Texture spriteSheet)
	{
		Hashtable<AnimationType, Animation> animations = new Hashtable<AnimationType, Animation>();
		
		Json tempJson = new Json();
		AnimationConfig config = tempJson.fromJson(AnimationConfig.class, Gdx.files.internal("characters/animationconfig.json"));
		Array<AnimationData> animationDataArray = config.getAnimationData();
		
		//for(int i = 0; i < animationDataArray.size; i++)
		//{
		//	Gdx.app.debug(TAG, "animationDataArray at index " + i);
		//	Gdx.app.debug(TAG, "\tAnimationType: " + animationDataArray.get(i).getAnimationType());
		//	for(int j = 0; j < animationDataArray.get(i).getGridPoints().size; j++)
		//	{
		//		Gdx.app.debug(TAG, "\tGridpoints: " + animationDataArray.get(i).getGridPoints().get(j));
		//	}
		//}

		for (AnimationData animationData : animationDataArray)
		{
			Array<GridPoint2> points = animationData.getGridPoints();
			AnimationType animationType = animationData.getAnimationType();

			Animation currentAnim = null;
			
			currentAnim = loadSingleAnimation(spriteSheet, points);
			
			animations.put(animationType, currentAnim);
		}
		
		return animations;
	}

	protected static Animation loadSingleAnimation(Texture spriteSheet, Array<GridPoint2> points)
	{
		TextureRegion[][] textureFrames = TextureRegion.split(spriteSheet, 32, 32);
	
		Array<TextureRegion> animationKeyFrames = new Array<TextureRegion>(points.size);

		for (GridPoint2 point : points)
		{
			animationKeyFrames.add(textureFrames[point.x][point.y]);
		}

		return new MovementAnimation.Animation(animationKeyFrames, .25f);
	}
}
