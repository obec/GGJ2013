package com.teammeatstick.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class Audio {
	
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
	
}
