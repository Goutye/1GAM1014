package ogam1014.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import ogam1014.graphics.Renderer;


public class RectangleButton extends Button implements IButton{

	private int x;
	private int y;
	private int w;
	private int h;
	private String name;
	private Color txt;
	private Color bg;
	
	public RectangleButton(int x, int y, int weight, int height, String name, Color txt,
			Color bg) {
		super(x, y, weight, height, name, txt,
				bg);
		

	}
	@Override
	public void drawButton(Renderer r) {
		Graphics2D g = r.getGraphics();
		g.setColor(bg);
		g.fillRect(x, y, w, h);
		g.setColor(txt);
		g.drawString(name, x + w / 2 - name.length() * 4, y + h / 2);
		
	}

	@Override
	public void drawSelectedButton(Renderer r) {
		Graphics2D g = r.getGraphics();
		drawButton(r);
		g.setColor(Color.ORANGE);
		g.setStroke(new BasicStroke(5));
		g.drawRect(x, y, w, h);
		
	}

	@Override
	public void drawClickedButton(Renderer r) {
		Graphics2D g = r.getGraphics();
		g.setColor(Color.ORANGE);
		g.fillRect(x, y, w, h);
		g.setColor(txt);
		g.drawString(name, x + w / 2 - name.length() * 4, y + h / 2);
		g.setColor(Color.YELLOW);
		g.setStroke(new BasicStroke(5));
		g.drawRect(x, y, w, h);

		
	}

}
