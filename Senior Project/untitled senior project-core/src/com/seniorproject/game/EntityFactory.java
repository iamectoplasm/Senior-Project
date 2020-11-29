package com.seniorproject.game;

import java.util.Hashtable;

import com.artemis.Archetype;
import com.artemis.ArchetypeBuilder;
import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.seniorproject.assetmanagement.PerformerAtlas;
import com.seniorproject.assetmanagement.PropAtlas;
import com.seniorproject.components.*;
import com.seniorproject.components.MovementAnimation.WalkAnimation;
import com.seniorproject.configs.AnimationConfig;
import com.seniorproject.configs.AnimationConfig.AnimationData;
import com.seniorproject.enums.CharacterName;
import com.seniorproject.enums.Prop;
import com.seniorproject.enums.AnimationType;

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
	
	public static EntityFactory getInstance()
	{
		if (instance == null)
		{
			instance = new EntityFactory();
		}

		return instance;
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
				.add(DrawableSprite.class)
				.add(Velocity.class)
				.add(ActionsQueue.class)
				.build(world)),
		
		PROP(new ArchetypeBuilder()
				.add(PropComponent.class)
				.add(Position.class)
				.add(DrawableSprite.class)
				.build(world)),
		
		EMOTE(new ArchetypeBuilder()
				.add(PerformerEmote.class)
				.add(Position.class)
				.add(DrawableSprite.class)
				.build(world));

		private Archetype archetype;

		private EntityType(Archetype setArchetype)
		{
			this.archetype = setArchetype;
		}
	}

	private EntityFactory()
	{ }
	
	public static void setWorld(World setWorld)
	{
		world = setWorld;
	}
	
	public static Entity getPerformerEntity(CharacterName character)
	{
		Entity entity = null;
		
		// Create the entity
		entity = world.createEntity(EntityType.PERFORMER.archetype);
		
		// Set the entity's name, to make debugging who is who easier
		entity.getComponent(Name.class).name = character.toString();
		Gdx.app.debug(TAG, "Name of performer entity being created: " + character.toString() + " with ID " + entity.getId());
		
		// Set up the textures
		//TextureRegion spriteSheet = PerformerAtlas.getInstance().getPerformerTexture(character);
		Array<TextureRegion> spriteSheets = PerformerAtlas.getInstance().getPerformerTexture(character);
		TextureRegion spriteSheet = spriteSheets.get(0);
		if(spriteSheets.size > 1)
		{
			TextureRegion specialSpriteSheet = spriteSheets.get(1);
			entity.getComponent(MovementAnimation.class).otherAnimations = loadSpecialAnimations(specialSpriteSheet);
		}
		
		if(spriteSheet == null)
		{
			Gdx.app.debug(TAG, "spriteSheet for entity " + character.toString() + " is null :(");
		}
		
		// Now set all the basic info that a performer entity will need
		// Load walk animations
		entity.getComponent(MovementAnimation.class).animations = loadWalkAnimations(spriteSheet);
		
		// Set current frame, otherwise everything dies in a null pointer exception
		entity.getComponent(DrawableSprite.class).currentFrame = entity.getComponent(MovementAnimation.class).animations.get(AnimationType.WALK_DOWN).getNextStandingFrame();
		
		// Sprites are 32 x 32, so to render them properly on the maps we set them to be 2 units high and 2 units wide
		entity.getComponent(DrawableSprite.class).drawWidth = 2;
		entity.getComponent(DrawableSprite.class).drawHeight = 2;
		
		// We want the sprite to render in the center of each 16 x 16 tile
		entity.getComponent(DrawableSprite.class).xOffset = -(1/2f);
		entity.getComponent(DrawableSprite.class).yOffset = (1/4f);
		entity.getComponent(Velocity.class).speed = Velocity.Speed.MED;
		
		//Gdx.app.debug(TAG, "Now calling initBoundingBox on entity " + character.toString());
		initBoundingBox(entity.getId());
		
		return entity;
	}
	
	public static Entity createPropEntity(Prop prop)
	{
		Entity entity = null;
		
		entity = world.createEntity(EntityType.PROP.archetype);
		Gdx.app.debug(TAG, "Name of prop entity being created: " + prop.toString() + " with ID " + entity.getId());
			
		entity.getComponent(PropComponent.class).prop = prop;
		
		TextureRegion propTexture = PropAtlas.getInstance().getPropTexture(prop);
		if(propTexture == null)
		{
			Gdx.app.debug(TAG, "spriteSheet for entity " + prop.toString() + " is null :(");
		}
		entity.getComponent(DrawableSprite.class).currentFrame = propTexture;
		entity.getComponent(DrawableSprite.class).drawWidth = propTexture.getRegionWidth() / 16;
		entity.getComponent(DrawableSprite.class).drawHeight = propTexture.getRegionHeight() / 16;
		
		//entity.getComponent(StagePosition.class).currentPosition = new Vector2(5, 5);
		//entity.getComponent(StagePosition.class).mapZIndex = 2;
		
		switch(Prop.getPropType(prop))
		{
		case OBJECT:
			
			break;
		case FURNITURE:
			break;
		case LIGHTING:
			break;
		}
		
		return entity;
	}
	
	private static Hashtable<AnimationType, WalkAnimation> loadWalkAnimations(TextureRegion spriteSheet)
	{
		Hashtable<AnimationType, WalkAnimation> animations = new Hashtable<AnimationType, WalkAnimation>();
		
		Json tempJson = new Json();
		AnimationConfig config = tempJson.fromJson(AnimationConfig.class, Gdx.files.internal("characters/animationconfig.json"));
		Array<AnimationData> animationDataArray = config.getWalkAnimationData();

		for (AnimationData animationData : animationDataArray)
		{
			Array<GridPoint2> points = animationData.getGridPoints();
			AnimationType animationType = animationData.getAnimationType();

			WalkAnimation currentAnim = null;
			
			currentAnim = loadSingleWalkAnimation(spriteSheet, points);
			
			animations.put(animationType, currentAnim);
		}
		
		return animations;
	}
	
	protected static WalkAnimation loadSingleWalkAnimation(TextureRegion spriteSheet, Array<GridPoint2> points)
	{
		TextureRegion[][] textureFrames = spriteSheet.split(32, 32);
	
		Array<TextureRegion> animationKeyFrames = new Array<TextureRegion>(points.size);

		for (GridPoint2 point : points)
		{
			animationKeyFrames.add(textureFrames[point.x][point.y]);
		}

		return new MovementAnimation.WalkAnimation(animationKeyFrames, .25f);
	}
	
	protected static Hashtable<AnimationType, Animation<TextureRegion>> loadSpecialAnimations(TextureRegion spriteSheet)
	{
		Hashtable<AnimationType, Animation<TextureRegion>> animations = new Hashtable<AnimationType, Animation<TextureRegion>>();
		
		Json tempJson = new Json();
		AnimationConfig config = tempJson.fromJson(AnimationConfig.class, Gdx.files.internal("characters/animationconfig.json"));
		Array<AnimationData> animationDataArray = config.getSpecialAnimationData();
		
		for (AnimationData animationData : animationDataArray)
		{
			Array<GridPoint2> points = animationData.getGridPoints();
			AnimationType animationType = animationData.getAnimationType();

			Animation<TextureRegion> newAnim = null;
			
			TextureRegion[][] textureFrames = spriteSheet.split(32, 32);
			
			Array<TextureRegion> animationKeyFrames = new Array<TextureRegion>(points.size);
			
			for(GridPoint2 point: points)
			{
				animationKeyFrames.add(textureFrames[point.x][point.y]);
			}
			
			newAnim = new Animation<TextureRegion>(.2f, animationKeyFrames, PlayMode.NORMAL);
			
			animations.put(animationType, newAnim);
		}
		
		return animations;
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
