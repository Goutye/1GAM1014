package ogam1014.ui;

import java.awt.Color;

import ogam1014.InputHandler;
import ogam1014.graphics.Renderer;

public class Button implements IButton{
	
	protected int x;
	protected int y;
	protected int w;
	protected int h;
	protected String name;
	protected Color txt;
	protected Color bg;
	protected Color cClick;
	protected boolean isDown=false;
	
	public Button(int x, int y, int weight, int height, String name, Color txt,
			Color bg,Color cClick) {
		this.x = x;
		this.y = y;
		this.txt = txt;
		this.name = name;
		this.w = weight;
		this.h = height;
		this.bg = bg;
		this.cClick = cClick;

	}
	public void draw(Renderer r) {
	}
	@Override
	public void drawSelected(Renderer r) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void drawClicked(Renderer r) {
		// TODO Auto-generated method stub
		
	}
	
	public void update(Renderer r, InputHandler input) {
		if(input.validate.down || input.leftButton.pressed)
			isDown=true;
		else isDown=false;
		if (isDown){
			drawClicked(r);
		}
		else{
			drawSelected(r);
			
		}
	}
	
	public boolean getIsDown(){
		return isDown;
	}
	

}
