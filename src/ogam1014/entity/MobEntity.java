package ogam1014.entity;

import java.awt.Point;

import ogam1014.Tile;
import ogam1014.collide.Collide;

public abstract class MobEntity extends Entity {
	protected static double PERSPECTIVE = 0.5; // 0.5 => 50% du haut du sprite ignor� dans les collisions.
	
	protected double dx;
	protected double dy;
	protected double time;

	@Override
	public void update(double dt) {
		time += dt;
		dx = dx * getFriction();
		dy = dy * getFriction();

		testWallCollision(dt);
		testEntityCollision(dt);

		x += dx * dt;
		y += dy * dt;
	}
	
	private void testWallCollision(double dt) {
		double y = this.y + Math.max( getHeight() * PERSPECTIVE, getHeight() - Tile.SIZE);
		double xx = x + dx * dt;
		double yy = y + dy * dt;
		int w = getWidth()-1;
		int h = getHeight() - (int) Math.max( getHeight() * PERSPECTIVE, getHeight() - Tile.SIZE);
		
		
		boolean collideX = false;
		boolean collideY = false;
		
		if (!collideY && level.getTile(x, dy < 0 ? yy : yy + h).isWall()) collideY = true;
		if (!collideY && level.getTile(x+w, dy < 0 ? yy : yy + h).isWall()) collideY = true;
		
		if (!collideX && level.getTile(dx < 0 ? xx : xx + w, y).isWall()) collideX = true;
		if (!collideX && level.getTile(dx < 0 ? xx : xx + w, y+h).isWall()) collideX = true;
		
		if (!collideX && !collideY && level.getTile(dx < 0 ? xx : xx + w, dy < 0 ? yy : yy + h).isWall()) collideX = collideY = true;
		if (!collideX && !collideY && level.getTile(dx < 0 ? xx : xx + w, dy >= 0 ? yy : yy + h).isWall()) collideX = collideY = true;
		if (!collideX && !collideY && level.getTile(dx >= 0 ? xx : xx + w, dy < 0 ? yy : yy + h).isWall()) collideX = collideY = true;

		if (collideX && collidesWithWalls()) {
			dx = 0;
		}
		if (collideY && collidesWithWalls()) {
			dy = 0;
		}
	}
	
	private void testEntityCollision(double dt) {
		double xx = x + dx * dt;
		double yy = y + dy * dt;
		for (Entity e : level.getEntityNear(xx, yy, getWidth(), getHeight())) {
			if (e == this)
				continue;
			
			boolean collide = Collide.aabb(xx, yy, getWidth(), getHeight(), e.x, e.y, e.getWidth(), e.getHeight());
			if (collide && collidesWith(e)) {
				this.onCollision(e);
				break;
			}
		}
	}
	
	abstract protected boolean collidesWith(Entity e);
	abstract protected void onCollision(Entity other);
	
	public void setPosition(Point p) {
		x = p.x;
		y = p.y;
	}

	protected abstract double getFriction();

	protected boolean collidesWithWalls() {
		return true;
	}
}
