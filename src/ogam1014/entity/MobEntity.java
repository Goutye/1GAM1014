package ogam1014.entity;

import java.awt.Point;
import java.util.ArrayList;

import ogam1014.collide.Box;
import ogam1014.collide.Collide;
import ogam1014.path.PathFinder;

public abstract class MobEntity extends Entity {

	private static final long serialVersionUID = 1128212645153539541L;
	static protected double PERSPECTIVE = 0.5; // 0.5 => 50% du haut du sprite ignor� dans les collisions.

	static protected double SPEED = 200;

	protected double hIgnored;
	protected double dx;
	protected double dy;
	protected double friction = 1;
	protected int dir_x = 0;
	protected int dir_y = 0;

	protected Boolean speaking = false;
	protected Boolean speakingToSomeone = false;
	
	protected int aggro = 300;


	@Override
	public void update(double dt) {
		super.update(dt);
		dx = dx * friction;
		dy = dy * friction;

		testWallCollision(dt);
		testEntityCollision(dt);

		x += dx * dt;
		y += dy * dt;
		this.box.x = (int) x;
		this.box.y = (int) (y + hIgnored);
	}

	public Point moveTowardPlayer() {
		Point p;

		Player player = level.getPlayer();
		Point pMob = new Point((int) this.getX(), (int) this.getY());
		Point pPlayer = new Point((int) player.getX(), (int) player.getY());

		if (pMob.distance(pPlayer) < aggro) {
			PathFinder pathFind = new PathFinder(player, this, level.getMap());

			ArrayList<Point> path = pathFind.A_star();
			if (path.size() > 1) {
				int x = path.get(1).x - path.get(0).x;
				int y = path.get(1).y - path.get(0).y;
				p = new Point(x, y);
			} else
				p = null;
		} else
			p = null;

		return p;

	}

	private void testWallCollision(double dt) {
		double xx = box.x + dx * dt;
		double yy = box.y + dy * dt;
		double x = box.x;
		double y = box.y;
		int w = getWidth() - 1;
		int h = getHeight() - (int) hIgnored;

		boolean collideX = false;
		boolean collideY = false;

		if (!collideY && level.getTile(x, dy < 0 ? yy : yy + h).isWall())
			collideY = true;
		if (!collideY && level.getTile(x + w, dy < 0 ? yy : yy + h).isWall())
			collideY = true;

		if (!collideX && level.getTile(dx < 0 ? xx : xx + w, y).isWall())
			collideX = true;
		if (!collideX && level.getTile(dx < 0 ? xx : xx + w, y + h).isWall())
			collideX = true;

		if (!collideX
				&& !collideY
				&& level.getTile(dx < 0 ? xx : xx + w, dy < 0 ? yy : yy + h)
						.isWall())
			collideX = collideY = true;
		if (!collideX
				&& !collideY
				&& level.getTile(dx < 0 ? xx : xx + w, dy >= 0 ? yy : yy + h)
						.isWall())
			collideX = collideY = true;
		if (!collideX
				&& !collideY
				&& level.getTile(dx >= 0 ? xx : xx + w, dy < 0 ? yy : yy + h)
						.isWall())
			collideX = collideY = true;

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

			boolean collide = Collide.aabb(xx, yy + hIgnored, getWidth(),
					(int) (getHeight() - hIgnored), e.getBox().x, e.getBox().y,
					e.getBox().width, e.getBox().height);
			;
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
		y = p.y - hIgnored;
		this.box = new Box((int) x, (int) (y), w, (int) (h - hIgnored));
	}

	protected boolean collidesWithWalls() {
		return true;
	}
	
	public void stopSpeaking() {
		speaking = false;
		speakingToSomeone = false;
	}
}
