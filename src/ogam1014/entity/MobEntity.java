package ogam1014.entity;


public abstract class MobEntity extends Entity {

	protected double dx;
	protected double dy;
	protected double time;
	
	@Override
	public void update(double dt) {
		time += dt;
		dx = dx * getFriction();
		dy = dy * getFriction();
		x += dx * dt;
		y += dy * dt;
	}

	protected abstract double getFriction();

}
