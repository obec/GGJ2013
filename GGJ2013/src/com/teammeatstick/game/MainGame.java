package com.teammeatstick.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import sun.applet.Main;

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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
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

public class MainGame implements ApplicationListener {
	private OrthographicCamera camera;
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
    static final float WORLD_TO_BOX=0.01f;  
    static final float BOX_WORLD_TO=100f;
    private Body body;
	
	private SpriteAnimator _spriteAnimator;
	
	private Vector2 _pulseVector = new Vector2(100000f, 0f);
	private Vector2 _dragVector = new Vector2(-20000.0f, 0f);
	private float _pulseInterval = 3.0f;
	private float _pulseTime = 3.0f;
	private int _maxPulse;
    
	@Override
	public void create() {
		_spriteAnimator = new SpriteAnimator(2, 2, "textures/sprites/VirusSprite.png", 4);
		GdxNativesLoader.load();
		world = new World(new Vector2(0, 0), true);  
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera(); //(w, h);//1, h/w);
		camera.setToOrtho(false, w, h);
		batch = new SpriteBatch();
		
		//Let's try to create a player!
		Player player1 = new Player(1, 1.0f, new Vector2(50,50), new Vector2(75,75), world);
		
		batch = new SpriteBatch();		
		gameAudio = new Audio();
		//gameAudio.create();		

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
        BodyDef groundBodyDef =new BodyDef();  
        groundBodyDef.position.set(new Vector2(0, 10));  
        Body groundBody = world.createBody(groundBodyDef);  
        PolygonShape groundBox = new PolygonShape();  
        groundBox.setAsBox((camera.viewportWidth) * 2, 10.0f);  
        groundBody.createFixture(groundBox, 0.0f);  
        //Dynamic Body  
        BodyDef bodyDef = new BodyDef();  
        bodyDef.type = BodyType.DynamicBody;  
        bodyDef.position.set(50.0f, 400.0f);//camera.viewportWidth / 2, camera.viewportHeight / 2);  
        body = world.createBody(bodyDef);
        CircleShape dynamicCircle = new CircleShape();  
        dynamicCircle.setRadius(50f);  
        FixtureDef fixtureDef = new FixtureDef();  
        fixtureDef.shape = dynamicCircle;  
        fixtureDef.density = 0.1f;  
        fixtureDef.friction = 1.0f;  
        fixtureDef.restitution = 1;  
        body.createFixture(fixtureDef);  
        debugRenderer = new Box2DDebugRenderer();
        
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
		batch.setProjectionMatrix(camera.combined);
		
		//camera.position.set(0f, Gdx.graphics.getHeight() / 2f, 0f);
		//camera.position.set((1 * body.getPosition().x) - 20, Gdx.graphics.getHeight() / 2, 0f);
		//camera.position += camera.d;
		
		//if(Gdx.input.isKeyPressed(Keys.K))
		//{
			//camera.position.add(10.f, 0f, 0f);
		float cameraX = camera.position.x;
		
		float minimumX = (body.getPosition().x - 50) + (Gdx.graphics.getWidth() / 2f);
		float maximumX = body.getPosition().x + 100f;
		
		if((body.getPosition().x - 50) + (Gdx.graphics.getWidth() / 2f) < camera.position.x)
		{
			cameraX = minimumX;
		}
		else if(maximumX > camera.position.x)
		{
			cameraX = maximumX;
		}
		else
		{
			cameraX += 10f * Gdx.graphics.getDeltaTime();
		}
		
		
		//camera.position.set((body.getPosition().x - 50) + (Gdx.graphics.getWidth() / 2f), camera.position.y, 0f);
		camera.position.set(cameraX, camera.position.y, 0f);
		camera.update();
	
		//Vector2 velocity = body.getLinearVelocity();
			
			//System.out.println("C: " + camera.position);
			//System.out.println("B: " + body.getPosition());
		//}
		//camera.position.add(10f, 0f, 0f);
		//camera.update();
		//System.out.println("C: " + camera.position);
		//camera.update();
		
		_background.Draw(batch);
		
		
		//Vector2 spritePos = body.getPosition();
		_spriteAnimator.updatePosition((int)body.getPosition().x /*+ (Gdx.graphics.getWidth() /2)*/, (int)(body.getPosition().y));
		_spriteAnimator.render();
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		Table.drawDebug(stage);
		
		debugRenderer.render(world, camera.combined);
		//batch.draw(texture2, 0, 0);
		//batch.draw(_background.GetBackgroundTxt(), 800/2, 20);

		//sprite.draw(batch);
		
		if(Gdx.input.justTouched()){
			//gameAudio.sound.play();
			System.out.println("playing sound?");
		}		

		if(Gdx.input.isKeyPressed(Keys.W))
		{
			//Gdx.app.log("Physics", "Applying force UP");
			//body.applyLinearImpulse(new Vector2(0, 5000), body.getPosition());
			Vector2 pos = body.getPosition();
			body.setTransform(pos.x, pos.y + 100 * Gdx.graphics.getDeltaTime(), body.getAngle());
		}
		if(Gdx.input.isKeyPressed(Keys.A))
		{
			body.applyLinearImpulse(new Vector2(-50000, 0), body.getPosition());
		}
		if(Gdx.input.isKeyPressed(Keys.S))
		{
			Vector2 pos = body.getPosition();
			body.setTransform(pos.x, pos.y + -100 * Gdx.graphics.getDeltaTime(), body.getAngle());
			//body.applyLinearImpulse(new Vector2(0, -5000), body.getPosition());
		}
		if(Gdx.input.isKeyPressed(Keys.D))
		{
			body.applyLinearImpulse(new Vector2(50000, 0), body.getPosition());
		}
		
		Pulse();
		
		// Physics
		world.step(BOX_STEP, BOX_VELOCITY_ITERATIONS, BOX_POSITION_ITERATIONS); 
	}

	public void Pulse()
	{
		_pulseTime += Gdx.graphics.getDeltaTime();
		
		Gdx.app.debug("Pulse", "PT: " + _pulseTime);
		//System.out.println("PT: " + _pulseTime + " " + _pulseVector);
		if(_pulseTime > _pulseInterval)
		{
			//Gdx.app.debug("Pulse", "Go Pulse Go!");
			
			Iterator<Body> bodies = world.getBodies();
			while(bodies.hasNext())
			{
				Body currentBody = bodies.next();
				//Vector2 speedIncrease = currentBody.getLinearVelocity() 
				currentBody.applyLinearImpulse(_pulseVector, currentBody.getPosition());
			}
			
			_pulseTime = Gdx.graphics.getDeltaTime();
		}
		else
		{
			Iterator<Body> bodies = world.getBodies();
			while(bodies.hasNext())
			{
				Body currentBody = bodies.next();
				currentBody.applyForce(_dragVector, currentBody.getPosition());
			}
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
