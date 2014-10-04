package ogam1014.ui;

import java.awt.Color;

import ogam1014.graphics.Renderer;

public class Button implements IButton{
	
	protected int x;
	protected int y;
	protected int w;
	protected int h;
	protected String name;
	protected Color txt;
	protected Color bg;
	
	public Button(int x, int y, int weight, int height, String name, Color txt,
			Color bg) {
		this.x = x;
		this.y = y;
		this.txt = txt;
		this.name = name;
		this.w = weight;
		this.h = height;
		this.bg = bg;

	}
	public void drawButton(Renderer r) {
	}
	@Override
	public void drawSelectedButton(Renderer r) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void drawClickedButton(Renderer r) {
		// TODO Auto-generated method stub
		
	}
	

}
