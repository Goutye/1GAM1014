package ogam1014;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import ogam1014.collide.Box;
import ogam1014.collide.Collide;
import ogam1014.entity.Entity;
import ogam1014.graphics.Renderer;

public class Level {
	private List<Entity> entities = new ArrayList<Entity>();
	private List<Entity> newEntities = new ArrayList<Entity>();
	private List<Entity> removedEntities = new ArrayList<Entity>();
	private Map map;
	private String name;

	public Level(String name) {
		map = new Map(name + ".tile");
		this.name = name;
	}

	public void addEntity(Entity entity) {
		newEntities.add(entity);
		entity.setLevel(this);
	}

	public void removeEntity(Entity e) {
		removedEntities.add(e);
	}
	
	public void update(double dt) {
		entities.addAll(newEntities);
		entities.removeAll(removedEntities);
		newEntities.clear();
		removedEntities.clear();

		for (Entity e : entities) {
			e.update(dt);
		}
	}

	public void draw(Renderer r) {
		map.draw(r);

		for (Entity e : entities) {
			e.draw(r);
		}
	}
	
	public Tile getTile(double x, double y){
		int xx = (int) Math.floor(x / Tile.SIZE);
		int yy = (int) Math.floor(y / Tile.SIZE);
		return map.getTile(xx, yy);
	}
	
	public List<Warp> getWarps() {
		List<Warp> warps =  new ArrayList<Warp>();
		warps.add(new Warp(new Point(128, 32), 1, 2, "map"));
		warps.add(new Warp(new Point(32, 128), 2, 1, "map"));
		
		return warps;
	}
}

