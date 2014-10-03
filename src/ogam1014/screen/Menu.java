package ogam1014.screen;

import java.awt.Color;
import java.awt.Graphics;

import ogam1014.Engine;

public class Menu extends Screen {
	
	private Button start = new Button((int)Engine.WIDTH / 2, (int) (Engine.HEIGHT * 0.2),60,60,"Start",Color.WHITE,Color.BLACK);


	@Override
	public void update(double dt) {
		if (input.validate.down) {
			engine.setScreen(new Game());
		}
	}

	@Override
	public void draw(Graphics g) {
		start.drawRectangle(g);
		g.drawString(Engine.NAME, (int)Engine.WIDTH / 2, (int) (Engine.HEIGHT * 0.2));
	}

}
