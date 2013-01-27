package com.teammeatstick.game;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class SpriteAnimator implements ApplicationListener {

        private final int        FRAME_COLS = 2;         // #1
        private final int        FRAME_ROWS = 2;         // #2
        
        Animation                       walkAnimation;          // #3
        Texture                         walkSheet;              // #4
        TextureRegion[]                 walkFrames;             // #5
        SpriteBatch                     spriteBatch;            // #6
        public TextureRegion                   currentFrame;           // #7
        
        float stateTime;                                        // #8
        
        //public float xPos = 0;
        //public float yPos = 0;
        public Sprite mySprite;

        @Override
        public void create() {
                walkSheet = new Texture(Gdx.files.internal("textures/sprites/VirusSprite.png"));     // #9
                TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / 
FRAME_COLS, walkSheet.getHeight() / FRAME_ROWS);                                // #10
                walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
                int index = 0;
                for (int i = 0; i < FRAME_ROWS; i++) {
                        for (int j = 0; j < FRAME_COLS; j++) {
                                walkFrames[index++] = tmp[i][j];
                        }
                }
                walkAnimation = new Animation(0.025f, walkFrames);              // #11
                spriteBatch = new SpriteBatch();                                // #12
                stateTime = 0f;                                                 // #13
                currentFrame = walkAnimation.getKeyFrame(stateTime, true); 		// prime first frame
                mySprite = new Sprite(currentFrame);
        }

        @Override
        public void render() {
                //Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);                                            // #14
                stateTime += Gdx.graphics.getDeltaTime();                       // #15
                currentFrame = walkAnimation.getKeyFrame(stateTime, true);      // #16
                mySprite.setTexture(currentFrame.getTexture());
                spriteBatch.begin();
                spriteBatch.draw(currentFrame,
                				mySprite.getX() / Constants.WORLD_WIDTH_METERS,
                				mySprite.getY() / Constants.WORLD_HEIGHT_METERS,
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
