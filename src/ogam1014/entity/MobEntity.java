package ogam1014.entity;

import java.awt.Point;

import ogam1014.collide.Box;

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
		int w = getWidth()-1;
		int h = getHeight()-1;
		boolean collideX = false;
		boolean collideY = false;
		
		if (!collideY && level.getTile(x, dy < 0 ? yy : yy + h).isWall()) collideY = true;
		if (!collideY && level.getTile(x+w, dy < 0 ? yy : yy + h).isWall()) collideY = true;
		
		if (!collideX && level.getTile(dx < 0 ? xx : xx + w, y).isWall()) collideX = true;
		if (!collideX && level.getTile(dx < 0 ? xx : xx + w, y+h).isWall()) collideX = true;
		
		
		
		if (!collideX && !collideY && level.getTile(dx < 0 ? xx : xx + w, dy < 0 ? yy : yy + h).isWall()) collideX = collideY = true;
		if (!collideX && !collideY && level.getTile(dx < 0 ? xx : xx + w, dy >= 0 ? yy : yy + h).isWall()) collideX = collideY = true;
		if (!collideX && !collideY && level.getTile(dx >= 0 ? xx : xx + w, dy < 0 ? yy : yy + h).isWall()) collideX = collideY = true;
		

		if (!collideX || !collidesWithWalls()) {
			x = xx;
		}
		else {
			dx = 0;
		}
		
		if (!collideY || !collidesWithWalls()) {
			y = yy;
		}
		else {
			dy = 0;
		}
	}

	public void setPosition(Point p) {
		x = p.x;
		y = p.y;
	}

	protected abstract double getFriction();

	protected boolean collidesWithWalls() {
		return true;
	}
}
