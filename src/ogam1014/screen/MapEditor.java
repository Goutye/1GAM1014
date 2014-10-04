package ogam1014.screen;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Point;
import java.awt.Stroke;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import ogam1014.Map;
import ogam1014.Tile;
import ogam1014.collide.Box;
import ogam1014.graphics.Renderer;
import ogam1014.graphics.Tileset;
import ogam1014.ui.Button;
import ogam1014.ui.RectangleButton;

public class MapEditor extends Screen{
	
	private static final int DEFAULT_SIZE = 100;
	private static final int POS_MAP_X = 100;
	private static final int POS_MAP_Y = 100;
	private static final int POS_TILESET_X = 0;
	private static final int POS_TILESET_Y = 0;
	private static final int NB_COL_TILESET = 6;
	private static final int MAP_DISPLAY_NB_TILE_X = 20;
	private static final int MAP_DISPLAY_NB_TILE_Y = 14;
	
	private static final Box BOX_SIZE_X_DECR = new Box(100, 0, 16, 16);
	private static final Box BOX_SIZE_X_INCR = new Box(116, 0, 16, 16);
	private static final RectangleButton BUTTON_X_DECR = new RectangleButton(100, 0, 16, 16, "-", Color.white, Color.black);
	private static final RectangleButton BUTTON_X_INCR = new RectangleButton(116, 0, 16, 16, "+", Color.white, Color.black);
	private static final Box BOX_SIZE_Y_DECR = new Box(100, 16, 16, 16);
	private static final Box BOX_SIZE_Y_INCR = new Box(116, 16, 16, 16);
	private static final RectangleButton BUTTON_Y_DECR = new RectangleButton(100, 16, 16, 16, "-", Color.white, Color.black);
	private static final RectangleButton BUTTON_Y_INCR = new RectangleButton(116, 16, 16, 16, "+", Color.white, Color.black);
	
	private static final double TIME_BEFORE_INCR_BY_MOUSE_DOWN = 0.5;
	
	private Tileset tileset;
	private Tile tab[][];
	private Map map;
	private Point mapDisplayStart = new Point(0,0);
	private Box boxTileset;
	private Box boxMap;
	private Tile currentTile = Tile.GRASS;
	private String fileName = "map1.tile";
	private double currentTimeBeforeIncrease = 0;
	
	public MapEditor() {
		int nb = 0;
		File f;
		
		do{
			++nb;
			f = new File("assets/maps/map" + nb + ".tile");
		}while(f.exists() || f.isDirectory());
		fileName = "map" + nb + ".tile";
		
		tab = new Tile[DEFAULT_SIZE][DEFAULT_SIZE];
		initTab(tab);
		tileset = new Tileset();
		initBoxesAndMap();
	}
	
	public MapEditor(String fileName) {
		load(fileName);
		this.fileName = fileName;
		tileset = new Tileset();
		initBoxesAndMap();
	}
	
	public MapEditor(int x, int y) {
		int nb = 0;
		File f;
		
		do{
			++nb;
			f = new File("assets/maps/map" + nb + ".tile");
		}while(f.exists() || f.isDirectory());
		fileName = "map" + nb + ".tile";
		
		tab = new Tile[x][y];
		initTab(tab);
		tileset = new Tileset();
		initBoxesAndMap();
	}
	
	private void initTab(Tile tab[][]) {
		int i, j;
		for (i = 0; i < tab.length; ++i)
			for (j = 0; j < tab[i].length; ++j)
				tab[i][j] = Tile.GRASS;
	}
	
	private void initBoxesAndMap() {
		map = new Map(tab);
		boxTileset = new Box(POS_TILESET_X, POS_TILESET_Y, NB_COL_TILESET * tileset.getW(), ((tileset.getNbTiles() - 1) / NB_COL_TILESET + 1) * tileset.getH());
		boxMap = new Box(POS_MAP_X, POS_MAP_Y, Math.min(tab.length, MAP_DISPLAY_NB_TILE_X) * tileset.getW(), Math.min(tab[0].length, MAP_DISPLAY_NB_TILE_Y)  * tileset.getH());
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
			else if (collide.AABB_point(BOX_SIZE_X_DECR, input.mouse)){
				resize(tab.length - 1, tab[0].length);
			}
			else if (collide.AABB_point(BOX_SIZE_Y_DECR, input.mouse)){
				resize(tab.length, tab[0].length - 1);
			}
			else if (collide.AABB_point(BOX_SIZE_X_INCR, input.mouse)){
				resize(tab.length + 1, tab[0].length);
			}
			else if (collide.AABB_point(BOX_SIZE_Y_INCR, input.mouse)){
				resize(tab.length, tab[0].length + 1);
			}
		}
		
