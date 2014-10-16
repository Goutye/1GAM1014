package ogam1014;
 
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ogam1014.collide.Box;
import ogam1014.collide.Collide;
import ogam1014.entity.Entity;
import ogam1014.entity.Player;
import ogam1014.graphics.Renderer;
import ogam1014.graphics.Tileset;

public class Map implements Serializable{
	
	private static final long serialVersionUID = 1L;
	transient private Tileset tileset;
	private Tile map[][];
	
	public Map(String fileName) {
		tileset = new Tileset();
		load(fileName);
	}
	
	public Map(Tile map[][]) {
		this.map = map;
		tileset = new Tileset();
	}
	
	public Tile[][] getMap() {
		return map;
	}
	
	public void draw(Renderer r) {
		int i, j;
		
		for(i = 0; i < map.length; ++i)
			for(j = 0; j < map[i].length; ++j) {
				tileset.draw(r, map[i][j], i * Tile.SIZE, j * Tile.SIZE);
			}
	}
	
	public void draw(Renderer r, int dx, int dy, int nbTileX, int nbTileY, int idTileStartX, int idTileStartY) {
		int i, j;
		
		for(i = idTileStartX; i < Math.min(map.length, idTileStartX + nbTileX); ++i)
			for(j = idTileStartY; j < Math.min(map[i].length, idTileStartY + nbTileY); ++j) {
				tileset.draw(r, map[i][j], dx + (i - idTileStartX) * Tile.SIZE, dy + (j - idTileStartY) * Tile.SIZE);
			}
	}
	
	public void save(String fileName) {
		try {
			FileOutputStream fout = new FileOutputStream(new File("assets/maps/" + fileName));
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(map);
			oos.close();
			System.out.println("Map saved!");
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void load(String fileName) {
		try {
			FileInputStream fint = new FileInputStream("assets/maps/" + fileName);
			ObjectInputStream ois = new ObjectInputStream(fint);
			map = (Tile[][]) ois.readObject();
			ois.close();
			System.out.println("Map loaded!");
		} catch(IOException e) {
			e.printStackTrace();
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Tile[][] getTab(){
		return map;
	}
	
	public void putTile(Tile tile, int x, int y) {
		map[x][y] = tile;
	}
	
	public Tile getTile(int x, int y) {
		if (x < 0 || x >= getWidth())
			return Tile.VOID;
		
		if (y < 0 || y >= getHeight())
			return Tile.VOID;
		
		return map[x][y];
	}
	
	public int getWidth() {
		return map.length;
	}
	
	public int getHeight() {
		return map[0].length;
	}

	public List<Point> getAvailablePositions(Player player) {
		List<Point> positions = new ArrayList<Point>();
		
		int skippedCaseW = Engine.WIDTH/2 / Tile.SIZE;
		int skippedCaseH = Engine.HEIGHT/2 / Tile.SIZE;
		Point p = new Point(0,0);
		Point posPlayer = new Point( (int) player.getX()/Tile.SIZE, (int) player.getY()/Tile.SIZE);
		Box box = new Box(posPlayer.x - skippedCaseW, posPlayer.y - skippedCaseH, skippedCaseW * 2, skippedCaseH * 2);
		
		
		for (int i = 0; i < map.length; ++i) {
			for (int j = 0; j < map[0].length; ++j) {
				p.x = i; p.y = j;
				
				if ( ! map[i][j].isWall() && ! Collide.AABB_point(box, p))
					positions.add(new Point(i * Tile.SIZE, j * Tile.SIZE));
			}
		}
		
		
		return positions;
	}
	
	
	public Point findPosTile(Entity e) throws NullPointerException{
		Point p = new Point((int) (e.getX() / Tile.SIZE), (int) (e.getY() / Tile.SIZE));
		return p;
	}
	
	public Tile findTile(Entity e) throws NullPointerException{
		System.out.println((int) (e.getX() / Tile.SIZE) + ", " + (int) (e.getY() / Tile.SIZE));
		return map[(int) (e.getX() / Tile.SIZE)][(int) (e.getY() / Tile.SIZE)];
	}
	
}
