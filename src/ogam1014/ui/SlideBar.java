package ogam1014.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import ogam1014.collide.Box;
import ogam1014.collide.Collide;
import ogam1014.graphics.Renderer;

public class SlideBar {

	private int x;
	private int y;
	private int max = 100;
	private int sizeMax = 10;
	private int widthMax = 10;
	private boolean direction;
	private int h;
	private int w;
	private Color cButton;
	private Color cBar;
	private int pos = 0;
	private Box box;
	private int valeur = 0;

	public SlideBar(int x, int y, boolean direction, int sizeMax, int w, int h,
			Color cButton, Color cBar, int max) {
		this.x = x;
		this.y = y;
		this.direction = direction;

		this.cButton = cButton;
		this.cBar = cBar;
		this.max = max;

		if (this.direction) {
			this.sizeMax = sizeMax;
			this.w = w;
			this.h = h;

		}
		else {
			this.widthMax = sizeMax;
			this.w = h;
			this.h = w;
		}
		
		new Box(x, y, widthMax, sizeMax); /** TODO: dafuq? */
		
		if (direction)
			box = new Box(x - w / 2 + 5, y + pos - 5, w, h);
		else 
			box = new Box(x + pos, y - h / 2, w, h);
	}

	public void draw(Renderer r) {
		Graphics2D g = r.getGraphics();
		g.setColor(cBar);
		g.fillRect(x, y, widthMax, sizeMax);
		g.setColor(cButton);
		
		if (direction) {
			g.fillRect(x - w / 2 + 5, y + pos - 5, w, h);
			r.setColor(cBar);
			r.drawText(Integer.toString(valeur), x - w / 2 + 5, y + pos - 5);
		}
		else {
			g.fillRect(x + pos, y - h / 2, w, h);
			r.setColor(cBar);
			r.drawText(Integer.toString(valeur), x + pos, y - h / 2);
		}
	}

	public int getValeur() {
		return valeur;
	}

	public void update(Point p) {
		
		if (Collide.AABB_point(box, p)) {
			if (direction) {
				int temp = Math.abs(y - p.y);
				int maxTemp = temp * max / sizeMax;
				maxTemp = max - maxTemp;
				
				if (maxTemp > 0) {
					pos = Math.abs(y - p.y);
					box.setLocation(x, pos + y - 5);
					valeur = pos * max / sizeMax;
					valeur = max - valeur;
				}
			}
			else {
				int temp = Math.abs(x - p.x);
				int maxTemp = temp * max / sizeMax;
				maxTemp = max - maxTemp;
				
				if (maxTemp > 0) {
					pos = Math.abs(x - p.x);
					box.setLocation(pos + x - 5, y);
					valeur = pos * max / widthMax;
				}
			}
		}
	}
}
