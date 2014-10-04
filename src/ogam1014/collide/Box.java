package ogam1014.collide;

@SuppressWarnings("serial")
public class Box extends java.awt.Rectangle{
	
	public Box(int x, int y, int w, int h){
		super(x, y, w, h);
	}
	
	public int getXMax(){
		return x + width - 1;
	}
	
	public int getYMax(){
		return y + height - 1;
	}
}
