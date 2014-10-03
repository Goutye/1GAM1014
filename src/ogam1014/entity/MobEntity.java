package ogam1014.entity;


public abstract class MobEntity extends Entity {

	protected double dx;
	protected double dy;
	protected double time;
	
	@Override
	public void update(double dt) {
		time += dt;
		dx = dx * 0.98 * dt;
		dy = dy * 0.98 * dt;
		x += dx * dt;
		y += dy * dt;
	}

}
