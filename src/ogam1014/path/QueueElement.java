package ogam1014.path;

import java.awt.Point;

import com.sun.javafx.collections.SortableList;

public class QueueElement implements Comparable {
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

	@Override
	public int compareTo(Object o) {
		if(o instanceof QueueElement){
			QueueElement qe = (QueueElement) o;
			
			
		}
		return 0;
	}
}
