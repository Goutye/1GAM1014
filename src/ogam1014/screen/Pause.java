package ogam1014.screen;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import ogam1014.Engine;
import ogam1014.graphics.Renderer;
import ogam1014.ui.Button;
import ogam1014.ui.RoundedButton;

public class Pause extends Screen {

	private Screen parent;
	private double time;
	private Button save = new RoundedButton((int) Engine.WIDTH / 2-Engine.WIDTH/10,
			(int) (Engine.HEIGHT * 0.4), Engine.WIDTH/5, Engine.HEIGHT/7, "Save", Color.white,
			Color.gray,Color.black);
	private Button load = new RoundedButton((int) Engine.WIDTH / 2-Engine.WIDTH/10,
			(int) (Engine.HEIGHT * 0.6), Engine.WIDTH/5, Engine.HEIGHT/7, "Load", Color.white,
			Color.gray,Color.black);
	
	private List<Button> buttons = new ArrayList<Button>();
	private int counter=0;

	public Pause(Screen parent) {
		this.parent = parent;
		buttons.add(save);
		buttons.add(load);
	}

	@Override
	public void update(double dt) {
		if (input.pause.pressed) {
			engine.setScreen(parent);
			return;
		}
		
		if (input.up.pressed && counter > 0) {
			counter--;
		}

		if (input.down.pressed) {
			counter++;
		}
		
		int i=0;
		for(Button button: buttons) {
			boolean hover = button.update(input.mouse);
			if(counter==i)
			button.setHover();
			
			
			if(hover){
				if(input.leftButton.pressed || input.validate.down) {
					button.setClick();
					
					if(counter==0){
						System.out.println(((Game)parent).toString());
						((Game)parent).save();
						
						
					}
						
					else if(counter==1){
						((Game)parent).load();	
						System.out.println(((Game)parent).toString());
					}
					
				}
				counter=i;
				
			}
			
			i++;
		}
			
		

		counter %= buttons.size();
		
		time += dt;
	}

	@Override
	public void draw(Renderer r) {
		boolean show = (int)(time * 2) % 2 == 0;
		
		for (Button button : buttons) {
		button.drawUpdate(r);
				
		}
	
		
		if (show) {
			r.setColor(Color.BLACK);
			r.drawCenteredText("Press 'p'", Engine.WIDTH/2, Engine.HEIGHT*0.2);
		}
	}

}
