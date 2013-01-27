package com.teammeatstick.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class Audio implements ContactListener {
	
	Sound sound;
	Music heart_beat;
	
	public void create() {
		sound = Gdx.audio.newSound(Gdx.files.internal(Constants.COLLIDE_NANO));
		
		heart_beat = Gdx.audio.newMusic(Gdx.files.internal(Constants.HEART_BEAT_NORMAL));
		heart_beat.setVolume(1.0f);
		heart_beat.setLooping(true);
		heart_beat.play();
	}

	public void pause() {
		heart_beat.pause();
	}

	
	public void resume() {
		heart_beat.play();
	}

	public void dispose() {
		heart_beat.dispose();
		sound.dispose();
	}

	@Override
	public void beginContact(Contact contact) {
		sound.play();
		System.out.println("BEGIN CONTACT!");
		
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
