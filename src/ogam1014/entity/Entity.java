package ogam1014.entity;

import java.awt.Graphics;

import ogam1014.Level;

public abstract class Entity {

	protected Level level;
	protected double x;
	protected double y;

	public abstract void update(double dt);

	public abstract void draw(Graphics g);

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
