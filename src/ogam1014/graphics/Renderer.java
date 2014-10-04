package ogam1014.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Renderer {

	private int width;
	private int height;
	public Image image;
	private Graphics2D graphics;

	public Renderer(int w, int h) {
		this.width = w;
		this.height = h;
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		graphics = (Graphics2D) image.getGraphics();
	}

	public void clear() {
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, width, height);
	}

	public void flip(Graphics parent) {
		parent.drawImage(image, 0, 0, width, height, null);
	}

	public void blit(Image image, double x, double y, int w, int h, int sourceX,
			int sourceY) {
		graphics.drawImage(image, (int) x, (int) y, (int)x + w, (int) y + h, sourceX, sourceY, sourceX + w,
				sourceY + h, null);
	}
	
	public Graphics2D getGraphics() {
		return graphics;
	}
}
