package ogam1014.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import ogam1014.graphics.Renderer;

public class RoundedButton extends Button implements IButton {

	public RoundedButton(int x, int y, int width, int height, String name,
			Color txt, Color bg, Color cClick) {
		super(x, y, width, height, name, txt, bg, cClick, 5);
	
	}
	
	public RoundedButton(int x, int y, int width, int height, String name,
			Color txt, Color bg, Color cClick, int stroke) {
		super(x, y, width, height, name, txt, bg, cClick, stroke);
	
	}

	@Override
	public void draw(Renderer r) {

		Graphics2D g = r.getGraphics();
		g.setColor(bg);
		g.fillRoundRect(x, y, w, h, 20, 20);
		r.setColor(txt);
		r.drawCenteredText(name, x, y+h/2, w);
	}

	@Override
	public void drawSelected(Renderer r) {
		
		Graphics2D g = r.getGraphics();
		draw(r);
		g.setColor(cClick);
		g.setStroke(new BasicStroke(stroke));
		g.drawRoundRect(x, y, w, h, 20, 20);
	}

	@Override
	public void drawClicked(Renderer r) {
		
		Graphics2D g = r.getGraphics();
		g.setColor(cClick);
		g.fillRoundRect(x, y, w, h, 20, 20);
		r.setColor(txt);
		r.drawCenteredText(name, x, y+h/2 , w);
		g.setColor(bg);
		g.setStroke(new BasicStroke(stroke));
		g.drawRoundRect(x, y, w, h, 20, 20);
	}
}
