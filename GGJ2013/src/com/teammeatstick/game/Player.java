package com.teammeatstick.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Player extends GameObject {
	
	public static Vector2 playerTarget = new Vector2();

	public Body playerBody;
	
	public int hitPoints = 100;
	public SpriteAnimator spriteAnimator;
		
	//constructor for player object
	public Player(int id, float delta, Vector2 position, Vector2 direction, World world, String spriteSheet) {
		
		super(id, position, direction, world, spriteSheet);
		
		this.id = id;

		this.position.set(position);
		this.direction.set(direction);
		
		spriteAnimator = new SpriteAnimator(3, 1, spriteSheet, 4);
		
		
		// Create a box2d body def
		BodyDef bodyDef = new BodyDef();
		// All game objects are dynamic, so they move with the Pulse
		bodyDef.type = BodyType.DynamicBody;
		// Set our body's starting position in the world
		bodyDef.position.set(this.position);

		// Create our body in the world using our body definition
		playerBody = world.createBody(bodyDef);
		playerBody.setUserData(this);
		
		//our sprites are squares, no need to get anything else here
		float radius = spriteAnimator.currentFrame.getRegionWidth() * Constants.WORLD_TO_BOX ;
		//convert from pixels to box2d shiz
		//radius *= 0.01;
		// Create a circle shape and set its radius to the equivalent sprite width
	    CircleShape circle = new CircleShape();
		circle.setRadius(radius);

		// Create a fixture definition to apply our shape to
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 0.5f; 
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.6f; // Make it bounce a little bit

		// Create our fixture and attach it to the body
		Fixture fixture = playerBody.createFixture(fixtureDef);
	    Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

		circle.dispose();
	}
	
	public void draw() {
		playerTarget.set(this.position.cpy());
		this.position.set(playerBody.getPosition().x, // * Constants.BOX_TO_WORLD,
						  playerBody.getPosition().y);// * Constants.BOX_TO_WORLD);
		spriteAnimator.updatePosition(this.position.x, this.position.y);
		spriteAnimator.render();
	}
	
	public void moveUp() {
		playerBody.applyLinearImpulse(new Vector2(0, 0.05f), playerBody.getPosition());
	}
	
	public void moveLeft() {
		playerBody.applyLinearImpulse(new Vector2(-0.2f, 0), playerBody.getPosition());
	}
	
	public void moveDown() {
		playerBody.applyLinearImpulse(new Vector2(0, -0.05f), playerBody.getPosition());
	}
	
	public void moveRight() {
		playerBody.applyLinearImpulse(new Vector2(0.2f, 0), playerBody.getPosition());
	}
}
