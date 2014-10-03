package ogam1014.entity;

import java.awt.Color;
import java.awt.Graphics;

import ogam1014.InputHandler;

public class Player extends MobEntity {
	static public double SPEED = 5000;

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
		if (input.fire.down) {
			Bullet b = new Bullet(this);
			level.addEntity(b);
		}

		super.update(dt);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.red);
		g.fillRect((int)Math.round(x), (int)Math.round(y), 10, 10);
	}

}
