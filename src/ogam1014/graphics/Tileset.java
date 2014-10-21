package ogam1014.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

import ogam1014.Tile;

public class Tileset implements Serializable{
	private static final long serialVersionUID = -2346856151575637835L;
	static public final int nbDisplayTileW = 12;
	static public final int nbDisplayTileH = 4;
	transient private BufferedImage img;

	public Tileset() {
		try {
			img = ImageIO.read(new File("assets/tileset.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void draw(Renderer r, Tile t, int x, int y) {
		int xTileset = (t.ordinal() % nbDisplayTileW) * Tile.SIZE;
		int yTileset = (t.ordinal() / nbDisplayTileW) * Tile.SIZE;

		r.blit(img, x, y, Tile.SIZE, Tile.SIZE, xTileset, yTileset);
	}
	
	public int getNbTiles(){
		return getNbTileW() * getNbTileH();
	}
	
	public int getNbTileW(){
		return img.getWidth()/Tile.SIZE;
	}
	
	public int getNbTileH(){
		return img.getHeight()/Tile.SIZE;
	}

	public void drawTileset(Renderer r, int x0, int y0, int x, int y) {
		int i, j, m = 0, n = 0;

		for (i = x0; i < nbDisplayTileW + x0; ++i) {
			for (j = y0; j < nbDisplayTileH + y0; ++j) {
				m = (j * getNbTileW() + i) % getNbTileW() - x0;
				n = (j * getNbTileW() + i) / getNbTileW() - y0;
				r.blit(img, x + m * Tile.SIZE, y + n * Tile.SIZE, Tile.SIZE, Tile.SIZE, i * Tile.SIZE, j * Tile.SIZE);
			}
		}
	}
}
