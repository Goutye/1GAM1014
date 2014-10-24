package ogam1014.entity;

import java.awt.Point;

import ogam1014.equipment.ItemFactory;

public class Thief extends Enemy {
	private static final long serialVersionUID = 8877449123438883245L;
	private int prevDirection = 1;

	public Thief() {
		this.w = 15;
		this.h = 31;
		time = random.nextDouble() * 2;
	}

	public void update(double dt) {
		super.update(dt);

		if (!speakingToSomeone) {

			Point p = moveTowardPlayer();

			if (p == null) {
				if (Math.floor(time) % 2 == 0)
					dir_x = -1;
				else
					dir_x = 1;

				if (Math.floor(time) % 2 == 0)
					dir_y = -1;
				else
					dir_y = 1;
			}

			else {
				dir_x = p.x;
				dir_y = p.y;
			}
			dy = dir_y * SPEED / 3;
			dx = dir_x * SPEED / 3;

			if (dir_x != prevDirection && random.nextInt(10) == 0) {
				tryToSpeak();
			}

			prevDirection = dir_x;
		} else {
			dx = 0;
			dy = 0;
		}
	}

	@Override
	protected void onDeath() {
		super.onDeath();
		if (random.nextFloat() < 0.8) {
			DroppedItem drop = new DroppedItem(
					ItemFactory.make("ammopack.smallx20"), x + w / 2, y + h / 2);
			level.addEntity(drop);
		}

		if (speaking) {
			level.removeEntity(dialogBox);
		}
	}
}
