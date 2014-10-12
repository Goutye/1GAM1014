package ogam1014.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import ogam1014.Level;
import ogam1014.collide.Box;
import ogam1014.graphics.Renderer;

public abstract class Entity {

	static protected Image IMAGE;
	static {
		try {
			IMAGE = ImageIO.read(new File("assets/entities.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	protected Level level;
	protected double x;
	protected double y;
	protected int w;
	protected int h;
	protected Direction dir;
	protected Box box;
	
	
	public abstract void update(double dt);

	public abstract void draw(Renderer r);

	public void setLevel(Level level) {
		this.level = level;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int getWidth() {
		return w;
	};

	public int getHeight() {
		return h;
	};
	
	public Box getBox() {
		return box;
	}
	
	public void drawBox(Renderer r) {
		r.setColor(Color.red);
		r.getGraphics().drawRect(box.x, box.y, box.width, box.height);
	}
}
