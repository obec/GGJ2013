package com.teammeatstick.game;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class SpriteAnimator implements ApplicationListener {

        private int			FRAME_COLS;
        private int			FRAME_ROWS;
        private float		FRAMES_PER_SECOND;
        private String		filePath;
        
        Animation			spriteAnimation;
        Texture				spriteSheet;
        TextureRegion[]		spriteFrames;
        SpriteBatch			spriteBatch;
        TextureRegion		currentFrame;
        
        float stateTime;
        
        public Sprite mySprite;

        public SpriteAnimator(int col, int row, String relativeFilePath, int framesPerSecond) {
        	FRAME_COLS = col;
        	FRAME_ROWS = row;
        	filePath = relativeFilePath;
        	FRAMES_PER_SECOND = framesPerSecond;
        	this.create();
        }
        
        @Override
        public void create() {
                spriteSheet = new Texture(Gdx.files.internal(filePath));
                TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / FRAME_COLS, spriteSheet.getHeight() / FRAME_ROWS);
                spriteFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
                int index = 0;
                for (int i = 0; i < FRAME_ROWS; i++) {
                        for (int j = 0; j < FRAME_COLS; j++) {
                                spriteFrames[index++] = tmp[i][j];
                        }
                }
                
                spriteAnimation = new Animation(0.025f, spriteFrames);           
                spriteBatch = new SpriteBatch();                                
                stateTime = 0f;                                                 
                currentFrame = spriteAnimation.getKeyFrame(stateTime, true);
                mySprite = new Sprite(currentFrame);
        }

        @Override
        public void render() {
                //Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);                                            // #14
                stateTime += Gdx.graphics.getDeltaTime();                       // #15
                currentFrame = spriteAnimation.getKeyFrame(stateTime, true);      // #16
                mySprite.setTexture(currentFrame.getTexture());
                spriteBatch.setProjectionMatrix(Constants.CAMERA.combined);
                spriteBatch.begin();
                spriteBatch.draw(currentFrame,
                				mySprite.getX(),
                				mySprite.getY(),
                				currentFrame.getRegionWidth() / Constants.PIXELS_PER_METER,
                				currentFrame.getRegionHeight() / Constants.PIXELS_PER_METER);
                spriteBatch.end();
                
                //System.out.println("Txt: " + xPos * Constants.WORLD_TO_BOX + " " + yPos * Constants.WORLD_TO_BOX);
        }
        
        public void setCamera(Camera camera)
        {
        	spriteBatch.setProjectionMatrix(camera.combined);
        }

        @Override
		public void resize(int width, int height) {
			// TODO Auto-generated method stub
			
		}

        @Override
		public void pause() {
			// TODO Auto-generated method stub
			
		}

        @Override
		public void resume() {
			// TODO Auto-generated method stub
			
		}

        @Override
		public void dispose() {
			// TODO Auto-generated method stub
			
		}
		
		public void updatePosition(float x, float y)
		{
			mySprite.setPosition(x - (currentFrame.getRegionWidth() / Constants.PIXELS_PER_METER),
								 y - (currentFrame.getRegionHeight() / Constants.PIXELS_PER_METER));
		}
}
