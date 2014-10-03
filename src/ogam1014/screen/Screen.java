package ogam1014.screen;

import java.awt.Graphics;

import ogam1014.Engine;
import ogam1014.InputHandler;

public abstract class Screen {

	protected InputHandler input;
	protected Engine engine;

	abstract public void update(double dt);
	abstract public void draw(Graphics g);
	
	public void init(Engine engine, InputHandler input) {
		this.engine = engine;
		this.input = input;
	}
}
