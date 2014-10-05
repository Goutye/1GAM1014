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
	private Box boxBar;
	private int valeur = 0;
	private Collide collide = new Collide();

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

		} else {
			this.widthMax = sizeMax;
			this.w = h;
			this.h = w;
		}
		boxBar = new Box(x, y, widthMax, sizeMax);

	}

	public void draw(Renderer r) {
		Graphics2D g = r.getGraphics();
		g.setColor(cBar);
		g.fillRect(x, y, widthMax, sizeMax);
		g.setColor(cButton);
		if (direction)
			g.fillRect(x - w / 2 + 5, y + pos-5, w, h);
		else
			g.fillRect(x + pos, y - h / 2, w, h);
	}

	public int getValeur() {
		return valeur;
	}

	public void update(Point p) {
		if (collide.AABB_point(boxBar, p)) {
			if (direction)
				pos = Math.abs(y - p.y);
			else
				pos = Math.abs(x - p.x);

			valeur = pos * max / sizeMax;
			if (direction)
				valeur = max - valeur;
			

		}

	}

}
