package ogam1014;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tileset {
	
	static private final int tW = 16;
	static private final int tH = 16;
	private BufferedImage img;
	
	public Tileset(){ 
		try{
			img = ImageIO.read(new File("assets/tileset.png"));
		}catch(IOException e){
			
		}
	}
	
	public void draw(Graphics g, Tile t, int x, int y){
		int xTileset = (t.ordinal()%6) * tW;
		int yTileset = (t.ordinal()/6) * tH;
		
		g.drawImage(img,  x, y, x+tW, y+tH, xTileset, yTileset, xTileset+tW, yTileset+tH, null);
	}
	
	public int getW(){
		return tW;
	}
	
	public int getH(){
		return tH;
	}
}
