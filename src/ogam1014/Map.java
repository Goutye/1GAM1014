package ogam1014;

public class Map {
	private Tileset tileset;
	static private final Tile map[][] = {
		{Tile.WATERBORDER_TL, Tile.WATERBORDER_T, Tile.WATERBORDER_T, Tile.WATERBORDER_TR},
		{Tile.WATERBORDER_L, Tile.GRASS, Tile.GRASS, Tile.WATERBORDER_R},
		{Tile.WATERBORDER_BL, Tile.WATERBORDER_B, Tile.WATERBORDER_BR}
	};
}
