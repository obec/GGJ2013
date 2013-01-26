package com.teammeatstick.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class Audio implements ApplicationListener {
	
	Sound sound;
	Music heart_beat;

	@Override
	public void create() {
		heart_beat.setVolume(1.0f);
		heart_beat.setLooping(true);
		sound = Gdx.audio.newSound(Gdx.files.internal(Constants.VENOM_BOMB));
		heart_beat = Gdx.audio.newMusic(Gdx.files.internal(Constants.HEART_BEAT));
		heart_beat.play();
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void render() {
		heart_beat.play();
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		heart_beat.dispose();
		sound.dispose();
		
	}

}
