package com.teammeatstick.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class Audio implements ContactListener {
	
	public Sound sound;
	public Music music;
	
	public void create() {
		//sound = Gdx.audio.newSound(Constants.COLLIDE_NANO);
		
		//music = Gdx.audio.newMusic(Constants.HEART_BEAT_NORMAL);
		//music.play();
	}
	public void setMusic(FileHandle musicFile, float musicVolume, boolean shouldLoop) {
		music = Gdx.audio.newMusic(musicFile);
		music.setVolume(musicVolume);
		music.setLooping(shouldLoop);
	}
	public void triggerSound(FileHandle soundFile, float soundVolume) {
		sound = Gdx.audio.newSound(soundFile);
		//sound.setVolume(0,soundVolume);
		sound.play(soundVolume);
	};

	public void pause() {
		music.pause();
	}

	
	public void resume() {
		music.play();
	}

	public void dispose() {
		music.dispose();
		sound.dispose();
	}

	@Override
	public void beginContact(Contact contact) {
		sound = Gdx.audio.newSound(Constants.COLLIDE_NANO);
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
