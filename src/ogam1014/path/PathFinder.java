package ogam1014.path;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;

import ogam1014.Map;
import ogam1014.Tile;
import ogam1014.entity.Entity;

public class PathFinder {
	private Entity start;
	private Entity end;
	private Map map;
	private LinkedList<QueueElement> queue;
	private int index = 0;
	private boolean[][] marker;
	private Tile tiles[][];
	
	public PathFinder(Entity start, Entity end, Map map) {
		this.start = start;
		this.end = end;
		this.map = map;
		marker = new boolean[map.getWidth()][map.getHeight()];
		tiles = map.getMap();
		queue = new LinkedList<QueueElement>();

	}

	private boolean end(Point current, Point end) {
		if (current.equals(end)) {
			return true;
		}
		return false;
	}

	protected void appendQueue(Point curTileIJ,Point end) {
		Point p[] = new Point[8];
		int withOrWithoutDiag;
		p[0] = new Point(curTileIJ.x, curTileIJ.y - 1);
		p[1] = new Point(curTileIJ.x - 1, curTileIJ.y);
		p[2] = new Point(curTileIJ.x + 1, curTileIJ.y);
		p[3] = new Point(curTileIJ.x, curTileIJ.y + 1);
		
		p[4] = new Point(curTileIJ.x - 1, curTileIJ.y - 1);
		p[5] = new Point(curTileIJ.x + 1, curTileIJ.y - 1);
		p[6] = new Point(curTileIJ.x - 1, curTileIJ.y + 1);
		p[7] = new Point(curTileIJ.x + 1, curTileIJ.y + 1);
		
		int loinEnd = curTileIJ.x-end.x+curTileIJ.y-end.y;
		
		if(loinEnd > 10 || loinEnd < -10)
			withOrWithoutDiag = 4;
		else
			withOrWithoutDiag = 8;
		
		for (int i = 0; i < withOrWithoutDiag; i++) {
			if (p[i] == null)
				continue;
			if (valide(p[i], marker)) {
				QueueElement qe = new QueueElement(p[i], index);
				queue.add(qe);
				marker[p[i].x][p[i].y] = true;
				// System.out.println("ajout de : " + qe.toString());
			} else {
				// System.out.println("position invalide : " + p[i]);
			}
		}

	}

	private boolean valide(Point point, boolean[][] marker) {
		if (point.x < 0 || point.x >= map.getWidth())
			return false;
		if (point.y < 0 || point.y >= map.getHeight())
			return false;
		if (map.getMap()[point.x][point.y].isWall())
			return false;
		if (marker[point.x][point.y])
			return false;

		return true;
	}

	private ArrayList<Point> sortStock(ArrayList<QueueElement> stock) {
		ArrayList<Point> fs = new ArrayList<Point>();
		QueueElement qe = stock.remove(stock.size() - 1);
		fs.add(qe.getTile());

		do {
			if (stock.isEmpty())
				return fs;
			qe = stock.get(qe.getPrecedente());
			fs.add(qe.getTile());
		} while (qe.getPrecedente() != 0);

		return fs;
	}

	public ArrayList<Point> A_star() {

		ArrayList<QueueElement> stock = new ArrayList<>();
		Point posEnd = Tile.convertToTilePos(end.getX(), end.getY());
		Point curTileIJ = map.findPosTile(start);
		marker[curTileIJ.x][curTileIJ.y] = true;
		QueueElement e = new QueueElement(curTileIJ,posEnd);

		queue.add(e);

		do {
			e = queue.remove();
			stock.add(e);
			curTileIJ = e.getTile();
			appendQueue(curTileIJ,posEnd);
			index++;
		} while (!queue.isEmpty() && !end(curTileIJ, posEnd));

		ArrayList<Point> finalStock = sortStock(stock);

		return finalStock;
	}

	public Point getPosStart() {
		Point curTileIJ = map.findPosTile(start);
		return curTileIJ;
	}

	public Point getPosEnd() {
		Point curTileIJ = map.findPosTile(end);
		return curTileIJ;
	}

}
