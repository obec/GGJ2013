package com.teammeatstick.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Input.Keys;
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
		_background = new Texture(Gdx.files.internal("textures/backgrounds/TestBackground6.png"));
		_backgroundRegion = new TextureRegion(_background, 0, _background.getHeight() - Gdx.graphics.getHeight() , Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		_backgroundSpeed = 0.5f;
	}
	
	public void UpdateBackground()
	{
		ScrollBackground();
	}
	
	public void ScrollBackground()
	{
		//_backgroundRegion.scroll(_backgroundSpeed * Gdx.graphics.getDeltaTime(), 0.0f);
		
		if(Gdx.input.isKeyPressed(Keys.RIGHT) &&
		   _backgroundRegion.getRegionX() < (_background.getWidth() - _backgroundRegion.getRegionWidth()))
		{
			_backgroundRegion.scroll(1 * Gdx.graphics.getDeltaTime(), 0);
		}
		if(Gdx.input.isKeyPressed(Keys.LEFT) &&
		   _backgroundRegion.getRegionX() > 0)
		{
			_backgroundRegion.scroll(-1 * Gdx.graphics.getDeltaTime(), 0);
		}
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
