package ogam1014.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Renderer {

	private int width;
	private int height;
	public Image image;
	private Graphics2D graphics;
	private AffineTransform originTransform;

	public Renderer(int w, int h) {
		this.width = w;
		this.height = h;
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		graphics = (Graphics2D) image.getGraphics();
		originTransform = graphics.getTransform();
	}

	public void clear() {
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, width, height);
	}

	public void flip(Graphics parent) {
		parent.drawImage(image, 0, 0, width, height, null);
	}

	public void blit(Image image, double x, double y, int w, int h,
			int sourceX, int sourceY) {
		int xx = (int) Math.round(x);
		int yy = (int) Math.round(y);
		graphics.drawImage(image, xx, yy, xx + w, yy + h, sourceX, sourceY,
				sourceX + w, sourceY + h, null);
	}

	public Graphics2D getGraphics() {
		return graphics;
	}

	public void useCamera(Camera camera) {
		graphics.setTransform(originTransform);
		if (camera != null) {
			graphics.translate(-camera.getX() + width / 2, -camera.getY()
					+ height / 2);
		}
	}

	public void drawText(String text, double x, double y) {
		int xx = (int) Math.round(x);
		int yy = (int) Math.round(y);
		graphics.drawString(text, xx, yy);
	}

	public void drawCenteredText(String text, double x, double y) {
		int xx = (int) Math.round(x);
		int yy = (int) Math.round(y);
		xx -= text.length() * 8 / 2;
		graphics.drawString(text, xx, yy);
	}

	public void setColor(Color color) {
		graphics.setColor(color);
	}
}
