package ogam1014.entity;

import java.awt.Color;
import java.awt.Graphics;

public class Bullet extends MobEntity {

	final private static double LIFETIME = 3.;
	final private static double SPEED = 900.;

	private Entity owner;

	public Bullet(Entity owner) {
		this.owner = owner;
		x = owner.getX();
		y = owner.getY();
		dx = owner.getDirection().x * SPEED;
		dy = owner.getDirection().y * SPEED;
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
		g.drawRect((int) x, (int) y, 2, 2);
	}

}
