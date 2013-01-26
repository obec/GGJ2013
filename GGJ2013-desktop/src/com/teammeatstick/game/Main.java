package com.teammeatstick.game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "GGJ2013";
		cfg.useGL20 = false;
		cfg.width = 1000;
		cfg.height = 600;
		
		new LwjglApplication(new MainGame(), cfg);
	}
}
