package ogam1014.entity;

import ogam1014.InputHandler;
import ogam1014.graphics.Renderer;

public class Player extends LivingEntity {
	static public double SPEED = 200;

	private InputHandler input;
	private int w;
	private int h;
	private int hCollision;
	
	
	public Player(InputHandler input) {
		this.input = input;
		this.w = 22;
		this.h = 43;
		this.hCollision = 32;
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
		r.blit(IMAGE, x, y + (hCollision - h), w, h, 2, 0);
	}

	@Override
	protected double getFriction() {
		return 0.9;
	}

	@Override
	public int getWidth() {
		return w;
	}

	@Override
	public int getHeight() {
		return hCollision;
	}

}
