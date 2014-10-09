package ogam1014.entity;

import ogam1014.graphics.Renderer;

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
	public void draw(Renderer r) {
		r.blit(IMAGE, x, y, 32, 32, 0, 32);
	}

	@Override
	protected double getFriction() {
		return .99;
	}
	
	@Override
	public int getWidth() {
		return 2;
	}
	
	@Override
	public int getHeight() {
		return 2;
	}
}
