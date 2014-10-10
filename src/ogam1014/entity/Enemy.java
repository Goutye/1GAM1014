package ogam1014.entity;

import ogam1014.graphics.Renderer;

public class Enemy extends MobEntity {

	@Override
	public void update(double dt) {
		super.update(dt);
	}
	
	@Override
	public void draw(Renderer r) {
		r.blit(IMAGE, x, y, 32, 32, 0, 0);
	}

	@Override
	public int getWidth() {
		return 32;
	}

	@Override
	public int getHeight() {
		return 32;
	}
	
	@Override
	protected double getFriction() {
		return .9;
	}

}
