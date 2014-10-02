package ogam1014.screen;

import java.awt.Color;
import java.awt.Graphics;

public class Button {
	
	private int _x;
	private int _y;
	private int _w;
	private int _h;
	private String _name;
	private Color _bg;
	private Color _txt;
	
	public Button(int x,int y,int weight, int height,String name, Color txt,Color bg){
		_x=x;
		_y=y;
		_txt=txt;
		_name =name;
		_w=weight;
		_h=height;
		_bg=bg;
		
	}
	
	void drawRectangle(Graphics g){
		
		g.setColor(_bg);
		g.fillRect(_x, _y, _w, _h);
		g.setColor(_txt);
		g.drawString(_name, _x+_w/2-_name.length()*5, _y+_h/2);
	}
	
void drawOval(Graphics g){
		
		g.setColor(_bg);
		g.fillOval(_x, _y, _w, _h);
		g.setColor(_txt);
		g.drawString(_name, _x+_w/2-_name.length()*4, _y+_h/2);
	}

}
