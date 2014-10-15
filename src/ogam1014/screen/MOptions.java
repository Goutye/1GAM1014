package ogam1014.screen;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import ogam1014.Engine;
import ogam1014.graphics.Renderer;
import ogam1014.ui.Button;
import ogam1014.ui.RoundedButton;
import ogam1014.ui.SlideBar;

public class MOptions extends Screen {
	
	
	
	private Screen parent;
	private Button back = new RoundedButton((int) Engine.WIDTH - Engine.WIDTH/4,
			(int) (Engine.HEIGHT * 0.7), Engine.WIDTH/5, Engine.HEIGHT/5, "Back", Color.BLACK,
			Color.YELLOW,Color.ORANGE);

	private Button sound = new RoundedButton((int) Engine.WIDTH / 5-Engine.WIDTH/10,
			(int) (Engine.HEIGHT * 0.2), Engine.WIDTH/5, Engine.HEIGHT/5, "Sound", Color.BLACK,
			Color.YELLOW,Color.ORANGE);
	
	private SlideBar slide= new SlideBar((int) Engine.WIDTH / 2,
			(int) (Engine.HEIGHT * 0.2), true, 100, 40, 20, Color.yellow, Color.orange, 100);
	private List<Button> buttons = new ArrayList<Button>();
	private int counter = 0;

	public MOptions(Screen parent) {
		buttons.add(sound);
		buttons.add(back);
		this.parent=parent;
	}

	public void update(double dt) {
		if (input.validate.down && counter == 0) {

		}

		if (input.validate.released && counter == 1) {
			engine.setScreen(parent);
		}

		if (input.up.pressed && counter > 0) {
			counter--;
		}

		if (input.down.pressed) {
			counter++;
		}
		
		if(input.leftButton.pressed || input.leftButton.down) {
			slide.update(input.mouse);
		}
		
		int i=0;

		for(Button button: buttons) {
			boolean hover = button.update(input.mouse);
			if(counter==i)
			button.setHover();
			
			if(hover){
				if(input.leftButton.pressed || input.validate.down) {
					button.setClick();
					
					if(counter==0) {
						
					}
					if(counter==1) {
						engine.setScreen(new Menu());
					}
				counter=i;
				
			}
			
			i++;
		}
		
		}
		counter %= buttons.size();
	}
	
	public void draw(Renderer r) {
		
		slide.draw(r);

		for (Button button : buttons) {
			button.drawUpdate(r);
			
	}
	}
}
