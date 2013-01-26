package com.teammeatstick.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.esotericsoftware.tablelayout.BaseTableLayout.Debug;

public class Background {
	private Texture _background;
	private TextureRegion _backgroundRegion;
	private float _backgroundSpeed;
	
	public void LoadBackground()
	{
		_background = new Texture(Gdx.files.internal("textures/backgrounds/TestBackground.png"));
		Gdx.app.log("Load", "W: " + Gdx.graphics.getWidth() + " H: " + Gdx.graphics.getHeight());
		_backgroundRegion = new TextureRegion(_background, 0, 0, 10, 10); //Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		_backgroundSpeed = 0.5f;
	}
	
	public void ScrollBackground()
	{
		_backgroundRegion.scroll(_backgroundSpeed * Gdx.graphics.getDeltaTime(), 0.0f);
	}
	
	public TextureRegion GetBackground()
	{
		return _backgroundRegion;
	}
	
	public Texture GetBackgroundTxt()
	{
		return _background;
	}
	
	public void dispose()
	{
		//_background.dispose();
	}
	
}
