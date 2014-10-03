package ogam1014.screen;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import ogam1014.Engine;

public class Menu extends Screen {
	
	private Button start = new Button((int)Engine.WIDTH / 2, (int) (Engine.HEIGHT * 0.2),100,50,"Start",Color.BLACK,Color.YELLOW);
	private Button options = new Button((int)Engine.WIDTH / 2, (int) (Engine.HEIGHT * 0.4),100,50,"Options",Color.BLACK,Color.YELLOW);
	private Button quit = new Button((int)Engine.WIDTH / 2, (int) (Engine.HEIGHT * 0.6),100,50,"Quit",Color.BLACK,Color.YELLOW);
	private List<Button> buttons = new ArrayList<Button>();
	private int counter=0;
	
	
	public Menu(){
		buttons.add(start);
		buttons.add(options);
		buttons.add(quit);
		
	}
	
	
	public void update(double dt) {
		
		if (input.validate.down) {
			engine.setScreen(new Game());
		}
		if(input.up.down){
			counter--;
	
			
		}
		if(input.down.down){
			counter++;
			
		}
		
		counter%=buttons.size();
	}

	@Override
	public void draw(Graphics g) {
		int i=0;
		for(Button button:buttons){
			if(counter==i){
				button.drawSelectedRectangle(g);
			}
			else
			button.drawRectangle(g);
			i++;
			
		}
	
		//g.drawString(Engine.NAME, (int)Engine.WIDTH / 2, (int) (Engine.HEIGHT * 0.2));
	}

}
