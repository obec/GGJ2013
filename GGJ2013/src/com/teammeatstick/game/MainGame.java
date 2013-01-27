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
		player1 = new Player(1, 1.0f, new Vector2(Gdx.graphics.getWidth()/2,Gdx.graphics.getWidth()/2), new Vector2(75,75), world, Constants.VIRUS_SPRITE);
		
		//Now for some baddies!
		baddies = new Baddie[baddieCount];
		for (int i = 0; i < baddieCount; i++){
			baddies[i] = new Baddie(i, new Vector2(MathUtils.random(Constants.SCREEN_WIDTH*2),Constants.SCREEN_HEIGHT), player1.position, world, Constants.VIRUS_SPRITE);
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
		
		//(Drawable) new Texture(Gdx.files.internal("textures/gui/TestButton.png")

		
		//sprite = new Sprite(region);
		//sprite.setSize(0.9f, 0.9f * sprite.getHeight() / sprite.getWidth());
		//sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		//sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);
		
		_background = new Background();
		
		//Ground body  
       /* BodyDef groundBodyDef = new BodyDef();  
        groundBodyDef.position.set(new Vector2(Constants.WORLD_WIDTH_METERS * 2, 0.0f));  
        Body groundBody = world.createBody(groundBodyDef);  
        PolygonShape groundBox = new PolygonShape();  
        groundBox.setAsBox(Constants.WORLD_WIDTH_METERS *2, 2.0f);  
        groundBody.createFixture(groundBox, 0.0f);  */
        //Dynamic Body  
        BodyDef bodyDef = new BodyDef();  
        bodyDef.type = BodyType.DynamicBody;  
        bodyDef.position.set(/*Constants.WORLD_WIDTH_METERS / 2,//*/_spriteAnimator.mySprite.getX() * Constants.WORLD_TO_BOX,
        					 /*Constants.WORLD_HEIGHT_METERS / 2); //*/_spriteAnimator.mySprite.getY() * Constants.WORLD_TO_BOX);//Constants.CAMERA.viewportWidth / 2, Constants.CAMERA.viewportHeight / 2);  
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
        
        //_spriteAnimator.updatePosition((int)(body.getPosition().x * Constants.BOX_TO_WORLD),
        //							   (int)(body.getPosition().y * Constants.BOX_TO_WORLD));
        
        //ChainShape chainShape = new ChainShape();
        //chainShape.
	}

	@Override
	public void dispose() {
		batch.dispose();
		_background.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(1, 0, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		Constants.CAMERA.update();
		//Constants.CAMERA.position.set(0f, Gdx.graphics.getHeight() / 2f, 0f);
		//Constants.CAMERA.position.set((1 * body.getPosition().x) - 20, Gdx.graphics.getHeight() / 2, 0f);
		//Constants.CAMERA.position += Constants.CAMERA.d;
		
		//if(Gdx.input.isKeyPressed(Keys.K))
		//{
			//Constants.CAMERA.position.add(10.f, 0f, 0f);
		/*float cameraX = Constants.CAMERA.position.x;

		batch.setProjectionMatrix(Constants.Constants.CAMERA.combined);
		
		//Constants.CAMERA.position.set(0f, Gdx.graphics.getHeight() / 2f, 0f);
		//Constants.CAMERA.position.set((1 * body.getPosition().x) - 20, Gdx.graphics.getHeight() / 2, 0f);
		//Constants.CAMERA.position += Constants.CAMERA.d;
		
		//if(Gdx.input.isKeyPressed(Keys.K))
		//{
			//camera.position.add(10.f, 0f, 0f);
		float cameraX = Constants.Constants.CAMERA.position.x;
		
		float minimumX = (body.getPosition().x * BOX_WORLD_TO - 50) + (Gdx.graphics.getWidth() / 2f);
		float maximumX = body.getPosition().x * BOX_WORLD_TO + 100f;
		
		if((body.getPosition().x * BOX_WORLD_TO - 50) + (Gdx.graphics.getWidth() / 2f) < Constants.CAMERA.position.x)
		{
			cameraX = minimumX;
		}
		else if(maximumX > Constants.Constants.CAMERA.position.x)
		{
			cameraX = maximumX;
		}
		else
		{
			cameraX += 100f * Gdx.graphics.getDeltaTime();
		}
		
		
		//Constants.CAMERA.position.set((body.getPosition().x - 50) + (Gdx.graphics.getWidth() / 2f), Constants.CAMERA.position.y, 0f);
		Constants.CAMERA.position.set(cameraX, Constants.CAMERA.position.y, 0f);
		Constants.CAMERA.update();
			cameraX += 10f * Gdx.graphics.getDeltaTime();
		}
		
		
		//Constants.CAMERA.position.set((body.getPosition().x - 50) + (Gdx.graphics.getWidth() / 2f), Constants.CAMERA.position.y, 0f);
		Constants.CAMERA.position.set(cameraX, Constants.CAMERA.position.y, 0f);
		Constants.CAMERA.update();*/
		
		//Vector2 velocity = body.getLinearVelocity();
			
			//System.out.println("C: " + Constants.CAMERA.position);
			//System.out.println("B: " + body.getPosition());
		//}
		//Constants.CAMERA.position.add(10f, 0f, 0f);
		//Constants.CAMERA.update();
		//System.out.println("C: " + Constants.CAMERA.position);
		//Constants.CAMERA.update();
		
		batch.setProjectionMatrix(Constants.CAMERA.combined);
		
		_background.Draw(batch);
		
		
		//Vector2 spritePos = body.getPosition();
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		Table.drawDebug(stage);
		
		debugRenderer.render(world, Constants.CAMERA.combined);
		
		if(Gdx.input.justTouched()){
			//gameAudio.sound = Gdx.audio.newSound(Constants.COLLIDE_NANO);
			//Gdx.audio.newSound(Constants.COLLIDE_NANO).play();
			gameAudio.triggerSound(Constants.COLLIDE_NANO, 0.5f);
			//gameAudio.sound.play();
			System.out.println("playing sound?");
		}		
		
		//keypress stuff for the main body
		if(Gdx.input.isKeyPressed(Keys.W))
		{
			body.applyLinearImpulse(new Vector2(0, 0.05f), body.getPosition());
			//Constants.CAMERA.position.y += 1;
		}
		if(Gdx.input.isKeyPressed(Keys.A))
		{
			//Constants.CAMERA.position.x -= 1;
			body.applyLinearImpulse(new Vector2(-0.05f, 0), body.getPosition());
		}
		if(Gdx.input.isKeyPressed(Keys.S))
		{
			//Constants.CAMERA.position.y -= 1;
			body.applyLinearImpulse(new Vector2(0, -0.05f), body.getPosition());
		}
		if(Gdx.input.isKeyPressed(Keys.D))
		{
			//Constants.CAMERA.position.x += 1;
			body.applyLinearImpulse(new Vector2(0.05f, 0), body.getPosition());
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
			baddies[i].move();
			baddies[i].draw();
		}

		//Pulse();
		
		debugRenderer.render(world, Constants.CAMERA.combined);
		
		// Physics
		world.step(BOX_STEP, BOX_VELOCITY_ITERATIONS, BOX_POSITION_ITERATIONS);
		
		_spriteAnimator.updatePosition((body.getPosition().x * Constants.BOX_TO_WORLD),
									   (body.getPosition().y * Constants.BOX_TO_WORLD));
		//_spriteAnimator.mySprite.setPosition(body.getPosition().x, body.getPosition().y);
		_spriteAnimator.render();
		
        System.out.println("Body: " + body.getPosition().x + " " + body.getPosition().y);
	}

	/*public void Pulse()
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
	}*/
	
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
