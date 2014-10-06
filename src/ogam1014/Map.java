package ogam1014;
 
import ogam1014.graphics.Renderer;
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
	
	public void draw(Renderer r){
		int i, j;
		
		for(i = 0; i < map.length; ++i)
			for(j = 0; j < map[i].length; ++j) {
				tileset.draw(r, map[i][j], i * Tile.SIZE, j * Tile.SIZE);
			}
	}
	
	public void draw(Renderer r, int dx, int dy, int nbTileX, int nbTileY, int idTileStartX, int idTileStartY) {
		int i, j;
		
		for(i = idTileStartX; i < Math.min(map.length, idTileStartX + nbTileX); ++i)
			for(j = idTileStartY; j < Math.min(map[i].length, idTileStartY + nbTileY); ++j){
				tileset.draw(r, map[i][j], dx + (i - idTileStartX) * Tile.SIZE, dy + (j - idTileStartY) * Tile.SIZE);
			}
	}
}
