package com.teammeatstick.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import sun.applet.Main;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Animator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxNativesLoader;

import com.esotericsoftware.tablelayout.BaseTableLayout.Debug;
import com.sun.xml.internal.stream.Entity;

public class MainGame implements ApplicationListener {
	private SpriteBatch batch;
	private Sprite sprite;
	private Audio gameAudio;
	
	private Background _background;
	
	private TextButton button;
	private Skin skin;
	
	private Stage stage;
	
    World world;
    Box2DDebugRenderer debugRenderer;  
    static final float BOX_STEP=1/60f;  
    static final int BOX_VELOCITY_ITERATIONS=6;  
    static final int BOX_POSITION_ITERATIONS=2;    
    
    private Body body;
	
	private SpriteAnimator _spriteAnimator;
	
	private Vector2 _pulseVector = new Vector2(1f, 0f);
	private Vector2 _dragVector = new Vector2(-0.001f, 0f);
	private float _pulseInterval = 2.0f;
	private float _pulseTime = 3.0f;
	private int _maxPulse;
	private float _basePulseX = 10f;
	private float _maxPulseDragPercent = .75f;
	private float _dragApplied = 0.0f;
    
	private Rectangle glViewport;
	
	private Player player1;
	private Baddie[] baddies;
	private int baddieCount = 5;		
    
	@Override
	public void create() {
		GdxNativesLoader.load();
		world = new World(new Vector2(0, 0), true);  

		Constants.CAMERA.setToOrtho(false,
						  Constants.WORLD_WIDTH_METERS,
						  Constants.WORLD_HEIGHT_METERS);// / Constants.PIXELS_PER_METER);//(Constants.WORLD_HEIGHT_METERS / 2 )* Constants.PIXELS_PER_METER);
		
		Constants.CAMERA.position.set(new Vector3((Constants.WORLD_WIDTH_METERS / 2),
										(Constants.WORLD_HEIGHT_METERS / 2),
										0.0f));
		Constants.CAMERA.update();//(w, h);//1, h/w);
		
		_spriteAnimator = new SpriteAnimator(2, 2, "textures/sprites/VirusSprite.png", 4);
		_spriteAnimator.create();
		_spriteAnimator.updatePosition((Constants.WORLD_WIDTH_METERS),//Constants.PIXELS_PER_METER),
									   (Constants.WORLD_HEIGHT_METERS));// * Constants.PIXELS_PER_METER);

		batch = new SpriteBatch();
		
		
		
		//Let's try to create a player!
		player1 = new Player(1,
				             1.0f,
				             new Vector2(Constants.WORLD_WIDTH_METERS/2,
								         Constants.WORLD_HEIGHT_METERS/2),
						     new Vector2(75,75),
						     world,
						     Constants.VIRUS_SPRITE);
		
		//Now for some baddies!
		baddies = new Baddie[baddieCount];
		for (int i = 0; i < baddieCount; i++){
			baddies[i] = new Baddie(i,
					                new Vector2(MathUtils.random(Constants.WORLD_WIDTH_METERS),
					                		    MathUtils.random(Constants.WORLD_HEIGHT_METERS)),
					                player1.position,
					                world,
					                Constants.VIRUS_SPRITE);
		}
		
		batch = new SpriteBatch();		
		gameAudio = new Audio();
		gameAudio.create();
		//set the music
		gameAudio.setMusic(Constants.HEART_BEAT_NORMAL,1.0f,true);
		gameAudio.music.play();
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
		
		_background = new Background();
		
		// Bottom body  
		BodyDef bottomBodyDef = new BodyDef();  
        bottomBodyDef.position.set(new Vector2(Constants.WORLD_WIDTH_METERS * 2, 0.0f));  
        Body bottomBody = world.createBody(bottomBodyDef);  
        PolygonShape bottomBox = new PolygonShape();  
        bottomBox.setAsBox(Constants.WORLD_WIDTH_METERS *2, 2.0f);  
        bottomBody.createFixture(bottomBox, 0.0f);
        
        BodyDef topBodyDef = new BodyDef();  
        topBodyDef.position.set(new Vector2(Constants.WORLD_WIDTH_METERS * 2, Constants.WORLD_HEIGHT_METERS));  
        Body topBody = world.createBody(topBodyDef);  
        PolygonShape topBox = new PolygonShape();  
        topBox.setAsBox(Constants.WORLD_WIDTH_METERS *2, 2.0f);  
        topBody.createFixture(topBox, 0.0f);
        
        //Dynamic Body  
        BodyDef bodyDef = new BodyDef();  
        bodyDef.type = BodyType.DynamicBody;  
        bodyDef.position.set(_spriteAnimator.mySprite.getX() * Constants.WORLD_TO_BOX,
        					 _spriteAnimator.mySprite.getY() * Constants.WORLD_TO_BOX);
        
        body = world.createBody(bodyDef);
        CircleShape dynamicCircle = new CircleShape();  
        dynamicCircle.setRadius(1.0f);  
        FixtureDef fixtureDef = new FixtureDef();  
        fixtureDef.shape = dynamicCircle;  
        fixtureDef.density = 0.1f;  
        fixtureDef.friction = 0.4f;  
        fixtureDef.restitution = 1;
        body.createFixture(fixtureDef);
        body.setUserData(_spriteAnimator);
        
        _spriteAnimator.mySprite.setOrigin(body.getPosition().x * Constants.WORLD_TO_BOX, body.getPosition().y * Constants.WORLD_TO_BOX);
        
        debugRenderer = new Box2DDebugRenderer();
	}

