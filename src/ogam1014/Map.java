package ogam1014;

import java.awt.Graphics;

public class Map {
	private Tileset tileset;
	static private final Tile map[][] = {
		{Tile.WATERBORDER_TL, Tile.WATERBORDER_L, Tile.WATERBORDER_BL},
		{Tile.WATERBORDER_T, Tile.GRASS, Tile.WATERBORDER_B},
		{Tile.WATERBORDER_T, Tile.GRASS, Tile.WATERBORDER_B},
		{Tile.WATERBORDER_TR, Tile.WATERBORDER_R, Tile.WATERBORDER_BR}
	};
	
	public Map(){
		tileset = new Tileset();
	}
	
	public void draw(Graphics g){
		int i,j;
		
		for(i = 0; i < map.length; ++i)
			for(j = 0; j < map[i].length; ++j){
				tileset.draw(g, map[i][j], i * tileset.getW(), j * tileset.getH());
			}
	}
}
