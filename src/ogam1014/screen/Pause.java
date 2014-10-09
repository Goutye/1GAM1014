package ogam1014.screen;

import java.awt.Color;

import ogam1014.Engine;
import ogam1014.graphics.Renderer;

public class Pause extends Screen {

	private Screen parent;
	private double time;

	public Pause(Screen parent) {
		this.parent = parent;
	}

	@Override
	public void update(double dt) {
		if (input.pause.pressed) {
			engine.setScreen(parent);
			return;
		}
		
		time += dt;
	}

	@Override
	public void draw(Renderer r) {
		boolean show = (int)(time * 2) % 2 == 0;
		
		if (show) {
			r.setColor(Color.BLACK);
			r.drawCenteredText("Press 'p'", Engine.WIDTH, Engine.HEIGHT);
		}
	}

}
