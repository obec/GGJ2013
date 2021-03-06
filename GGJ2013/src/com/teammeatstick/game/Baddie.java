package com.teammeatstick.game;

import com.badlogic.gdx.Gdx;
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

	public Body baddieBody;
	
	public int hitPoints = 5;
	public SpriteAnimator spriteAnimator;
	
	//Baddie constructor
	public Baddie(int id, Vector2 position, Vector2 direction, World world, String spriteSheet) {
		super(id, position, direction, world, spriteSheet);
		
		this.id = id;

		this.position.set(position);
		this.direction.set(direction);
		
		this.velocity.set(1f,1f);
		
		spriteAnimator = new SpriteAnimator(2, 2, spriteSheet, 4);
		
		// Create a box2d body def
				BodyDef bodyDef = new BodyDef();
				// All game objects are dynamic, so they move with the Pulse
				bodyDef.type = BodyType.DynamicBody;
				// Set our body's starting position in the world
				bodyDef.position.set(this.position);

				// Create our body in the world using our body definition
				baddieBody = world.createBody(bodyDef);
				baddieBody.setUserData(this);
				
				//our sprites are squares, no need to get anything else here
				float radius = spriteAnimator.currentFrame.getRegionWidth() * Constants.WORLD_TO_BOX;
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
				Fixture fixture = baddieBody.createFixture(fixtureDef);
			    Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

				circle.dispose();
			}
			
			public void draw() {
				this.position.set(baddieBody.getPosition().x,
						          baddieBody.getPosition().y);
				spriteAnimator.updatePosition(this.position.x, this.position.y);
				spriteAnimator.render();
			}
			
			public void move(){
				
				Vector2 targetVector = new Vector2(Player.playerTarget.x - baddieBody.getPosition().x,
												   Player.playerTarget.y - baddieBody.getPosition().y);
				
				this.baddieBody.applyForce(targetVector.nor().mul(2.0f), this.baddieBody.getPosition());

				//float arcx = Player.playerTarget.x;
				//float arcy = Player.playerTarget.y;
			    //float desiredAngle = MathUtils.atan2(arcx,arcy);
				//this.baddieBody.setTransform(Player.playerTarget, desiredAngle);
				//this.baddieBody.applyForce(velocity, direction);

				// THIS ONE WORKS this.baddieBody.applyForce(velocity, direction);

				//this.baddieBody.applyLinearImpulse(velocity, direction);
			}

}
