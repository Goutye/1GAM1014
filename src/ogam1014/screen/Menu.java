package ogam1014.screen;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import ogam1014.Engine;
import ogam1014.graphics.Renderer;
import ogam1014.ui.Button;
import ogam1014.ui.RoundedButton;

public class Menu extends Screen {

	private Screen parent;
	private Button start = new RoundedButton((int) Engine.WIDTH / 2-Engine.WIDTH/10,
			(int) (Engine.HEIGHT * 0.2), Engine.WIDTH/5, Engine.HEIGHT/7, "Start", Color.BLACK,
			Color.YELLOW,Color.ORANGE);
	private Button options = new RoundedButton((int) Engine.WIDTH / 2 - Engine.WIDTH/10,
			(int) (Engine.HEIGHT * 0.4), Engine.WIDTH/5, Engine.HEIGHT/7, "Options", Color.BLACK,
			Color.YELLOW,Color.ORANGE);
	private Button quit = new RoundedButton((int) Engine.WIDTH / 2 -Engine.WIDTH/10,
			(int) (Engine.HEIGHT * 0.6), Engine.WIDTH/5, Engine.HEIGHT/7, "Quit", Color.BLACK,
			Color.YELLOW,Color.ORANGE);
	private List<Button> buttons = new ArrayList<Button>();
	private int counter = 0;

	public Menu() {
		buttons.add(start);
		buttons.add(options);
		buttons.add(quit);
	}

	public void update(double dt) {
		if (input.validate.pressed && counter == 0) {
			engine.setScreen(new Game());
		}

		if (input.validate.pressed && counter == 1) {
			engine.setScreen(new MOptions(this));
		}
		if (input.validate.pressed && counter == 2) {
			System.exit(0);
		}

		if (input.up.pressed && counter > 0) {
			counter--;
		}

		if (input.down.pressed) {
			counter++;
		}

		// Just to access to MapEditor faster
		if (input.rightButton.pressed) {
			engine.setScreen(new MapEditor("map.tile"));
		}
		
		int i=0;
		for(Button button: buttons) {
			if(input.leftButton.pressed) {
				button.setClick();
				
				if(counter==0)
				{

					engine.setScreen(new Game());
				}
					
				else if(counter==1)
					engine.setScreen(new MOptions(this));
					
				else if(counter==2)
					System.exit(0);
			}
			
			if(button.update(input.mouse))
				counter=i;
			
			i++;
		}

		counter %= buttons.size();
	}

	@Override
	public void draw(Renderer r) {
		int i=0;
		for (Button button : buttons) {
			button.drawUpdate(r);
			
			if (counter == i) {
				button.drawSelected(r);
				
				if (input.validate.down) { /** TODO: dafuq? */
					button.drawClicked(r);
				}
			}
				
			i++;
		}
	}
}
