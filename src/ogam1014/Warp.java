package ogam1014;

import java.awt.Point;

import ogam1014.collide.Box;

public class Warp {
	private Box box;
	private int id;
	private int idNextLevel;
	private String nameNextLevel;
	
	public Warp(Point p, int id, int idNextLevel, String nameNextLevel) {
		this.id = id;
		this.idNextLevel = idNextLevel;
		this.nameNextLevel = nameNextLevel;
		this.box = new Box(p.x, p.y, Tile.SIZE, Tile.SIZE);
	}
	
	public Box getBox() {
		return box;
	}
	
	public int getId() {
		return id;
	}
	
	public int getIdNextLevel() {
		return idNextLevel;
	}
	
	public String getNameNextLevel() {
		return nameNextLevel;
	}
}
