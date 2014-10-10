package ogam1014.entity;

import ogam1014.graphics.Renderer;

public class Enemy extends LivingEntity {

	@Override
	public void update(double dt) {
		super.update(dt);
	}
	
	@Override
	public void draw(Renderer r) {
		r.blit(IMAGE, x, y, 22, 43, 2, 0);
	}

	@Override
	public int getWidth() {
		return 22;
	}

	@Override
	public int getHeight() {
		return 43;
	}
	
	@Override
	protected double getFriction() {
		return .9;
	}

}
