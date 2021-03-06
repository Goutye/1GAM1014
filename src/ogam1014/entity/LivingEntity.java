package ogam1014.entity;

public abstract class LivingEntity extends MobEntity {

	private static final long serialVersionUID = 5051404621894792305L;
	protected double health = 10;

	public LivingEntity() {
		this.friction = 0.8;
	}

	@Override
	public void update(double dt) {
		super.update(dt);
		if (health <= 0) {
			onDeath();
			level.removeEntity(this);
		}
	}

	@Override
	protected boolean collidesWith(Entity e) {
		return e instanceof LivingEntity;
	}

	@Override
	protected void onCollision(Entity other) {
		if (other instanceof MobEntity) {
			MobEntity m = (MobEntity) other;
			m.dx += dx;
			m.dy += dy;
			dx = 0;
			dy = 0;
		}
	}

	public void takeDamage(double n) {
		health -= n;
	}

	protected void onDeath() {
	}

}
