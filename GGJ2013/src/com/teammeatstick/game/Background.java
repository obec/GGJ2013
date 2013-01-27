package com.teammeatstick.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
	private Texture[] _backgroundTextures;
	private TextureAtlas _levelAtlas;
	float scrollTimer = 0.0f;
	SpriteBatch spriteBatch;
	Texture spriteTexture;
	Sprite sprite;
	
	Background(){
		LoadBackground();
		spriteBatch = new SpriteBatch();
	    spriteTexture = new Texture(Gdx.files.internal("textures/backgrounds/looplevel/fuzzyblood.png"));
	                 
	    spriteTexture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
	    sprite = new Sprite(spriteTexture, 0, 0, 800, 600);
	    sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	
	public void LoadBackground()
	{
		_levelAtlas = new TextureAtlas(Gdx.files.internal("textures/backgrounds/testLevel/testLevelPack.atlas"));
	
		Array<AtlasRegion> regions = _levelAtlas.getRegions();
		_backgroundTextures = new Texture[regions.size];
		
		for(int atlas = 0; atlas < regions.size; atlas++)
		{
			_backgroundTextures[atlas] = regions.get(atlas).getTexture();
		}
	}
	
	public Texture[] GetBackground()
	{
		return _backgroundTextures;
	}
	
	public void Draw(SpriteBatch batch)
	{
		/*batch.begin();
	
		for(int texture=0; texture<_backgroundTextures.length; texture++)
		{
			batch.draw(_backgroundTextures[texture],
					   texture * (_backgroundTextures[texture].getWidth() / Constants.PIXELS_PER_METER),
					   0f,
					   _backgroundTextures[texture].getWidth() / Constants.PIXELS_PER_METER,
					   _backgroundTextures[texture].getHeight() / Constants.PIXELS_PER_METER);
		}
		
		batch.end();*/
		
		scrollTimer+=Gdx.graphics.getDeltaTime() / 4;
	     if(scrollTimer>1.0f)
	         scrollTimer = 0.0f;
	                
	     sprite.setU(scrollTimer);
	     sprite.setU2(scrollTimer+1);
	                
	     spriteBatch.begin();
	     sprite.draw(spriteBatch);
	     spriteBatch.end();
	}
	
	public Texture GetBackgroundTxt()
	{
		return _background;
	}
	
	public void dispose()
	{
		for(int texture=0; texture<_backgroundTextures.length; texture++)
		{
			_backgroundTextures[texture].dispose();
		}
	}
	
}
