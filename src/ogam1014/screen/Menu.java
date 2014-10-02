package ogam1014.screen;

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
		g.drawString(Engine.NAME, Engine.WIDTH / 2, (int) (Engine.HEIGHT * 0.2));
	}

}
