package ogam1014.entity;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import ogam1014.collide.Box;
import ogam1014.collide.Collide;
import ogam1014.graphics.Renderer;

public class Swing extends Entity {
	private static final long serialVersionUID = 1L;

	private MobEntity owner;
	private double duration;
	private List<LivingEntity> hit = new ArrayList<>();
	private double repulsePower = 200;
	private double angle;
	private double damage;

	public Swing(MobEntity owner, int size, double angle) {
		this.owner = owner;
		this.angle = angle;
		owner.lockMovement();

		w = size;
		h = size;
		x = owner.getX() + owner.getWidth() / 2 - w / 2 + Math.cos(angle) * size / 2;
		y = owner.getY() + owner.getHeight() / 2 - h / 2 + Math.sin(angle) * size / 2;
		this.box = new Box((int) x, (int) y, w, h);
	}

	@Override
	public void update(double dt) {
		super.update(dt);

		for (Entity e : level.getEntityNear(y, x, getWidth(), getHeight())) {
			if (e == this || e == owner || !(e instanceof LivingEntity))
				continue;

			boolean collide = Collide.aabb(x, y, getWidth(),
					(int) getHeight(), e.getBox().x, e.getBox().y,
					e.getBox().width, e.getBox().height);

			if (collide) {
				hit((LivingEntity) e);
			}
		}

		for (LivingEntity e : hit) {
			e.dx += Math.cos(angle) * repulsePower;
			e.dy += Math.sin(angle) * repulsePower;
		}
		
		if (time >= duration) {
			remove();
		}
	}

	private void hit(LivingEntity e) {
		if (hit.contains(e))
			return;

		e.takeDamage(damage);
		hit.add(e);
	}

	private void remove() {
		level.removeEntity(this);
		owner.unlockMovement();
	}

	@Override
	public void draw(Renderer r) {
		r.setColor(new Color(0, 0, 0, 0.2f));
		r.getGraphics().fillRect((int) x, (int) y, w, h);
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}
	
	public void setRepulsePower(double repulsePower) {
		this.repulsePower = repulsePower;
	}
	
	public void setDamage(double damage) {
		this.damage = damage;
	}

}
