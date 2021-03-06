package com.teammeatstick.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class GameObject {
	
	//position and direction vectors
	public Vector2 position = new Vector2();
	public Vector2 direction = new Vector2();
	public Vector2 velocity = new Vector2();
	public Audio audio = new Audio();
	
	//velocity and delta floats for calculating pos 

	public float delta = 1.0f;	
	
	//object ID
	public int id = 0;
	
	//need to add sprite stuff
	
	//constructor for vanilla object
	public GameObject(int id, Vector2 position, Vector2 direction, World world, String spriteSheet) {
		super();
		
		this.id = id;
		this.position.set(position);
		this.direction.set(direction);
		this.audio.create();
		
	}

	
	public void draw() {
		//draw the sprite at position()
	}
	
	public void move(){

	}
}
