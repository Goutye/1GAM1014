package ogam1014.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Button {
	
	private int x;
	private int y;
	private int w;
	private int h;
	private String name;
	private Color txt;
	private Color bg;
	
	public Button(int x,int y,int weight, int height,String name, Color txt,Color bg){
		this.x=x;
		this.y=y;
		this.txt=txt;
		this.name =name;
		this.w=weight;
		this.h=height;
		this.bg=bg;
		
	}
	
	public void drawRectangle(Graphics g){
		
		g.setColor(bg);
		g.fillRoundRect(x, y, w, h,20,20);
		g.setColor(txt);
		g.drawString(name, x+w/2-name.length()*4, y+h/2);
	}
	
	public void drawSelectedRectangle(Graphics g){
		
		Graphics2D g2 = (Graphics2D) g;
	
		drawRectangle(g);
		g2.setColor(Color.ORANGE);
		g2.setStroke(new BasicStroke(5));
		g.drawRoundRect(x, y, w, h,20,20);
		
		
		
	}
	
}

