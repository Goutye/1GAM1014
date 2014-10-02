package ogam1014.screen;

import java.awt.Graphics;

import ogam1014.Engine;
import ogam1014.InputHandler;

public class Menu implements IScreen {

	private Engine engine;
	private InputHandler input;

	public Menu(Engine engine, InputHandler input) {
		this.engine = engine;
		this.input = input;
	}

	@Override
	public void update(double dt) {
		if (input.validate.down)
			engine.setScreen(new Game(engine, input));
	}

	@Override
	public void draw(Graphics g) {
		g.drawString(Engine.NAME, Engine.WIDTH / 2, (int) (Engine.HEIGHT * 0.2));
	}

}
