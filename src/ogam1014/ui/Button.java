package ogam1014.ui;

import java.awt.Color;
import java.awt.Point;

import ogam1014.collide.Box;
import ogam1014.collide.Collide;
import ogam1014.graphics.Renderer;

public class Button implements IButton {

	protected int x;
	protected int y;
	protected int w;
	protected int h;
	protected String name;
	protected Color txt;
	protected Color bg;
	protected Color cClick;
	protected boolean down;
	protected boolean hover;
	protected Box box;
	protected boolean click;

	public Button(int x, int y, int weight, int height, String name, Color txt,
			Color bg, Color cClick) {
		this.x = x;
		this.y = y;
		this.txt = txt;
		this.name = name;
		this.w = weight;
		this.h = height;
		this.bg = bg;
		this.cClick = cClick;
		this.box = new Box(x, y, w, h);

	}

	public void draw(Renderer r) {
	}

	@Override
	public void drawSelected(Renderer r) {

		// TODO Auto-generated method stub

	}

	@Override
	public void drawClicked(Renderer r) {

		// TODO Auto-generated method stub

	}

	public void setClick() {
		click = true;
	}

	public boolean update(Point p) {
		if (p != null) {
			if (Collide.AABB_point(box, p)){
				hover = true;
			return true;
			}

			else{
				hover = false;
			return false;	
			}
		}
		return false;
		
	}

	public void drawUpdate(Renderer r) {
		if (click && hover) {
			System.out.print("dessin");
			drawClicked(r);
			click = false;
			return;
		}
		if (hover && !down) {
			drawSelected(r);
			click = false;
			hover = false;
			return;
		}

		draw(r);
	}

	public boolean getDown() {
		return down;
	}

}