		if (input.leftButton.down) {
			if (collide.AABB_point(boxMap, input.mouse)){
				putTile();
			}
			else if (collide.AABB_point(BOX_SIZE_X_DECR, input.mouse)){
				if (currentTimeBeforeIncrease >= TIME_BEFORE_INCR_BY_MOUSE_DOWN)
					resize(tab.length - 1, tab[0].length);
				else
					currentTimeBeforeIncrease += dt;
			}
			else if (collide.AABB_point(BOX_SIZE_Y_DECR, input.mouse)){
				if (currentTimeBeforeIncrease >= TIME_BEFORE_INCR_BY_MOUSE_DOWN)
					resize(tab.length, tab[0].length - 1);
				else
					currentTimeBeforeIncrease += dt;
			}
			else if (collide.AABB_point(BOX_SIZE_X_INCR, input.mouse)){
				if (currentTimeBeforeIncrease >= TIME_BEFORE_INCR_BY_MOUSE_DOWN)
					resize(tab.length + 1, tab[0].length);
				else
					currentTimeBeforeIncrease += dt;
			}
			else if (collide.AABB_point(BOX_SIZE_Y_INCR, input.mouse)){
				if (currentTimeBeforeIncrease >= TIME_BEFORE_INCR_BY_MOUSE_DOWN)
					resize(tab.length, tab[0].length + 1);
				else
					currentTimeBeforeIncrease += dt;
			}
		}	
		
		if (input.rightButton.pressed) {
			save();
		}
		
		if (input.leftButton.released) {
			currentTimeBeforeIncrease = 0.;
		}
		
		if (input.fireLeft.pressed) {
			if (mapDisplayStart.x > 0)
				--mapDisplayStart.x;
		}
		else if (input.fireRight.pressed) {
			if (mapDisplayStart.x < tab.length - 1 - MAP_DISPLAY_NB_TILE_X)
				++mapDisplayStart.x;
		}
		else if (input.fireUp.pressed) {
			if (mapDisplayStart.y > 0)
				--mapDisplayStart.y;
		}
		else if (input.fireDown.pressed) {
			if (mapDisplayStart.y < tab[0].length - 1 - MAP_DISPLAY_NB_TILE_Y)
				++mapDisplayStart.y;
		}
		
		if (input.left.pressed) {
			if (currentTile.ordinal() % NB_COL_TILESET > 0)
				currentTile = Tile.values()[currentTile.ordinal() - 1];
		}
		else if (input.right.pressed) {
			if (currentTile.ordinal() % NB_COL_TILESET < NB_COL_TILESET - 1)
				currentTile = Tile.values()[currentTile.ordinal() + 1];
		}
		else if (input.up.pressed) {
			if (currentTile.ordinal() / NB_COL_TILESET > 0)
				currentTile = Tile.values()[currentTile.ordinal() - NB_COL_TILESET];
		}
		else if (input.down.pressed) {
			if (currentTile.ordinal() / NB_COL_TILESET < tileset.getNbTiles() / NB_COL_TILESET - 1)
				currentTile = Tile.values()[currentTile.ordinal() + NB_COL_TILESET];
		}
	}

	private void putTile() {
		int x = (input.mouse.x - POS_MAP_X) / tileset.getW() + mapDisplayStart.x;
		int y = (input.mouse.y - POS_MAP_Y) / tileset.getH() + mapDisplayStart.y;
		
		tab[x][y] = currentTile;
		map = new Map(tab);
	}

	private void selectTile() {
		int x = (input.mouse.x - POS_TILESET_X) / tileset.getW();
		int y = (input.mouse.y - POS_TILESET_Y) / tileset.getH();
		
		currentTile = Tile.values()[y * NB_COL_TILESET + x]; 
	}
	
	public void resize(int x, int y) {
		if (x < 1 || y < 1)
			return;
		
		Tile newMap[][] = new Tile[x][y];
		initTab(newMap);
		
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
		boxMap = new Box(POS_MAP_X, POS_MAP_Y, Math.min(tab.length, MAP_DISPLAY_NB_TILE_X) * tileset.getW(), Math.min(tab[0].length, MAP_DISPLAY_NB_TILE_Y)  * tileset.getH());
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
			FileOutputStream fout = new FileOutputStream(new File("assets/maps/" + fileName));
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
		map.draw(r, POS_MAP_X , POS_MAP_Y, boxMap.width / 16, boxMap.height / 16, mapDisplayStart.x, mapDisplayStart.y);
		tileset.drawTileset(r, POS_TILESET_X, POS_TILESET_Y, NB_COL_TILESET);
		drawSelectedTile(r);
		drawMapArea(r);
		drawButtonIncrDecr(r);
	}
	
	public void drawButtonIncrDecr(Renderer r) {
		Color prevColor = r.getGraphics().getColor();
		
		BUTTON_X_DECR.draw(r);
		BUTTON_X_INCR.draw(r);
		BUTTON_Y_DECR.draw(r);
		BUTTON_Y_INCR.draw(r);
		
		r.getGraphics().setColor(Color.black);
		r.getGraphics().drawString("Width : " + tab.length, 140, 12);
		r.getGraphics().drawString("Height : " + tab[0].length, 140, 28);
		r.getGraphics().drawString("Origin : " + mapDisplayStart.x + "," + mapDisplayStart.y, 200, 12);
		r.getGraphics().setColor(prevColor);
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
	
	public void drawMapArea(Renderer r) {
		Color prevColor = r.getGraphics().getColor();
		Stroke prevStroke = r.getGraphics().getStroke();
		r.getGraphics().setColor(Color.black);
		r.getGraphics().setStroke(new BasicStroke(1));
		r.getGraphics().drawRoundRect(boxMap.x, boxMap.y, MAP_DISPLAY_NB_TILE_X * tileset.getW(), MAP_DISPLAY_NB_TILE_Y * tileset.getH(), 0, 0);
		r.getGraphics().setStroke(prevStroke);
		r.getGraphics().setColor(prevColor);
	}
}
