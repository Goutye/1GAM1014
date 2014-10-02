package ogam1014.screen;

import java.awt.Graphics;

import ogam1014.Engine;
import ogam1014.InputHandler;
import ogam1014.Level;
import ogam1014.entity.Player;

public class Game extends Screen {

	private Level level = new Level();
	private Player player;
	
	@Override
	public void init(Engine engine, InputHandler input) {
		super.init(engine, input);
		player = new Player(input);
		level.addEntity(player);
	}

	@Override
	public void update(double dt) {
		level.update(dt);
	}

	@Override
	public void draw(Graphics g) {
		level.draw(g);
	}

}
