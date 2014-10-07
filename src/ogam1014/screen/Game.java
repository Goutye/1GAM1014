package ogam1014.screen;

import ogam1014.Engine;
import ogam1014.InputHandler;
import ogam1014.Level;
import ogam1014.collide.Collide;
import ogam1014.entity.Player;
import ogam1014.graphics.Camera;
import ogam1014.graphics.Renderer;

public class Game extends Screen {

	private Level level = new Level();
	private Camera camera = new Camera();
	private Player player;

	public void init(Engine engine, InputHandler input) {
		super.init(engine, input);
		if (player == null) {
			player = new Player(input);
			level.addEntity(player);
		}
	}

	@Override
	public void update(double dt) {
		if (input.pause.pressed) {
			engine.setScreen(new Pause(this));
			return;
		}
		level.update(dt);
		camera.update(dt);
	}

	@Override
	public void draw(Renderer r) {
		camera.centerOn(player);
		r.useCamera(camera);
		level.draw(r);
		r.useCamera(null);
	}

}
