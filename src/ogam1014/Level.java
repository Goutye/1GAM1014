package ogam1014;

import java.util.ArrayList;
import java.util.List;

import ogam1014.entity.Entity;
import ogam1014.graphics.Renderer;

public class Level {
	private List<Entity> entities = new ArrayList<Entity>();
	private List<Entity> newEntities = new ArrayList<Entity>();
	private List<Entity> removedEntities = new ArrayList<Entity>();
	private Map map;

	public Level() {
		map = new Map("Map.tile");
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
}
