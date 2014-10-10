package ogam1014.entity;

import ogam1014.InputHandler;
import ogam1014.graphics.Renderer;

public class Player extends MobEntity {
	static public double SPEED = 200;

	private InputHandler input;

	public Player(InputHandler input) {
		this.input = input;
	}

	@Override
	public void update(double dt) {
		if (input.up.down) {
			dy = -SPEED;
		}

		if (input.down.down) {
			dy = SPEED;
		}

		if (input.right.down) {
			dx = SPEED;
		}

		if (input.left.down) {
			dx = -SPEED;
		}

		boolean fire = false;
		double fireDx = 0;
		double fireDy = 0;

		if (input.fireUp.down) {
			fire = true;
			fireDy = -1;
		}

		if (input.fireDown.down) {
			fire = true;
			fireDy = 1;
		}

		if (input.fireLeft.down) {
			fire = true;
			fireDx = -1;
		}

		if (input.fireRight.down) {
			fire = true;
			fireDx = 1;
		}

		if (fire) {
			Bullet b = new Bullet(this, fireDx * Bullet.SPEED, fireDy * Bullet.SPEED);
			level.addEntity(b);
		}

		super.update(dt);
	}

	@Override
	public void draw(Renderer r) {
		r.blit(IMAGE, x, y, 32, 32, 0, 0);
	}

	@Override
	protected double getFriction() {
		return 0.9;
	}

	@Override
	public int getWidth() {
		return 32;
	}

	@Override
	public int getHeight() {
		return 32;
	}

}
