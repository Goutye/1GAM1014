package ogam1014.ui;

import ogam1014.graphics.Renderer;

public interface IButton {

	void draw(Renderer r);
	void drawSelected(Renderer r);
	void drawClicked(Renderer r);
}
