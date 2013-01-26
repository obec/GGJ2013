package com.teammeatstick.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import com.esotericsoftware.tablelayout.BaseTableLayout.Debug;

import com.teammeatstick.game.Audio;


public class MainGame implements ApplicationListener {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Texture texture2;
	TextureRegion region;
	private TextureRegion region2;
	private Sprite sprite;
	private Audio gameAudio;
	
	private Background _background;
	
	private TextButton button;
	private Skin skin;
	
	private Stage stage;
	
	
	@Override
	public void create() {		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera(); //(w, h);//1, h/w);
		camera.setToOrtho(false, w, h);
		batch = new SpriteBatch();
		
		//texture = new Texture(Gdx.files.internal("data/libgdx.png"));
		texture = new Texture(Gdx.files.internal("textures/backgrounds/TestTexture.png"));
		//texture = new Texture(Gdx.files.internal("textures/backgrounds/TestBackground.png"));
		//texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		region = new TextureRegion(texture, 0, 0, 10, 10);

		
		batch = new SpriteBatch();		
		gameAudio = new Audio();
		gameAudio.create();

		texture = new Texture(Gdx.files.internal("data/libgdx.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		
		texture2 = new Texture(Gdx.files.internal("textures/backgrounds/TestBackground6.png"));
		region2 = new TextureRegion(texture2, 0, texture2.getHeight() - Gdx.graphics.getHeight() , Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		

		stage = new Stage(20, 400, true);
		Table table = new Table();
		table.setY(200);
		table.setX(50);
		TextButtonStyle style = new TextButtonStyle();
		style.font = new BitmapFont();
		style.fontColor = Color.WHITE;
		
		skin = new Skin();
		skin.add("logo", new Texture(Gdx.files.internal("textures/gui/TestButton.png")));
		button = new TextButton("ClickMe", style);
		
		table.add(button);
		stage.addActor(table);

		//(Drawable) new Texture(Gdx.files.internal("textures/gui/TestButton.png")

		
		//sprite = new Sprite(region);
		//sprite.setSize(0.9f, 0.9f * sprite.getHeight() / sprite.getWidth());
		//sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		//sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);
		
		_background = new Background();
		_background.LoadBackground();
	}

	@Override
	public void dispose() {
		batch.dispose();
		texture.dispose();
		//_background.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(1, 0, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		//sprite.draw(batch);
		batch.draw(region2, 0, 0);
		batch.end();
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		Table.drawDebug(stage);
		
		//batch.draw(texture2, 0, 0);
		//Texture txt = _background.GetBackgroundTxt();
		//batch.draw(_background.GetBackgroundTxt(), 800/2, 20);

		//sprite.draw(batch);
		
		if(Gdx.input.justTouched()){
			gameAudio.sound.play();
			System.out.println("playing sound?");
		}
		
		if(Gdx.input.isKeyPressed(Keys.RIGHT) &&
		   region2.getRegionX() < (texture2.getWidth() - region2.getRegionWidth()))
		{
			region2.scroll(1 * Gdx.graphics.getDeltaTime(), 0);
		}
		if(Gdx.input.isKeyPressed(Keys.LEFT) &&
		   region2.getRegionX() > 0)
		{
			region2.scroll(-1 * Gdx.graphics.getDeltaTime(), 0);
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
