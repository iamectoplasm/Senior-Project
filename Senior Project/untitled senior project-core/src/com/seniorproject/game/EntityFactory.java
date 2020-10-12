package com.seniorproject.game;

import java.util.Hashtable;

import com.artemis.Archetype;
import com.artemis.ArchetypeBuilder;
import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.seniorproject.components.*;
import com.seniorproject.components.MovementAnimation.Animation;
import com.seniorproject.components.MovementAnimation.AnimationType;
import com.seniorproject.enums.CharacterName;
import com.seniorproject.scripting.AnimationConfig;
import com.seniorproject.scripting.AnimationConfig.AnimationData;

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
				.add(MovementDirection.class)
				.add(MovementAnimation.class)
				.add(MovementState.class)
				.add(Name.class)
				.add(Position.class)
				.add(PerformerSprite.class)
				.add(Velocity.class)
				.add(Actions.class)
				.add(BoundingBox.class)
				.build(world));

		private Archetype archetype;

		private EntityType(Archetype setArchetype)
		{
			this.archetype = setArchetype;
		}
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
	
	public static String FIRST_WITCH_SPRITESHEET = "characters/sprites/first-witch.png";
	public static String SECOND_WITCH_SPRITESHEET = "characters/sprites/second-witch.png";
	public static String THIRD_WITCH_SPRITESHEET = "characters/sprites/third-witch.png";

	private EntityFactory()
	{
		entities = new Hashtable<String, String>();

		entities.put(CharacterName.ANGUS.name(), ANGUS_SPRITESHEET);
		entities.put(CharacterName.BANQUO.name(), BANQUO_SPRITESHEET);
		entities.put(CharacterName.DONALBAIN.name(), DONALBAIN_SPRITESHEET);
		entities.put(CharacterName.DUNCAN.name(), DUNCAN_SPRITESHEET);
		entities.put(CharacterName.LADY_MACBETH.name(), LADY_MACBETH_SPRITESHEET);
		entities.put(CharacterName.LENNOX.name(), LENNOX_SPRITESHEET);
		entities.put(CharacterName.MACBETH.name(), MACBETH_SPRITESHEET);
		entities.put(CharacterName.MACDUFF.name(), MACDUFF_SPRITESHEET);
		entities.put(CharacterName.MALCOLM.name(), MALCOLM_SPRITESHEET);
		entities.put(CharacterName.ROSS.name(), ROSS_SPRITESHEET);
		entities.put(CharacterName.SERGEANT.name(), SERGEANT_SPRITESHEET);
		entities.put(CharacterName.FIRST_WITCH.name(), FIRST_WITCH_SPRITESHEET);
		entities.put(CharacterName.SECOND_WITCH.name(), SECOND_WITCH_SPRITESHEET);
		entities.put(CharacterName.THIRD_WITCH.name(), THIRD_WITCH_SPRITESHEET);
	}
	
	public static Entity getPerformerEntity(CharacterName character)
	{
		Entity entity = null;
		
		entity = world.createEntity(EntityType.PERFORMER.archetype);
			
		entity.getComponent(Name.class).name = character.toString();
		
		Gdx.app.debug(TAG, "Name of entity being created: " + character.toString());
		
		String spriteSheetPath = EntityFactory.getInstance().getEntitiesTable().get(character.toString());
		AssetLoader.loadTextureAsset(spriteSheetPath);
		Texture spriteSheet = AssetLoader.getTextureAsset(spriteSheetPath);
		
		if(spriteSheet == null)
		{
			Gdx.app.debug(TAG, "spriteSheet for entity " + character.toString() + " is null :(");
		}
		
		entity.getComponent(MovementAnimation.class).animations = loadAnimations(spriteSheet);
		entity.getComponent(PerformerSprite.class).currentFrame = entity.getComponent(MovementAnimation.class).animations.get(AnimationType.WALK_DOWN).getNextStandingFrame();
		entity.getComponent(Velocity.class).velocity = .25f;
		
		Gdx.app.debug(TAG, "Now calling initBoundingBox on entity " + character.toString());
		initBoundingBox(entity.getId());
		
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
	
	public static void initBoundingBox(int entityId)
	{
		BoundingBox box = world.getEntity(entityId).getComponent(BoundingBox.class);
		Position position = world.getEntity(entityId).getComponent(Position.class);
		
		box.shapeRenderer = new ShapeRenderer();

		box.xPos = position.currentPosition.x;
		box.yPos = position.currentPosition.y;

		box.boundingBox = new Rectangle(box.xPos, box.yPos, 1, 1);
		
		box.boundingBoxCenter = box.boundingBox.getCenter(new Vector2());
	}
}
