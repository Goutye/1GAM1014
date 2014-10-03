package ogam1014.screen;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import ogam1014.Engine;
import ogam1014.ui.Button;

public class MOptions extends Screen {
	private Button back = new Button((int) Engine.WIDTH -100,
			(int) (Engine.HEIGHT * 0.8), 100, 50, "Back", Color.BLACK,
			Color.YELLOW);
	
	private Button sound = new Button((int) Engine.WIDTH/5,
			(int) (Engine.HEIGHT * 0.2), 100, 50, "Sound", Color.BLACK,
			Color.YELLOW);
	private List<Button> buttons = new ArrayList<Button>();
	private int counter = 0;
	
	public MOptions() {
		buttons.add(sound);
		buttons.add(back);
	}
	
	public void update(double dt) {
		if (input.validate.down && counter == 0) {
			
		}
		
		if (input.validate.pressed && counter == 1) {
			engine.setScreen(new Menu());
		}
		
		if (input.up.pressed && counter > 0) {
			counter--;
		}
		
		if (input.down.pressed) {
			counter++;
		}

		counter %= buttons.size();
	}
	
	public void draw(Graphics g) {
		int i = 0;
		
		for (Button button : buttons) {
			if (counter == i)
				button.drawSelectedRectangle(g);
			else
				button.drawRectangle(g);
			
			i++;
		}
	}
}
