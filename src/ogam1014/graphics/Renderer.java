package ogam1014.graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Renderer {

	public Image image;
	private Graphics2D graphics;
	private AffineTransform originTransform;
	private int width;
	private int height;
	private int realWidth;
	private int realHeight;

	public Renderer(int w, int h, int realWidth, int realHeight) {
		this.width = w;
		this.height = h;
		this.realWidth = realWidth;
		this.realHeight = realHeight;
		image = new BufferedImage(realWidth, realHeight, BufferedImage.TYPE_INT_RGB);
		graphics = (Graphics2D) image.getGraphics();
		originTransform = graphics.getTransform();
	}

	public void clear() {
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, width, height);
	}

	public void flip(Graphics parent) {
		parent.drawImage(image, 0, 0, realWidth, realHeight, 0, 0, width, height, null);
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
		double strLen = graphics.getFontMetrics().getStringBounds(text, graphics).getWidth();
		int start = (int) strLen/2;
		graphics.drawString(text, xx - start, yy);
	}
	
	public void drawCenteredText(String text, double x, double y, int w) {
		int xx = (int) Math.round(x);
		int yy = (int) Math.round(y);
		double strLen = graphics.getFontMetrics().getStringBounds(text, graphics).getWidth();
		int start = w/2 - (int) strLen/2;
		graphics.drawString(text, start + xx, yy - (graphics.getFontMetrics().getAscent() + graphics.getFontMetrics().getDescent())/2  + graphics.getFontMetrics().getAscent());
	}

	public void setColor(Color color) {
		graphics.setColor(color);
	}
	
	public void setStroke(int x) {
		graphics.setStroke(new BasicStroke(x));;
	}
}
