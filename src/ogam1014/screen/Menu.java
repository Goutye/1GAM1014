package ogam1014.screen;

import java.awt.Color;
import java.awt.Graphics;

import ogam1014.Engine;

public class Menu extends Screen {
	
	private Button start = new Button((int)Engine.WIDTH / 2, (int) (Engine.HEIGHT * 0.2),100,50,"Start",Color.BLACK,Color.YELLOW);
	private Button options = new Button((int)Engine.WIDTH / 2, (int) (Engine.HEIGHT * 0.4),100,50,"Options",Color.BLACK,Color.YELLOW);
	private Button quit = new Button((int)Engine.WIDTH / 2, (int) (Engine.HEIGHT * 0.6),100,50,"Quit",Color.BLACK,Color.YELLOW);
	@Override
	public void update(double dt) {
		if (input.validate.down) {
			engine.setScreen(new Game());
		}
	}

	@Override
	public void draw(Graphics g) {
		start.drawSelectedRectangle(g);
		quit.drawRectangle(g);
		options.drawRectangle(g);
		//g.drawString(Engine.NAME, (int)Engine.WIDTH / 2, (int) (Engine.HEIGHT * 0.2));
	}

}
