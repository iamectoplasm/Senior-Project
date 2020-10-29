package com.seniorproject.game;

import java.util.Hashtable;

import com.artemis.Archetype;
import com.artemis.ArchetypeBuilder;
import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
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
import com.seniorproject.configs.AnimationConfig;
import com.seniorproject.configs.AnimationConfig.AnimationData;
import com.seniorproject.enums.CharacterName;

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
	
	//private Hashtable<String, TextureRegion> entityTextures;
	
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
				.add(BoundingBox.class)
				.add(PerformerEmote.class)
				.add(MovementDirection.class)
				.add(MovementAnimation.class)
				.add(MovementState.class)
				.add(Name.class)
				.add(Position.class)
				.add(PerformerSprite.class)
				.add(Velocity.class)
				.add(StageDirections.class)
				.build(world));

		private Archetype archetype;

		private EntityType(Archetype setArchetype)
		{
			this.archetype = setArchetype;
		}
	}

	private EntityFactory()
	{ }
	
	public static Entity getPerformerEntity(CharacterName character)
	{
		Entity entity = null;
		
		entity = world.createEntity(EntityType.PERFORMER.archetype);
			
		entity.getComponent(Name.class).name = character.toString();
		
		Gdx.app.debug(TAG, "Name of entity being created: " + character.toString());
		
		TextureRegion spriteSheet = PerformerAtlas.getInstance().getPerformerTexture(character);
		
		if(spriteSheet == null)
		{
			Gdx.app.debug(TAG, "spriteSheet for entity " + character.toString() + " is null :(");
		}
		
		entity.getComponent(MovementAnimation.class).animations = loadAnimations(spriteSheet);
		entity.getComponent(PerformerSprite.class).currentFrame = entity.getComponent(MovementAnimation.class).animations.get(AnimationType.WALK_DOWN).getNextStandingFrame();
		entity.getComponent(Velocity.class).velocity = .25f;
		
		//Gdx.app.debug(TAG, "Now calling initBoundingBox on entity " + character.toString());
		initBoundingBox(entity.getId());
		
		return entity;
	}

	public static void setWorld(World setWorld)
	{
		world = setWorld;
	}
	
	private static Hashtable<AnimationType, Animation> loadAnimations(TextureRegion spriteSheet)
	{
		Hashtable<AnimationType, Animation> animations = new Hashtable<AnimationType, Animation>();
		
		Json tempJson = new Json();
		AnimationConfig config = tempJson.fromJson(AnimationConfig.class, Gdx.files.internal("characters/animationconfig.json"));
		Array<AnimationData> animationDataArray = config.getAnimationData();

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
	
	protected static Animation loadSingleAnimation(TextureRegion spriteSheet, Array<GridPoint2> points)
	{
		TextureRegion[][] textureFrames = spriteSheet.split(32, 32);
	
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
