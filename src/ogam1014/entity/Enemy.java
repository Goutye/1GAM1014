package ogam1014.entity;

import ogam1014.Tile;
import ogam1014.graphics.Renderer;

public class Enemy extends LivingEntity {
	private static final long serialVersionUID = -1125801212253913516L;

	public Enemy() {
		this.h = 31;
		this.w = 15;
		this.hIgnored = Math.max( this.h * PERSPECTIVE, this.w - Tile.SIZE);
	}
	
	@Override
	public void update(double dt) {
		super.update(dt);
	}
	
	@Override
	public void draw(Renderer r) {
		r.blit(IMAGE, x, y, 15, 31, 0, 0);
	}
	
}
