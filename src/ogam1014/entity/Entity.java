package ogam1014.entity;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import ogam1014.Level;
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

	public abstract double getWidth();

	public abstract double getHeight();
}
