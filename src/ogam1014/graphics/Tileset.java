package ogam1014.graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

import ogam1014.Tile;

public class Tileset implements Serializable{
	static private final int nbTileW = 12;
	static private final int nbTileH = 4;
	transient private BufferedImage img;

	public Tileset() {
		try {
			img = ImageIO.read(new File("assets/tileset.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void draw(Renderer r, Tile t, int x, int y) {
		int xTileset = (t.ordinal() % nbTileW) * Tile.SIZE;
		int yTileset = (t.ordinal() / nbTileW) * Tile.SIZE;

		r.blit(img, x, y, Tile.SIZE, Tile.SIZE, xTileset, yTileset);
	}
	
	public int getNbTiles(){
		return nbTileH * nbTileW;
	}
	

	public void drawTileset(Renderer r, int x, int y, int col) {
		int i, j, m = 0, n = 0;

		for (i = 0; i < nbTileW; ++i) {
			for (j = 0; j < nbTileH; ++j) {
				m = (j * nbTileW + i) % col;
				n = (j * nbTileW + i) / col;
				r.blit(img, x + m * Tile.SIZE, y + n * Tile.SIZE, Tile.SIZE, Tile.SIZE, i * Tile.SIZE, j * Tile.SIZE);
			}
		}
	}
}
