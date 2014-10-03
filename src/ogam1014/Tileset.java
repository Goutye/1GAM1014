package ogam1014;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tileset {
	
	static private final int tW = 16;
	static private final int tH = 16;
	static private final int nbTileW = 6;
	static private final int nbTileH = 3;
	private BufferedImage img;
	
	public Tileset(){ 
		try{
			img = ImageIO.read(new File("assets/tileset.png"));
		}catch(IOException e){
			
		}
	}
	
	public void draw(Graphics g, Tile t, int x, int y){
		int xTileset = (t.ordinal() % nbTileW) * tW;
		int yTileset = (t.ordinal()/ nbTileW) * tH;
		
		g.drawImage(img,  x, y, x+tW, y+tH, xTileset, yTileset, xTileset+tW, yTileset+tH, null);
	}
	
	public int getW(){
		return tW;
	}
	
	public int getH(){
		return tH;
	}
	
	public void drawTileset(Graphics g, int x, int y, int col){
		int i, j, m = 0, n = 0;
		
		for(i = 0; i < nbTileW; ++i){
			for(j = 0; j < nbTileH; ++j){
				m = (j * nbTileW + i) % col;
				n = (j * nbTileW + i) / col;
				g.drawImage(img, x + m*tW, y + n*tH, x + m*tW + tW, y + n*tH + tH, i * tW, j * tH, i * tW + tW, j * tH + tH, null);
			}
		}
	}
}
