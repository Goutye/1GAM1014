package ogam1014.entity;

import java.awt.Color;
import java.awt.Graphics;

public class Bullet extends MobEntity {

	final private static double LIFETIME = 3.;
	final public static double SPEED = 600.;

	private Entity owner;

	public Bullet(Entity owner, double dx, double dy) {
		this.owner = owner;
		this.dx = dx;
		this.dy = dy;
		x = owner.getX() + owner.getWidth() / 2;
		y = owner.getY() + owner.getHeight() / 2;
	}

	@Override
	public void update(double dt) {
		super.update(dt);

		if (time >= LIFETIME) {
			level.removeEntity(this);
		}
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawRect((int) x, (int) y, (int) getWidth(), (int) getHeight());
	}

	@Override
	protected double getFriction() {
		return .99;
	}
	
	@Override
	public double getWidth() {
		return 2;
	}
	
	@Override
	public double getHeight() {
		return 2;
	}
}
