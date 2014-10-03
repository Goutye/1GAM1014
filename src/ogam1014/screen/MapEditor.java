package ogam1014.screen;

import java.awt.Graphics;

import ogam1014.Map;
import ogam1014.Tile;
import ogam1014.Tileset;



public class MapEditor extends Screen{
	private static final int DEFAULT_SIZE = 100;
	private static final int POS_MAP_X = 100;
	private static final int POS_MAP_Y = 100;
	private static final int POS_TILESET_X = 0;
	private static final int POS_TILESET_Y = 0;
	private static final int NB_COL_TILESET = 6;
	
	
	private Tileset tileset;
	private Tile tab[][];
	private Map map;
	
	public MapEditor(){
		tab = new Tile[DEFAULT_SIZE][DEFAULT_SIZE];
		initTab();
		tileset = new Tileset();
		map = new Map(tab);
	}
	
	public MapEditor(int x, int y){
		tab = new Tile[x][y];
		initTab();
		tileset = new Tileset();
		map = new Map(tab);
	}
	
	private void initTab(){
		int i,j;
		for(i = 0; i < tab.length; ++i)
			for(j = 0; j < tab[i].length; ++j)
				tab[i][j] = Tile.GRASS;
	}
	
	@Override
	public void update(double dt) {
		if(input.validate.pressed){
			resize(10, 10);
		}
		
		// TODO Click to select a Tile
		// TODO Click on the map to put a Tile
		// TODO Bonus : Stay down the click to put a lot of Tile in a row
		
	}

	@Override
	public void draw(Graphics g) {
		map.draw(g , POS_MAP_X , POS_MAP_Y);
		tileset.drawTileset(g, POS_TILESET_X, POS_TILESET_Y, NB_COL_TILESET);
	}
	
	public void resize(int x, int y){
		Tile newMap[][] = new Tile[x][y];
		
		if(x <= tab.length && y <= tab[0].length){
			copy(newMap, tab, x, y);
		}else if(x > tab.length && y <= tab[0].length){
			copy(newMap, tab, tab.length, y);
		}else if(x <= tab.length && y > tab[0].length){
			copy(newMap, tab, x, tab[0].length);
		}else{
			copy(newMap, tab, tab.length, tab[0].length);
		}
		
		tab = newMap;
		map = new Map(tab);
	}
	
	private void copy(Tile m1[][], Tile m2[][], int x, int y){
		int i,j;
		
		for(i = 0; i < x; ++i){
			for(j = 0; j < y; ++j){
				m1[i][j] = m2[i][j];
			}
		}
	}
}
