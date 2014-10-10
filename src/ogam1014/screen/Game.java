package ogam1014.screen;

import java.awt.Point;
import java.util.List;
import java.util.Random;

import ogam1014.Engine;
import ogam1014.InputHandler;
import ogam1014.Level;
import ogam1014.Warp;
import ogam1014.collide.Box;
import ogam1014.collide.Collide;
import ogam1014.entity.Enemy;
import ogam1014.entity.Player;
import ogam1014.graphics.Camera;
import ogam1014.graphics.Renderer;

public class Game extends Screen {

	static private Random random = new Random();
	private Level level;
	private List<Warp> warps;
	private Camera camera = new Camera();
	private Player player;

	public void init(Engine engine, InputHandler input) {
		super.init(engine, input);
		if (player == null) {
			player = new Player(input);
			changeLevel("map", 1);
		}
	}

	public void changeLevel(String name, int idWarp) {
		level = new Level(name);
		warps = level.getWarps();
		
		for( Warp w : warps) {
			if (idWarp == w.getId()) {
				player.setPosition(w.getBox().getLocation());
				break;
			}
		}
		
		level.addEntity(player);
		
		// add some enemies
		for (int i = 0; i < 50; i++) {
			Enemy e = new Enemy();
			int x = random.nextInt(level.getWidth());
			int y = random.nextInt(level.getHeight());
			e.setPosition(new Point(x, y));
			level.addEntity(e);
		}
	}
	
	private void changeLevel() {
		Box box = new Box((int) player.getX(), (int) player.getY(), player.getWidth(), player.getHeight());
		
		for(Warp w : warps) {
			if(Collide.AABB_AABB(box, w.getBox()) && input.validate.pressed){
				changeLevel(w.getNameNextLevel(), w.getIdNextLevel());
			}
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
		changeLevel();
	}

	@Override
	public void draw(Renderer r) {
		camera.centerOn(player);
		r.useCamera(camera);
		level.draw(r);
		r.useCamera(null);
	}
}
