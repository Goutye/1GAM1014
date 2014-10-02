package ogam1014.screen;

import java.awt.Color;
import java.awt.Graphics;

import ogam1014.Engine;

public class Menu extends Screen {

	@Override
	public void update(double dt) {
		if (input.validate.down) {
			engine.setScreen(new Game());
		}
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawString(Engine.NAME, (int)Engine.WIDTH / 2, (int) (Engine.HEIGHT * 0.2));
	}

}