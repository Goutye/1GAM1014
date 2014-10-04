package ogam1014.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import ogam1014.Tile;

public class Tileset {

	static private final int tW = 16;
	static private final int tH = 16;
	static private final int nbTileW = 6;
	static private final int nbTileH = 3;
	private BufferedImage img;

	public Tileset() {
		try {
			img = ImageIO.read(new File("assets/tileset.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void draw(Renderer r, Tile t, int x, int y) {
		int xTileset = (t.ordinal() % nbTileW) * tW;
		int yTileset = (t.ordinal() / nbTileW) * tH;

		r.blit(img, x, y, tW, tH, xTileset, yTileset);
	}

	public int getW() {
		return tW;
	}

	public int getH() {
		return tH;
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
				r.blit(img, x + m * tW, y + n * tH, tW, tH, i * tW, j * tH);
			}
		}
	}
}
