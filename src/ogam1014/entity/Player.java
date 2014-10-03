package ogam1014.entity;

import java.awt.Color;
import java.awt.Graphics;

import ogam1014.InputHandler;

public class Player extends MobEntity {
	static public double SPEED = 100;

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
			Bullet b = new Bullet(this, fireDx * Bullet.SPEED,
					fireDy * Bullet.SPEED);
			level.addEntity(b);
		}

		super.update(dt);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.red);
		g.fillRect((int) Math.round(x), (int) Math.round(y), (int) getWidth(), (int) getHeight());
	}

	@Override
	protected double getFriction() {
		return 0.9;
	}

	@Override
	public double getWidth() {
		return 32;
	}

	@Override
	public double getHeight() {
		return 32;
	}

}
