package ogam1014;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import ogam1014.entity.IEntity;

public class Level {
	private List<IEntity> entities = new ArrayList<IEntity>();

	public void addEntity(IEntity entity) {
		entities.add(entity);
	}

	public void update(double dt) {
		for (IEntity e : entities) {
			e.update(dt);
		}
	}
	
	public void draw(Graphics g) {
		for (IEntity e : entities) {
			e.draw(g);
		}
	}
}
