package com.teammeatstick.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;


public class Constants {
	//Audio resources
	public static final String HEART_BEAT_NORMAL = "sounds/effects/heart_beat_normal.mp3";
	public static final String HEART_BEAT_SLOW = "sounds/effects/heart_beat_slow.mp3";
	public static final String HEART_BEAT_FAST = "sounds/effects/heart_beat_fast.mp3";
	public static final String POWER_UP = "";
	public static final String COLLIDE_VIRUS = "sounds/effects/squish_virus_collide.mp3";
	public static final String COLLIDE_NANO = "sounds/effects/Nanobot_crash.mp3";
	public static final String BZZSHHHHT = "sounds/effects/bzsshhh.mp3";
	public static final String CREDIT_MUSIC = "sounds/music/credits_music.mp3";
	public static final String INGAME_MUSIC = "sounds/music/ingame_music.mp3";
	public static final String MENU_MUSIC = "";
	public static final String NANO_VICTORY = "sounds/effects/nanobot_win.mp3";
	public static final String VIRUS_VICTORY = "sounds/effects/Virus_win.mp3";
	public static final String MENU_SELECTION = "sounds/effects/menu_select.mp3";
	public static final String MENU_MOVE = "sounds/effects/menu_move.mp3";
	
	public static final float SCREEN_WIDTH = Gdx.graphics.getWidth();
	public static final float SCREEN_HEIGHT = Gdx.graphics.getHeight();
	public static final float PIXELS_PER_METER = 16.0f;
	public static final float WORLD_WIDTH_METERS = SCREEN_WIDTH / PIXELS_PER_METER;
	public static final float WORLD_HEIGHT_METERS = SCREEN_HEIGHT / PIXELS_PER_METER;
    public static final float WORLD_TO_BOX = 0.01f;  
    public static final float BOX_TO_WORLD = 100.0f;
	public static final int SCALE_FACTOR = 10;
	
	//what else? die? victory? menu selections?

	//Camera
	public static final OrthographicCamera CAMERA = new OrthographicCamera();  //(w, h);//1, h/w);
	
	//Sprites
	public static final String VIRUS_SPRITE = "textures/sprites/VirusSprite.png";
}
