package com.teammeatstick.game;

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
	
	public Vector2 position = new Vector2();
	public Vector2 velocity = new Vector2();
	public Vector2 direction = new Vector2();
	
	public int hitPoints = 0;
	
	//need to add sprite stuff
	
	//constructor for player object
	public Player(int id, float delta, Vector2 position, Vector2 direction, World world) {
		
		super(id, position, direction, world);
		
		this.id = id;

		this.position.set(position);
		this.direction.set(direction);
		
		// Create a box2d body def
		BodyDef bodyDef = new BodyDef();
		// All game objects are dynamic, so they move with the Pulse
		bodyDef.type = BodyType.DynamicBody;
		// Set our body's starting position in the world
		bodyDef.position.set(this.position);

		// Create our body in the world using our body definition
		Body body = world.createBody(bodyDef);

		// Create a circle shape and set its radius to 6
	    CircleShape circle = new CircleShape();
		circle.setRadius(60f);

		// Create a fixture definition to apply our shape to
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 0.5f; 
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.6f; // Make it bounce a little bit

		// Create our fixture and attach it to the body
		Fixture fixture = body.createFixture(fixtureDef);
	    Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();


		// Remember to dispose of any shapes after you're done with them!
		// BodyDef and FixtureDef don't need disposing, but shapes do.
		circle.dispose();
	}
	
	public void draw() {
		
	}
	
	public void move(){
		
	}
}
