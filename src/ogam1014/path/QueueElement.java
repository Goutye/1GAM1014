package ogam1014.path;

import java.awt.Point;

public class QueueElement{
	private Point tile;
	private int precedente;
	private Point end;
 
	public QueueElement(Point tile, Point end) {
		this.tile = tile;
		this.precedente = 0;
		this.end = end;
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
	
	public Point getEnd() {
		return end;
	}
}
