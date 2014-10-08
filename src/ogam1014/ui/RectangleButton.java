package ogam1014.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import ogam1014.graphics.Renderer;


public class RectangleButton extends Button implements IButton{
	

	
	public RectangleButton(int x, int y, int weight, int height, String name, Color txt,
			Color bg,Color cClick) {
		super(x, y, weight, height, name, txt,
				bg,cClick);
	}
	@Override
	public void draw(Renderer r) {
		
		Graphics2D g = r.getGraphics();
		g.setColor(bg);
		g.fillRect(x, y, w, h);
		r.setColor(txt);
		r.drawCenteredText(name, x + w / 2, y + h / 2);
		
	}

	@Override
	public void drawSelected(Renderer r) {
		

		Graphics2D g = r.getGraphics();
		draw(r);
		g.setColor(cClick);
		g.setStroke(new BasicStroke(5));
		g.drawRect(x, y, w, h);
		
	}

	@Override
	public void drawClicked(Renderer r) {
		super.drawClicked(r);

		Graphics2D g = r.getGraphics();
		g.setColor(bg);
		g.fillRect(x, y, w, h);
		r.setColor(txt);
		r.drawCenteredText(name, x + w / 2, y + h / 2);
		g.setColor(cClick);
		g.setStroke(new BasicStroke(5));
		g.drawRect(x, y, w, h);
		

		
	}

}
