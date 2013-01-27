package com.teammeatstick.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Baddie extends GameObject {

	public Vector2 position = new Vector2();
	public Vector2 velocity = new Vector2();
	public Vector2 direction = new Vector2();
	
	public Body baddieBody;
	
	public int hitPoints = 5;
	public SpriteAnimator spriteAnimator;
	
	//Baddie constructor
	public Baddie(int id, Vector2 position, Vector2 direction, World world, String spriteSheet) {
		super(id, position, direction, world, spriteSheet);
		
		this.id = id;

		this.position.set(position);
		this.direction.set(direction);
		
		spriteAnimator = new SpriteAnimator(2, 2, spriteSheet, 4);
		
		// Create a box2d body def
				BodyDef bodyDef = new BodyDef();
				// All game objects are dynamic, so they move with the Pulse
				bodyDef.type = BodyType.DynamicBody;
				// Set our body's starting position in the world
				bodyDef.position.set(this.position);

				// Create our body in the world using our body definition
				baddieBody = world.createBody(bodyDef);
				
				//our sprites are squares, no need to get anything else here
				float radius = spriteAnimator.currentFrame.getRegionWidth();
				//convert from pixels to box2d shiz
				radius *= 0.01;
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
				Fixture fixture = baddieBody.createFixture(fixtureDef);
			    Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

				circle.dispose();
			}
			
			public void draw() {
				this.position.set(baddieBody.getPosition());
				spriteAnimator.updatePosition((int)this.position.x, (int)this.position.y);
				spriteAnimator.render();
			}
			
			public void move(){
				System.out.println("Attempting to move baddies");
				System.out.println(Player.playerTarget);
				
				float arcx = Player.playerTarget.x;
				float arcy = Player.playerTarget.y;
			    float desiredAngle = MathUtils.atan2(arcx,arcy);
			    System.out.println(desiredAngle);
			    System.out.println("before fucking with Player.position");
			    System.out.println(Player.playerTarget);
			    Vector2 target = new Vector2();
			    target.set(Player.playerTarget.cpy());	
			    System.out.println("after fucking with Player.position");
			    System.out.println(Player.playerTarget);		    
				baddieBody.setTransform(target, desiredAngle);
				
				baddieBody.applyLinearImpulse(velocity, direction);
			}

}
