package com.teammeatstick.game;

import com.badlogic.gdx.math.Vector2;

public class GameObject {
	
	//position and direction vectors
	public Vector2 position = new Vector2();
	public Vector2 velocity = new Vector2();
	public Vector2 direction = new Vector2();
	
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
		
	}
	
	public void move(){
		
	}
}
