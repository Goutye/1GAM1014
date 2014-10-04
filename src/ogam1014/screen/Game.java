package ogam1014.screen;

import ogam1014.Engine;
import ogam1014.InputHandler;
import ogam1014.Level;
import ogam1014.collide.Collide;
import ogam1014.entity.Player;
import ogam1014.graphics.Renderer;

public class Game extends Screen {

	private Level level = new Level();
	private Player player;
	
	@Override
	public void init(Engine engine, InputHandler input, Collide collide) {
		super.init(engine, input, collide);
		player = new Player(input);
		level.addEntity(player);
	}

	@Override
	public void update(double dt) {
		level.update(dt);
	}

	@Override
	public void draw(Renderer r) {
		level.draw(r);
	}

}
