package SpriteSheet;

import java.awt.image.BufferedImage;

public class SpriteSheet {

	public BufferedImage SpriteSheet;
	
	public SpriteSheet(BufferedImage ss) {
		this.SpriteSheet = ss;
	}
	
	public BufferedImage grabSprite(int x, int y, int width, int height) {
		BufferedImage sprite = SpriteSheet.getSubimage(x, y, width, height);
		return sprite;
	}
}
