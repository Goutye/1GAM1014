package ogam1014.entity;

import ogam1014.equipment.ItemFactory;

public class Thief extends Enemy {
	private static final long serialVersionUID = 1L;

	private int direction = 1;

	public Thief() {
		this.w = 15;
		this.h = 31;
		time = random.nextDouble() * 2;
	}

	public void update(double dt) {
		super.update(dt);

		if (Math.floor(time) % 2 == 0)
			direction = -1;
		else
			direction = 1;

		dx = direction * SPEED / 3;
	}

	@Override
	protected void onDeath() {
		super.onDeath();
		if (random.nextFloat() < 0.8) {
			DroppedItem drop = new DroppedItem(ItemFactory.make("ammopack.smallx20"), x + w / 2, y + h / 2);
			level.addEntity(drop);
		}
	}

}
