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
	
	public void drawTile(Graphics g, Tile t, int x, int y){
		int xTileset = t.ordinal()%10;
		int yTileset = t.ordinal()/10;
		
		g.drawImage(img,  x, y, x+tW, x+tH, xTileset, yTileset, xTileset+tW, yTileset+tH, null);
	}
}
