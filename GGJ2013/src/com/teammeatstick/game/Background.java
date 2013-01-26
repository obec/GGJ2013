package com.teammeatstick.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.tablelayout.BaseTableLayout.Debug;

public class Background {
	private Texture _background;
	private TextureRegion _backgroundRegion;
	private Texture[] _backgroundTextures;
	private TextureAtlas _levelAtlas;
	private AtlasRegion _levelRegion;
	private float _backgroundSpeed;
	private float _lastX;
	
	public void LoadBackground()
	{
		_levelAtlas = new TextureAtlas(Gdx.files.internal("textures/backgrounds/testLevel/testLevelPack.atlas"));
		_levelRegion = _levelAtlas.findRegion("TestLevelPart1");
		_background = new Texture(Gdx.files.internal("textures/backgrounds/TestBackground6.png"));
	
		Array<AtlasRegion> regions = _levelAtlas.getRegions();
		_backgroundTextures = new Texture[regions.size];
		
		for(int atlas = 0; atlas < regions.size; atlas++)
		{
			_backgroundTextures[atlas] = regions.get(atlas).getTexture();
		}
		
		
		_backgroundRegion = new TextureRegion(_background, 0, _background.getHeight() - Gdx.graphics.getHeight() , /*Gdx.graphics.getWidth()*/4096, Gdx.graphics.getHeight());
		_backgroundSpeed = 0.5f;
	}
	
	public void UpdateBackground(Vector2 playerPos)
	{
		ScrollBackground(playerPos);
	}
	
	public void ScrollBackground(Vector2 playerPos)
	{		
		//_backgroundRegion.scroll(_backgroundSpeed * 0.1f * Gdx.graphics.getDeltaTime(), 0.0f);
		
		//_backgroundRegion.scroll((playerPos.x - _lastX) * 0.05f * Gdx.graphics.getDeltaTime(), 0);
		//_lastX = playerPos.x;
		//_backgroundRegion(playerPos.x);
		
		if(Gdx.input.isKeyPressed(Keys.RIGHT) &&
		   _backgroundRegion.getRegionX() < (_background.getWidth() - _backgroundRegion.getRegionWidth()))
		{
			_backgroundRegion.scroll(0.5f * Gdx.graphics.getDeltaTime(), 0);
		}
		if(Gdx.input.isKeyPressed(Keys.LEFT) &&
		   _backgroundRegion.getRegionX() > 0)
		{
			_backgroundRegion.scroll(-0.5f * Gdx.graphics.getDeltaTime(), 0);
		}
	}
	
	public Texture[] GetBackground()
	{
		return _backgroundTextures;
	}
	
	public void Draw(SpriteBatch batch)
	{
		batch.begin();
	
		for(int texture=0; texture<_backgroundTextures.length; texture++)
		{
			batch.draw(_backgroundTextures[texture], texture * _backgroundTextures[texture].getWidth(), 0f);
		}
		
		batch.end();
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
