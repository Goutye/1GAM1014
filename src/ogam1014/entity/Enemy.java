package ogam1014.entity;

import ogam1014.Tile;
import ogam1014.graphics.Renderer;

public class Enemy extends LivingEntity {

	public Enemy() {
		this.h = 43;
		this.w = 22;
		this.hIgnored = Math.max( this.h * PERSPECTIVE, this.w - Tile.SIZE);
	}
	
	@Override
	public void update(double dt) {
		super.update(dt);
	}
	
	@Override
	public void draw(Renderer r) {
		r.blit(IMAGE, x, y, 22, 43, 2, 0);
	}
	
	@Override
	protected double getFriction() {
		return .9;
	}

}
