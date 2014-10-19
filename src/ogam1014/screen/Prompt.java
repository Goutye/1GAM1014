package ogam1014.screen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import ogam1014.graphics.Renderer;
import ogam1014.ui.Button;
import ogam1014.ui.RectangleButton;

public class Prompt extends Screen {
	
	private Screen parentScreen;
	private IPromptCallback callback;
	
	private List<String> choices = new ArrayList<String>();
	private String text;
	private List<Button> buttons = new ArrayList<Button>();
	
	private int selected = 0;
	
	public Prompt(Screen parentScreen, IPromptCallback callback) {
		this.parentScreen = parentScreen;
		this.callback = callback;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void addOption(int i, String txt) {
		choices.add(txt);
		buttons.add(new RectangleButton(20, 100 + 25 * i, 100, 20,
				txt, new Color(50, 50, 50),
				new Color(0.7f, 0.7f, 0.7f), new Color(0.4f, 0.4f, 0.4f)));
	}

	@Override
	public void update(double dt) {
		if(input.up.pressed && selected > 0)
			selected--;

		if(input.down.pressed && selected < choices.size() - 1)
			selected++;
		
		if(input.validate.pressed) {
			callback.promptScreenCallback(selected);
			engine.setScreen(parentScreen);
		}
	}

	@Override
	public void draw(Renderer r) {
		int i = 0;
		Graphics2D g = r.getGraphics();
		
		parentScreen.draw(r);
		g.setColor(new Color(0, 0, 0, 0.5f));
		g.fillRect(0, 0, engine.getWidth(), engine.getHeight());
		
		g.setColor(new Color(255, 255, 255));
		r.drawCenteredText(text, 40, 100, 200);
		
		for (Button button : buttons) {
			if (selected == i)
				button.drawSelected(r);
			else
				button.drawUpdate(r);

			i++;
		}
	}
}
