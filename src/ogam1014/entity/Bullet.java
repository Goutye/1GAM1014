package ogam1014.entity;

import ogam1014.collide.Box;
import ogam1014.equipment.BulletType;
import ogam1014.graphics.Renderer;

public class Bullet extends MobEntity {
	private static final long serialVersionUID = 4341836626802694282L;
	final private static double LIFETIME = 3.;
	final public static double SPEED = 600.;

	private Entity owner;
	private BulletType type;
	private double originalDx;
	private double originalDy;

	public Bullet(Entity owner, BulletType type, double dx, double dy) {
		this.owner = owner;
		this.type = type;
		this.originalDx = dx;
		this.originalDy = dy;
		x = owner.getX() + owner.getWidth()/2;
		y = owner.getY() + owner.getHeight()/2;
		w = 4;
		h = 4;
		hIgnored = 0;
		this.box = new Box((int) x, (int) (y), w, (int) (h));
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
		r.blit(IMAGE, x - 2, y - 2, 8, 8, 16, 44);
	}

	@Override
	protected boolean collidesWith(Entity e) {
		return e != owner && e instanceof LivingEntity;
	}

	@Override
	public void onCollision(Entity other) {
		if (other instanceof LivingEntity) {
			LivingEntity e = (LivingEntity) other;
			e.takeDamage(type.getDamage());
		}
		level.removeEntity(this);
	}

	@Override
	public int getWidth() {
		return w;
	}

	@Override
	public int getHeight() {
		return h;
	}

	@Override
	protected boolean collidesWithWalls() {
		level.removeEntity(this);
		return true;
	}
}
