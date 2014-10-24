package ogam1014.ui;

import java.awt.Color;
import java.awt.Point;

import ogam1014.collide.Box;
import ogam1014.collide.Collide;
import ogam1014.graphics.Renderer;

public abstract class Button implements IButton {

	protected int x;
	protected int y;
	protected int w;
	protected int h;
	protected String name;
	protected Color txt;
	protected Color bg;
	protected Color cClick;
	protected boolean hover;
	protected Box box;
	protected boolean down;
	protected int stroke;

	public Button(int x, int y, int width, int height, String name, Color txt,
			Color bg, Color cClick, int stroke) {
		this.x = x;
		this.y = y;
		this.txt = txt;
		this.name = name;
		this.w = width;
		this.h = height;
		this.bg = bg;
		this.cClick = cClick;
		this.box = new Box(x, y, w, h);
		this.stroke = stroke;

	}

	public void draw(Renderer r) {
	}

	@Override
	public void drawSelected(Renderer r) {
	}

	@Override
	public void drawClicked(Renderer r) {
	}

	public void setClick() {
		down = true;
	}
	
	public void setHover(){
		hover = true;
	}
	public boolean update(Point p) {
		if (p != null) {
			if (Collide.AABB_point(box, p)) {
				hover = true;
				return true;
			}
			
			hover = false;
			return false;	
		}
		
		return false;
	}
	
	public void setText(String text) {
		this.name = text;
	}

	public void drawUpdate(Renderer r) {
		if (down && hover) {
			drawClicked(r);
			down = false;
			return;
		}
		
		if (hover) {
			drawSelected(r);
			hover = false;
			return;
		}

		draw(r);
	}

	public boolean getDown() {
		return down;
	}
}
