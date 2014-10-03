package ogam1014;

import java.awt.Graphics;

import ogam1014.graphics.Tileset;

public class Map {
	private Tileset tileset;
	private Tile map[][] = {
			{Tile.WATERBORDER_TL, Tile.WATERBORDER_L, Tile.WATERBORDER_BL},
			{Tile.WATERBORDER_T, Tile.GRASS, Tile.WATERBORDER_B},
			{Tile.WATERBORDER_T, Tile.GRASS, Tile.WATERBORDER_B},
			{Tile.WATERBORDER_TR, Tile.WATERBORDER_R, Tile.WATERBORDER_BR}
		};
	
	public Map(){
		tileset = new Tileset();
	}
	
	public Map(Tile map[][]){
		this.map = map;
		tileset = new Tileset();
	}
	
	public void draw(Graphics g){
		int i, j;
		
		for(i = 0; i < map.length; ++i)
			for(j = 0; j < map[i].length; ++j) {
				tileset.draw(g, map[i][j], i * tileset.getW(), j * tileset.getH());
			}
	}
	
	public void draw(Graphics g, int dx, int dy) {
		int i, j;
		
		for(i = 0; i < map.length; ++i)
			for(j = 0; j < map[i].length; ++j){
				tileset.draw(g, map[i][j], dx + i * tileset.getW(), dy + j * tileset.getH());
			}
	}
}
