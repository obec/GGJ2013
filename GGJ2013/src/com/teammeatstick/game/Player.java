package com.teammeatstick.game;

import com.badlogic.gdx.math.Vector2;

public class Player extends GameObject {
	
	public Vector2 position = new Vector2();
	public Vector2 velocity = new Vector2();
	public Vector2 direction = new Vector2();
	
	public int hitPoints = 0;
	
	//need to add sprite stuff
	
	//constructor for player object
	public Player(int id, Vector2 position, Vector2 direction) {
		
		super(id, position, direction);
		
		this.id = id;

		this.position.set(position);
		this.direction.set(direction);
	}
	
}
