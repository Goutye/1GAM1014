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

		double xx = x + dx * dt;
		double yy = y + dy * dt;
		int w = getWidth();
		int h = getHeight();
		boolean collideX = false;
		boolean collideY = false;
		
		if (!collideY && level.getTile(x, yy).isWall()) collideY = true;
		if (!collideY && level.getTile(x, yy+h).isWall()) collideY = true;
		
		if (!collideX && level.getTile(xx, y).isWall()) collideX = true;
		if (!collideX && level.getTile(xx+w, y).isWall()) collideX = true;
		
		if (!collideX && !collideY && level.getTile(xx, yy).isWall()) collideX = collideY = true;
		if (!collideX && !collideY && level.getTile(xx+w, yy+h).isWall()) collideX = collideY = true;
		

		if (!collideX || !collidesWithWalls()) {
			x = xx;
		}
		if (!collideY || !collidesWithWalls()) {
			y = yy;
		}
	}

	protected abstract double getFriction();

	protected boolean collidesWithWalls() {
		return true;
	}

}
