package ogam1014;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import ogam1014.entity.Enemy;
import ogam1014.entity.EnemyType;
import ogam1014.entity.Entity;
import ogam1014.entity.Player;
import ogam1014.entity.Thief;
import ogam1014.graphics.Renderer;

public class Level implements Serializable{
	private static final long serialVersionUID = 1L;
	static private Random random = new Random();
	
	private List<Entity> entities = new ArrayList<Entity>();
	transient private List<Entity> newEntities = new ArrayList<Entity>();
	transient private List<Entity> removedEntities = new ArrayList<Entity>();
	
	private List<Entity> passivEntities = new ArrayList<Entity>();
	transient private List<Entity> passivNewEntities = new ArrayList<Entity>();
	transient private List<Entity> passivRemovedEntities = new ArrayList<Entity>();
	
	private double ratioEnemyType[] = {0.5, 0.5};
	private Map map;
	private String name;
	private Player player;
	private double currentTimeMobSpawn = -1.;
	private double timeBeforeMobSpawn;
	 private double percent_mobByAvailablePositions;

	public Level(String name) {
		double checkRatioEnemyType = 0;
		
		for(double e : ratioEnemyType) 
			checkRatioEnemyType += e;
		
		if( checkRatioEnemyType != 1.0) {
			System.out.println("La somme des ratios des types d'ennemies du level " + name + " est différente de 1. (" + checkRatioEnemyType + ")");
			System.exit(1);
		}
		
		map = new Map(name + ".tile");
		this.name = name;
		
		switch(name){
		case "map":
			timeBeforeMobSpawn = 10.;
			percent_mobByAvailablePositions = 0.01;
			break;
		default:
			timeBeforeMobSpawn = 20.;
			percent_mobByAvailablePositions = 0.000;
		}
	}

	public void addEntity(Entity entity) {
		if (entity.isPassiv())
			passivNewEntities.add(entity);
		else
			newEntities.add(entity);
		
		entity.setLevel(this);
		
		if(entity instanceof Player) {
			player = (Player) entity;
		}
	}

	public void removeEntity(Entity e) {
		if (e.isPassiv())
			passivRemovedEntities.add(e);
		else
			removedEntities.add(e);
	}
	
	public void update(double dt) {
		addRandomEnemies(dt);
		
		entities.addAll(newEntities);
		passivEntities.addAll(passivNewEntities);
		newEntities.clear();
		passivNewEntities.clear();

		for (Entity e : entities) {
			e.update(dt);
		}
		for (Entity e : passivEntities) {
			e.update(dt);
		}

		entities.removeAll(removedEntities);
		passivEntities.removeAll(passivRemovedEntities);
		removedEntities.clear();
		passivRemovedEntities.clear();
		
		Collections.sort(entities, new Comparator<Entity>() {
			@Override
			public int compare(Entity o1, Entity o2) {
				int y1 = (int) (o1.getY() + o1.getHeight());
				int y2 = (int) (o2.getY() + o2.getHeight());
				return y1 - y2;
			}
		});
	}

	public void draw(Renderer r) {
		map.draw(r);

		for (Entity e : entities) {
			e.draw(r);
		}
		for (Entity e : passivEntities) {
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
		warps.add(new Warp(new Point(Tile.SIZE*4, Tile.SIZE*1), 1, 2, "map"));
		warps.add(new Warp(new Point(Tile.SIZE*1, Tile.SIZE*4), 2, 1, "map"));
		
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
	
	private Enemy newEnemy() {
		double rand = random.nextDouble();
		
		if (rand <= ratioEnemyType[0])
			return (Enemy) (new Enemy());
		
		rand -= ratioEnemyType[0];
		
		if (rand <= ratioEnemyType[1])
			return (Enemy) (new Thief());
		
		return new Enemy();
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
				Enemy e = newEnemy();
				
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
	
	public void setLoad(Level l,InputHandler input){
		map=l.map;
		entities = l.entities;
		player=l.player;
		player.setInput(input);
	}
	
	public Map getMap(){
		return map;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public List<Entity> getEntities() {
		return entities;
	}
	
	
}

