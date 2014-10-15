package ogam1014.screen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import ogam1014.Engine;
import ogam1014.InputHandler;
import ogam1014.Level;
import ogam1014.Warp;
import ogam1014.collide.Box;
import ogam1014.collide.Collide;
import ogam1014.entity.Player;
import ogam1014.graphics.Camera;
import ogam1014.graphics.Renderer;

public class Game extends Screen {

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

		for (Warp w : warps) {
			if (idWarp == w.getId()) {
				player.setPosition(w.getBox().getLocation());
				break;
			}
		}

		level.addEntity(player);
	}

	private void changeLevel() {
		Box box = new Box((int) player.getX(), (int) player.getY(),
				player.getWidth(), player.getHeight());

		for (Warp w : warps) {
			if (Collide.AABB_AABB(box, w.getBox()) && input.validate.pressed) {
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

	public void save() {
		
		File save = new File("assets/save");
		if (!save.exists()) {
			try {
				save.mkdir();
			} catch (SecurityException e) {
				e.printStackTrace();

			}
		}
		try {
			FileOutputStream fout = new FileOutputStream(new File(
					"assets/save/" + "save1"));
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(level);
			oos.close();
			System.out.println("Level saved!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
