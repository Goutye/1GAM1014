package ogam1014.entity;

import ogam1014.graphics.Renderer;

public class Bullet extends MobEntity {

	final private static double LIFETIME = 3.;
	final public static double SPEED = 600.;

	private Entity owner;
	private double originalDx;
	private double originalDy;

	public Bullet(Entity owner, double dx, double dy) {
		this.owner = owner;
		this.originalDx = dx;
		this.originalDy = dy;
		x = owner.getX() + owner.getWidth() / 2;
		y = owner.getY() + owner.getHeight() / 2;
	}

	@Override
	public void update(double dt) {
		this.dx = this.originalDx;
		this.dy = this.originalDy;
		super.update(dt);

		if (time >= LIFETIME) {
			level.removeEntity(this);
		}
	}

	@Override
	public void draw(Renderer r) {
		r.blit(IMAGE, x - 4, y - 4, 8, 8, 0, 32);
	}

	@Override
	protected boolean collidesWith(Entity e) {
		return e != owner && !(e instanceof Bullet);
	}

	@Override
	public void onCollision(Entity other) {
		if (other instanceof LivingEntity) {
			LivingEntity e = (LivingEntity) other;
			e.takeDamage(1);
		}
		level.removeEntity(this);
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

	@Override
	protected boolean collidesWithWalls() {
		level.removeEntity(this);
		return true;
	}
}
