package ogam1014.entity;

import ogam1014.collide.Box;
import ogam1014.graphics.Renderer;

public class Swing extends Entity {
	private static final long serialVersionUID = 1L;

	private MobEntity owner;
	private double duration;
	
	public Swing(MobEntity owner, double duration) {
		this.owner = owner;
		this.duration = duration;
		owner.lockMovement();
		
		x = owner.getX() + owner.getWidth()/2;
		y = owner.getY() + owner.getHeight()/2;
		w = 4;
		h = 4;
		this.box = new Box((int) x, (int) (y), w, h);
	}

	@Override
	public void update(double dt) {
		super.update(dt);
		if (time >= duration) {
			remove();
		}
	}

	private void remove() {
		level.removeEntity(this);
		owner.unlockMovement();
	}

	@Override
	public void draw(Renderer r) {
		r.blit(IMAGE, x - 2, y - 2, 8, 0, 16, 44);
	}

}
