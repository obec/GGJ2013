package com.teammeatstick.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;


public class Constants {
	//Audio resources
	public static final FileHandle HEART_BEAT_NORMAL = Gdx.files.internal("sounds/effects/heart_beat_normal.mp3");
	public static final FileHandle HEART_BEAT_SLOW = Gdx.files.internal("sounds/effects/heart_beat_slow.mp3");
	public static final FileHandle HEART_BEAT_FAST = Gdx.files.internal("sounds/effects/heart_beat_fast.mp3");
	public static final FileHandle POWER_UP = Gdx.files.internal("");
	public static final FileHandle COLLIDE_VIRUS = Gdx.files.internal("sounds/effects/squish_virus_collide.mp3");
	public static final FileHandle COLLIDE_NANO = Gdx.files.internal("sounds/effects/Nanobot_crash.mp3");
	public static final FileHandle BZZSHHHHT = Gdx.files.internal("sounds/effects/bzsshhh.mp3");
	public static final FileHandle CREDIT_MUSIC = Gdx.files.internal("sounds/music/credits_music.mp3");
	public static final FileHandle INGAME_MUSIC = Gdx.files.internal("sounds/music/ingame_music.mp3");
	public static final FileHandle MENU_MUSIC = Gdx.files.internal("");
	public static final FileHandle NANO_VICTORY = Gdx.files.internal("sounds/effects/nanobot_win.mp3");
	public static final FileHandle VIRUS_VICTORY = Gdx.files.internal("sounds/effects/Virus_win.mp3");
	public static final FileHandle MENU_SELECTION = Gdx.files.internal("sounds/effects/menu_select.mp3");
	public static final FileHandle MENU_MOVE = Gdx.files.internal("sounds/effects/menu_move.mp3");
	
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
