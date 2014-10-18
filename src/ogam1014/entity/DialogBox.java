package ogam1014.entity;

import java.awt.Color;

import ogam1014.collide.Box;
import ogam1014.entity.Entity;
import ogam1014.graphics.Renderer;

public class DialogBox extends Entity {
	private static int maxWidth = 150;
	private static int maxHeight = 40;
	private static int fontSize = 10;
	
	private String text;
	private Entity e;
	private Box box = new Box(0,0,0,0);
	private Boolean validBox; 
	private int currentText = 0;
	
	public DialogBox(Entity e, String text, Boolean validBox) {
		this.text = text;
		this.validBox = validBox;
		this.passiv = true;
		this.e = e;
		
		if (text.length() * 10 <= maxWidth) {
			box.width = text.length() * 10;
			box.height = maxHeight/2;
		}
		else {
			box.width = maxWidth;
			box.height = maxHeight;
		}
		
		box.x = (int) e.getX() - box.width/2;
		box.y = (int) e.getY() - box.height;
	}

	@Override
	public void update(double dt) {
		box.x = (int) e.getX() - box.width/2;
		box.y = (int) e.getY() - box.height;
		
		/* TODO Time duration of a dialogBox for a non validBox
		 * TODO If validBox, need to block other keyboard action (iPrompt ?)
		 * TODO Transparency + real bubble
		 */
	}

	@Override
	public void draw(Renderer r) {
		r.setColor(Color.black);
		r.setStroke(1);
		r.getGraphics().fillRoundRect(box.x, box.y, box.width, box.height, 10, 10);
		r.setColor(Color.white);
		r.drawCenteredText(text, box.x, box.y + 5, box.width);
	}
}
