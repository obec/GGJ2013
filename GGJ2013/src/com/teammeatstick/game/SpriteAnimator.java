package com.teammeatstick.game;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
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
        
        int xPos = 0;
        int yPos = 0;

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
                spriteAnimation = new Animation(1/FRAMES_PER_SECOND, spriteFrames);
                spriteBatch = new SpriteBatch();
                stateTime = 0f;
                currentFrame = spriteAnimation.getKeyFrame(stateTime, true); 		// prime first frame
        }

        @Override
        public void render() {
                stateTime += Gdx.graphics.getDeltaTime();
                currentFrame = spriteAnimation.getKeyFrame(stateTime, true);
                spriteBatch.setProjectionMatrix(Constants.CAMERA.combined);
                spriteBatch.begin();
                spriteBatch.draw(currentFrame, xPos, yPos);
                spriteBatch.end();
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
		
		public void updatePosition(int x, int y)
		{
			xPos = x - (currentFrame.getRegionWidth()/2);
			yPos = y - (currentFrame.getRegionHeight()/2);
		}
}
