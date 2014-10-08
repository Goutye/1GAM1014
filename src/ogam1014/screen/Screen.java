package ogam1014.screen;

import ogam1014.Engine;
import ogam1014.InputHandler;
import ogam1014.graphics.Renderer;

public abstract class Screen {

	protected InputHandler input;
	protected Engine engine;

	abstract public void update(double dt);
	abstract public void draw(Renderer r);
	
	public void init(Engine engine, InputHandler input) {
		this.engine = engine;
		this.input = input;
	}
}
