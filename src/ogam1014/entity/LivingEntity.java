package ogam1014.entity;

public abstract class LivingEntity extends MobEntity {
	protected double health = 10;
	
	@Override
	public void update(double dt) {
		super.update(dt);
		if (health <= 0) {
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

}
