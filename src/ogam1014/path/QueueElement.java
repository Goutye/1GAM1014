package ogam1014.path;

import java.awt.Point;

import ogam1014.Tile;

public class QueueElement {
	private Point tile;
	private int precedente;
 
	public QueueElement(Point tile) {
		this.tile = tile;
		this.precedente = 0;
	}
	
	public QueueElement(Point tile, int precedente) {
		this.tile = tile;
		this.precedente = precedente;
	}
	
	public Point getTile() {
		return tile;
	}
	public int getPrecedente() {
		return precedente;
	}
}
