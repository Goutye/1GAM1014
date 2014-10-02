package ogam1014.entity;

import java.awt.Color;
import java.awt.Graphics;

import ogam1014.InputHandler;

public class Player implements IEntity {
	static public double SPEED = 5000;

	private InputHandler input;
	private double x = 0;
	private double y = 0;
	private double dx = 0;
	private double dy = 0;

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
		dx = dx * 0.98 * dt;
		dy = dy * 0.98 * dt;
		x += dx * dt;
		y += dy * dt;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.red);
		g.fillRect((int)Math.round(x), (int)Math.round(y), 10, 10);
	}

}
