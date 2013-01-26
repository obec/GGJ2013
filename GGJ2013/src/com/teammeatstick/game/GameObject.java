package com.teammeatstick.game;

import com.badlogic.gdx.math.Vector2;

public class GameObject {
	
	private static final float MAX_VELOCITY = 8;
	
	//position and direction vectors
	public Vector2 position = new Vector2();
	public Vector2 direction = new Vector2();
	//velocty and delta floats for calculating pos 
	private float velocity = 0.0f;
	private float delta = 0.0f;
	
	//object ID
	public int id = 0;
	
	//need to add sprite stuff
	
	//constructor for vanilla object
	public GameObject(int id, Vector2 position, Vector2 direction) {
		super();
		
		this.id = id;

		this.position.set(position);
		this.direction.set(direction);
	}
	
	public void draw() {
		//draw the sprite at position()
	}
	
	public void move(){
		position.add(direction.x * velocity * delta, direction.y * velocity * delta);
	}
}