	@Override
	public void dispose() {
		gameAudio.dispose();
		batch.dispose();
		_background.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(1, 0, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		Constants.CAMERA.update();
		
		batch.setProjectionMatrix(Constants.CAMERA.combined);
		
		_background.Draw(batch);
		
		// Test Button
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		Table.drawDebug(stage);
		
		debugRenderer.render(world, Constants.CAMERA.combined);
		
		if(Gdx.input.justTouched()){
			//gameAudio.sound = Gdx.audio.newSound(Constants.COLLIDE_NANO);
			//Gdx.audio.newSound(Constants.COLLIDE_NANO).play();
			gameAudio.triggerSound(Constants.COLLIDE_NANO, 1f);
			//gameAudio.sound.play();
			System.out.println("playing sound?");
		}		
		
		//keypress stuff for the main body
		if(Gdx.input.isKeyPressed(Keys.UP))
		{
			Constants.CAMERA.position.y += 1;
		}
		if(Gdx.input.isKeyPressed(Keys.LEFT))
		{
			Constants.CAMERA.position.x -= 1;	
		}
		if(Gdx.input.isKeyPressed(Keys.DOWN))
		{
			Constants.CAMERA.position.y -= 1;
		}
		if(Gdx.input.isKeyPressed(Keys.RIGHT))
		{
			Constants.CAMERA.position.x += 1;
		}

		
		//WASD keyboard input
		if(Gdx.input.isKeyPressed(Keys.W))
		{
			player1.moveUp();
		}
		if(Gdx.input.isKeyPressed(Keys.A))
		{
			player1.moveLeft();
		}
		if(Gdx.input.isKeyPressed(Keys.S))
		{
			player1.moveDown();
		}
		if(Gdx.input.isKeyPressed(Keys.D))
		{
			player1.moveRight();
		}
		
		player1.draw();
		for(int i = 0; i < baddieCount; i++){
			//baddies[i].move();
			baddies[i].draw();
		}

		Pulse();
		
		debugRenderer.render(world, Constants.CAMERA.combined);
		
		// Physics
		world.step(BOX_STEP, BOX_VELOCITY_ITERATIONS, BOX_POSITION_ITERATIONS);
		
		_spriteAnimator.updatePosition((body.getPosition().x * Constants.BOX_TO_WORLD),
									   (body.getPosition().y * Constants.BOX_TO_WORLD));
		//_spriteAnimator.mySprite.setPosition(body.getPosition().x, body.getPosition().y);
		_spriteAnimator.render();
		
        //System.out.println("Body: " + body.getPosition().x + " " + body.getPosition().y);
	}

	public void Pulse()
	{
		_pulseTime += Gdx.graphics.getDeltaTime();
		
		//Gdx.app.debug("Pulse", "PT: " + _pulseTime);
		//System.out.println("PT: " + _pulseTime + " " + _pulseVector);
		if(_pulseTime > _pulseInterval)
		{
			//Gdx.app.debug("Pulse", "Go Pulse Go!");
			
			Iterator<Body> bodies = world.getBodies();
			while(bodies.hasNext())
			{
				Body currentBody = bodies.next();
				//Vector2 speedIncrease = currentBody.getLinearVelocity()
				//_pulseVector.add(_pulseVector);
				//currentBody.setLinearDamping(0.0f);
				//System.out.println(_pulseVector);
				//System.out.println();
				//currentBody.applyLinearImpulse(_pulseVector, currentBody.getPosition());
				//_pulseVector.add(-1.0f * _pulseVector);
				currentBody.applyLinearImpulse(_pulseVector, currentBody.getPosition());
				//System.out.println("" + currentBody.getLinearVelocity());
			}
			
			_pulseTime = Gdx.graphics.getDeltaTime();
			_dragApplied = 0.0f;
		}
		//else if((-1 * _dragApplied) < (_pulseVector.x * _maxPulseDragPercent))
		//{
		//	Iterator<Body> bodies = world.getBodies();
		//	while(bodies.hasNext())
		//	{
		//		Body currentBody = bodies.next();
		//		currentBody.applyForce(_dragVector, currentBody.getPosition());
		//		_dragApplied = _dragApplied + _dragVector.x;
		//	}
		//}
	}
	
	@Override
	public void resize(int width, int height) {
	   Vector3 oldpos = new Vector3(Constants.CAMERA.position);
	   Constants.CAMERA.setToOrtho(false,
	                      width / Constants.PIXELS_PER_METER,
	                      height / Constants.PIXELS_PER_METER);
	   Constants.CAMERA.translate(oldpos.x-Constants.CAMERA.position.x, oldpos.y-Constants.CAMERA.position.y);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
