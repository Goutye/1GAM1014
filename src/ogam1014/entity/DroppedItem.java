package ogam1014.entity;

import ogam1014.collide.Box;
import ogam1014.equipment.Item;
import ogam1014.graphics.Renderer;

public class DroppedItem extends Entity {
	private static final long serialVersionUID = 1L;

	private Item item;

	public DroppedItem(Item item, double x, double y) {
		this.item = item;
		this.h = 8;
		this.w = 8;
		this.x = x;
		this.y = y;
		this.box = new Box((int) x, (int) y, w, h);
	}

	@Override
	public void update(double dt) {
	}

	@Override
	public void draw(Renderer r) {
		r.blit(IMAGE, x - 2, y - 2, 8, 8, 8, 44); /* TODO */
	}

	public Item popItem() {
		level.removeEntity(this);
		return item;
	}

}
