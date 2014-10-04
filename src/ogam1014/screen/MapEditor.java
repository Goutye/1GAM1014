package ogam1014.screen;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import ogam1014.Map;
import ogam1014.Tile;
import ogam1014.collide.Box;
import ogam1014.graphics.Renderer;
import ogam1014.graphics.Tileset;

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
	private Box boxTileset;
	private Box boxMap;
	private Tile currentTile = Tile.GRASS;
	
	public MapEditor() {
		tab = new Tile[DEFAULT_SIZE][DEFAULT_SIZE];
		initTab();
		tileset = new Tileset();
		initBoxesAndMap();
	}
	
	public MapEditor(String fileName) {
		load(fileName);
		tileset = new Tileset();
		initBoxesAndMap();
	}
	
	public MapEditor(int x, int y) {
		tab = new Tile[x][y];
		initTab();
		tileset = new Tileset();
		initBoxesAndMap();
	}
	
	private void initTab() {
		int i, j;
		for (i = 0; i < tab.length; ++i)
			for (j = 0; j < tab[i].length; ++j)
				tab[i][j] = Tile.GRASS;
	}
	
	private void initBoxesAndMap() {
		map = new Map(tab);
		boxTileset = new Box(POS_TILESET_X, POS_TILESET_Y, NB_COL_TILESET * tileset.getW(), ((tileset.getNbTiles() - 1) / NB_COL_TILESET + 1) * tileset.getH());
		boxMap = new Box(POS_MAP_X, POS_MAP_Y, tab.length * tileset.getW(), tab[0].length * tileset.getH());
	}
	
	@Override
	public void update(double dt) {
		if (input.validate.pressed) {
			resize(10, 10);
		}
		
		if (input.leftButton.pressed) {
			if (collide.AABB_point(boxTileset, input.mouse)){
				selectTile();
			}
		}
		
		if (input.leftButton.down) {
			if (collide.AABB_point(boxMap, input.mouse)){
				putTile();
			}
		}	
		
		if (input.rightButton.pressed) {
			save();
		}
	}

	private void putTile() {
		int x = (input.mouse.x - POS_MAP_X) / tileset.getW();
		int y = (input.mouse.y - POS_MAP_Y) / tileset.getH();
		
		tab[x][y] = currentTile;
		map = new Map(tab);
	}

	private void selectTile() {
		int x = (input.mouse.x - POS_TILESET_X) / tileset.getW();
		int y = (input.mouse.y - POS_TILESET_Y) / tileset.getH();
		
		currentTile = Tile.values()[y * NB_COL_TILESET + x]; 
	}
	
	public void resize(int x, int y) {
		Tile newMap[][] = new Tile[x][y];
		
		if (x <= tab.length && y <= tab[0].length) {
			copy(newMap, tab, x, y);
		}
		else if (x > tab.length && y <= tab[0].length) {
			copy(newMap, tab, tab.length, y);
		}
		else if (x <= tab.length && y > tab[0].length) {
			copy(newMap, tab, x, tab[0].length);
		}
		else {
			copy(newMap, tab, tab.length, tab[0].length);
		}
		
		tab = newMap;
		map = new Map(tab);
		boxMap = new Box(POS_MAP_X, POS_MAP_Y, tab.length * tileset.getW(), tab[0].length * tileset.getH());
	}
	
	private void copy(Tile m1[][], Tile m2[][], int x, int y) {
		int i, j;
		
		for (i = 0; i < x; ++i) {
			for (j = 0; j < y; ++j) {
				m1[i][j] = m2[i][j];
			}
		}
	}
	
	private void save() {
		try{
			FileOutputStream fout = new FileOutputStream(new File("assets/maps/map.tile"));
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(tab);
			oos.close();
			System.out.println("Map saved!");
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	private void load(String fileName) {
		try{
			FileInputStream fint = new FileInputStream("assets/maps/" + fileName);
			ObjectInputStream ois = new ObjectInputStream(fint);
			tab = (Tile[][]) ois.readObject();
			ois.close();
			System.out.println("Map loaded!");
		}catch(IOException e){
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void draw(Renderer r) {
		map.draw(r, POS_MAP_X , POS_MAP_Y);
		tileset.drawTileset(r, POS_TILESET_X, POS_TILESET_Y, NB_COL_TILESET);
		drawSelectedTile(r);
	}
	
	public void drawSelectedTile(Renderer r) {
		int x = currentTile.ordinal() % NB_COL_TILESET * tileset.getW() + POS_TILESET_X;
		int y = currentTile.ordinal() / NB_COL_TILESET * tileset.getH() + POS_TILESET_Y;
		Color prevColor = r.getGraphics().getColor();
		Stroke prevStroke = r.getGraphics().getStroke();
		r.getGraphics().setColor(Color.red);
		r.getGraphics().setStroke(new BasicStroke(1));
		r.getGraphics().drawRoundRect(x, y, tileset.getW(), tileset.getH(), 0, 0);
		r.getGraphics().setStroke(prevStroke);
		r.getGraphics().setColor(prevColor);
	}
}
