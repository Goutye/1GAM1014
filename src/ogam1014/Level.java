package ogam1014;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ogam1014.entity.Enemy;
import ogam1014.entity.Entity;
import ogam1014.entity.Player;
import ogam1014.graphics.Renderer;

public class Level {
	static private Random random = new Random();
	
	
	private List<Entity> entities = new ArrayList<Entity>();
	private List<Entity> newEntities = new ArrayList<Entity>();
	private List<Entity> removedEntities = new ArrayList<Entity>();
	private Map map;
	private String name;
	private Player player;
	private double currentTimeMobSpawn = -1.;
	private double timeBeforeMobSpawn;
	private double percent_mobByAvailablePositions;

	public Level(String name) {
		map = new Map(name + ".tile");
		this.name = name;
		
		switch(name){
		case "map":
			timeBeforeMobSpawn = 10.;
			percent_mobByAvailablePositions = 0.15;
			break;
		default:
			timeBeforeMobSpawn = 20.;
			percent_mobByAvailablePositions = 0.000;
		}
	}

	public void addEntity(Entity entity) {
		newEntities.add(entity);
		entity.setLevel(this);
		
		if(entity instanceof Player) {
			player = (Player) entity;
		}
	}

	public void removeEntity(Entity e) {
		removedEntities.add(e);
	}
	
	public void update(double dt) {
		addRandomEnemies(dt);
		
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

	public int getWidth() {
		return map.getWidth() * Tile.SIZE;
	}

	public int getHeight() {
		return map.getHeight() * Tile.SIZE;
	}

	public List<Entity> getEntityNear(double x, double y, int width, int height) {
		return entities;
	}
	
	private void addRandomEnemies(double dt) {
		if (currentTimeMobSpawn < 0) {
			//Level just loaded
			List<Point> positions = map.getAvailablePositions(player);
			if (positions.size() == 0) {
				System.out.println("No positions found via 0");
				return;
			}
			int nbMob = (int)  (positions.size() * percent_mobByAvailablePositions);
			
			for (int i = 0; i < nbMob; ++i) {
				Point p = positions.get( random.nextInt(positions.size()) );
				Enemy e = new Enemy();
				
				e.setPosition(p);
				positions.remove(p);
				addEntity(e);
			}
			
			currentTimeMobSpawn = 0.;
		}
		else {
			currentTimeMobSpawn += dt;
			
			if (currentTimeMobSpawn >= timeBeforeMobSpawn) {
				List<Point> positions = map.getAvailablePositions(player);
				if (positions.size() == 0) {
					System.out.println("No positions found");
					return;
				}
				
				Point p = positions.get( random.nextInt(positions.size()) );
				Enemy e = new Enemy();
				
				e.setPosition(p);
				addEntity(e);
				currentTimeMobSpawn -= timeBeforeMobSpawn;
			}
		}
	}
}

