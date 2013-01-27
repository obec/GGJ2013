package com.teammeatstick.game;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class CollisionHandler implements ContactListener{

	@Override
	public void beginContact(Contact contact) {

		System.out.println("We have contact");
		
		GameObject objectA = (GameObject) contact.getFixtureA().getBody().getUserData();
		GameObject objectB = (GameObject) contact.getFixtureB().getBody().getUserData();
		
		if(objectA != null)
		{
			System.out.println(objectA.getClass().getName());
		}
		
		if(objectA instanceof Player)
		{
			if(objectB instanceof Baddie)
			{
				System.out.println("Player hit baddie");
			}
		}
		//if(Player.class == contact.getFixtureA().getClass())
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}
