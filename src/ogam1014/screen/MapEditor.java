package ogam1014.screen;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Stroke;
import java.io.File;
import java.util.ArrayList;

import ogam1014.mapeditor.EntityXML;
import ogam1014.mapeditor.Mode;
import ogam1014.Map;
import ogam1014.Tile;
import ogam1014.collide.Box;
import ogam1014.collide.Collide;
import ogam1014.entity.EnemyType;
import ogam1014.graphics.Renderer;
import ogam1014.graphics.Tileset;
import ogam1014.ui.RectangleButton;

public class MapEditor extends Screen{
	private static final int DEFAULT_SIZE = 100;
	private static final int POS_MAP_X = 0;
	private static final int POS_MAP_Y = Tile.SIZE * 4 + ogam1014.Engine.HEIGHT % Tile.SIZE;
	private static final int POS_TILESET_X = 0;
	private static final int POS_TILESET_Y = 0;
	private static final int MAP_DISPLAY_NB_TILE_X = ogam1014.Engine.WIDTH / Tile.SIZE;
	private static final int MAP_DISPLAY_NB_TILE_Y = (ogam1014.Engine.HEIGHT - POS_MAP_Y) / Tile.SIZE;
	private static final int NB_COL_ENTITY_AREA = 12;
	
	private static final Box BOX_MENU = new Box(Tileset.nbDisplayTileW * Tile.SIZE, 0, 200, 320);
	private static final Box BOX_SIZE_X_DECR = new Box(BOX_MENU.x, BOX_MENU.y, 8, 8);
	private static final Box BOX_SIZE_X_INCR = new Box(BOX_MENU.x + 8, BOX_MENU.y, 8, 8);
	private static final RectangleButton BUTTON_X_DECR = new RectangleButton(BOX_MENU.x, BOX_MENU.y, 8, 8, "-", Color.white, Color.black,Color.gray, 1);
	private static final RectangleButton BUTTON_X_INCR = new RectangleButton(BOX_MENU.x + 8, BOX_MENU.y, 8, 8, "+", Color.white, Color.black,Color.gray, 1);
	private static final Box BOX_SIZE_Y_DECR = new Box(BOX_MENU.x, 8, 8, 8);
	private static final Box BOX_SIZE_Y_INCR = new Box(BOX_MENU.x + 8, 8, 8, 8);
	private static final RectangleButton BUTTON_Y_DECR = new RectangleButton(BOX_MENU.x, 8, 8, 8, "-", Color.white, Color.black,Color.gray, 1);
	private static final RectangleButton BUTTON_Y_INCR = new RectangleButton(BOX_MENU.x + 8, 8, 8, 8, "+", Color.white, Color.black,Color.gray, 1);
	private static final Box BOX_MODE = new Box(BOX_MENU.x + 8, 24, 32, 16);
	private static final RectangleButton BUTTON_MODE = new RectangleButton(BOX_MENU.x + 8, 24, 32, 16, "Map", Color.white, Color.black,Color.gray, 1);
	
	private static final double TIME_BEFORE_INCR_BY_MOUSE_DOWN = 0.5;
	
	private Tileset tileset;
	private Tile tab[][];
	private ArrayList<EntityXML> entities = new ArrayList<EntityXML>();
	private Map map;
	private Point mapDisplayStart = new Point(0,0);
	private Point tilesetDisplayStart = new Point(0,0);
	private Box boxTileset;
	private Box boxMap;
	private Tile currentTile = Tile.GRASS;
	private EnemyType currentEntity = EnemyType.Enemy;
	private boolean inSelectionOfEntity = false;
	private String fileName = "map1.tile";
	private double currentTimeBeforeIncrease = 0;
	private boolean mapCurrentlyClicked = false;
	private boolean tilesetCurrentlyClicked = false;
	private Point mapClickPosition = new Point(0,0);
	private Point tilesetClickPosition = new Point(0,0);
	private Mode mode = Mode.Map;
	private EntityXML currentSelectedEntityXML = null;
	
	public MapEditor() {
		int nb = 0;
		File f;
		
		do {
			++nb;
			f = new File("assets/maps/map" + nb + ".tile");
		} while(f.exists() || f.isDirectory());
		
		fileName = "map" + nb + ".tile";
		
		tab = new Tile[DEFAULT_SIZE][DEFAULT_SIZE];
		initTab(tab);
		tileset = new Tileset();
		map = new Map(tab);
		initBoxes();
	}
	
	public MapEditor(String fileName) {
		map = new Map(fileName);
		tab = map.getTab();
		this.fileName = fileName;
		tileset = new Tileset();
		initBoxes();
	}
	
	public MapEditor(int x, int y) {
		int nb = 0;
		File f;
		
		do {
			++nb;
			f = new File("assets/maps/map" + nb + ".tile");
		} while(f.exists() || f.isDirectory());
		
		fileName = "map" + nb + ".tile";
		
		tab = new Tile[x][y];
		initTab(tab);
		tileset = new Tileset();
		map = new Map(tab);
		initBoxes();
	}
	
	private void initTab(Tile tab[][]) {
		int i, j;
		for (i = 0; i < tab.length; ++i)
			for (j = 0; j < tab[i].length; ++j)
				tab[i][j] = Tile.GRASS;
	}
	
	private void initBoxes() {
		boxTileset = new Box(POS_TILESET_X, POS_TILESET_Y, Tileset.nbDisplayTileW * Tile.SIZE, Tileset.nbDisplayTileH * Tile.SIZE);
		boxMap = new Box(POS_MAP_X, POS_MAP_Y, Math.min(tab.length, MAP_DISPLAY_NB_TILE_X) * Tile.SIZE, Math.min(tab[0].length, MAP_DISPLAY_NB_TILE_Y)  * Tile.SIZE);
	}
	
	@Override
	public void update(double dt) {
		if (input.leftButton.pressed) {
			if (Collide.AABB_point(boxTileset, input.mouse)) {
				if (mode == Mode.Map)
					selectTile();
				else 
					selectEntity();
			}
			else if (Collide.AABB_point(BOX_MODE, input.mouse)) {
				mode = mode == Mode.Map ? Mode.Entity : Mode.Map;
				BUTTON_MODE.setText(mode.toString());
			}
			else if (mode == Mode.Entity) {
				if (Collide.AABB_point(boxMap, input.mouse)) {
					if (inSelectionOfEntity)
						putEntity();
					else
						displayInfoEntity();
				}
				else if (Collide.AABB_point(BOX_SIZE_X_DECR, input.mouse)) {
					currentSelectedEntityXML.setIDWalkingText(currentSelectedEntityXML.getIDWalkingText() - 1);
				}
				else if (Collide.AABB_point(BOX_SIZE_Y_DECR, input.mouse)) {
					currentSelectedEntityXML.setIDSpeakingText(currentSelectedEntityXML.getIDSpeakingText() - 1);
				}
				else if (Collide.AABB_point(BOX_SIZE_X_INCR, input.mouse)) {
					currentSelectedEntityXML.setIDWalkingText(currentSelectedEntityXML.getIDWalkingText() + 1);
				}
				else if (Collide.AABB_point(BOX_SIZE_Y_INCR, input.mouse)) {
					currentSelectedEntityXML.setIDSpeakingText(currentSelectedEntityXML.getIDSpeakingText() + 1);
				}
			}
			else if (mode == Mode.Map) {
				if (Collide.AABB_point(BOX_SIZE_X_DECR, input.mouse)) {
					resize(tab.length - 1, tab[0].length);
				}
				else if (Collide.AABB_point(BOX_SIZE_Y_DECR, input.mouse)) {
					resize(tab.length, tab[0].length - 1);
				}
				else if (Collide.AABB_point(BOX_SIZE_X_INCR, input.mouse)) {
					resize(tab.length + 1, tab[0].length);
				}
				else if (Collide.AABB_point(BOX_SIZE_Y_INCR, input.mouse)) {
					resize(tab.length, tab[0].length + 1);
				}
			}
		}
		
		if (input.leftButton.down) {
			if (Collide.AABB_point(boxMap, input.mouse)) {
				if (mode == Mode.Map)
					putTile();
			}
			else if (mode == Mode.Map) {
				if (Collide.AABB_point(BOX_SIZE_X_DECR, input.mouse)) {
					if (currentTimeBeforeIncrease >= TIME_BEFORE_INCR_BY_MOUSE_DOWN)
						resize(tab.length - 1, tab[0].length);
					else
						currentTimeBeforeIncrease += dt;
				}
				else if (Collide.AABB_point(BOX_SIZE_Y_DECR, input.mouse)) {
					if (currentTimeBeforeIncrease >= TIME_BEFORE_INCR_BY_MOUSE_DOWN)
						resize(tab.length, tab[0].length - 1);
					else
						currentTimeBeforeIncrease += dt;
				}
				else if (Collide.AABB_point(BOX_SIZE_X_INCR, input.mouse)) {
					if (currentTimeBeforeIncrease >= TIME_BEFORE_INCR_BY_MOUSE_DOWN)
						resize(tab.length + 1, tab[0].length);
					else
						currentTimeBeforeIncrease += dt;
				}
				else if (Collide.AABB_point(BOX_SIZE_Y_INCR, input.mouse)) {
					if (currentTimeBeforeIncrease >= TIME_BEFORE_INCR_BY_MOUSE_DOWN)
						resize(tab.length, tab[0].length + 1);
					else
						currentTimeBeforeIncrease += dt;
				}
			}
		}	
		
		if (input.rightButton.pressed) {			
			if (Collide.AABB_point(boxMap, input.mouse)) {
				mapCurrentlyClicked = true;
				mapClickPosition = new Point(input.mouse);
				
				if (mode == Mode.Entity)
					removeEntity();
			}
			else if(Collide.AABB_point(boxTileset, input.mouse) && mode == Mode.Map) {
				tilesetCurrentlyClicked = true;
				tilesetClickPosition = new Point(input.mouse);
			}
			else {
				map.save(fileName);
			}
		}
		
		if (input.rightButton.down) {
			if (mapCurrentlyClicked) {
				if (input.mouse.x - mapClickPosition.x >= Tile.SIZE) {
					if (mapDisplayStart.x > 0) {
						--mapDisplayStart.x;
						mapClickPosition.x += Tile.SIZE;
					}
				}
				else if (mapClickPosition.x - input.mouse.x >= Tile.SIZE) {
					if (mapDisplayStart.x < tab.length - 1 - MAP_DISPLAY_NB_TILE_X) {
						++mapDisplayStart.x;
						mapClickPosition.x -= Tile.SIZE;
					}
				}
				
				if (input.mouse.y - mapClickPosition.y >= Tile.SIZE) {
					if (mapDisplayStart.y > 0) {
						--mapDisplayStart.y;
						mapClickPosition.y += Tile.SIZE;
					}
				}
				else if (mapClickPosition.y - input.mouse.y >= Tile.SIZE) {
					if (mapDisplayStart.y <  tab[0].length - 1 - MAP_DISPLAY_NB_TILE_Y) {
						++mapDisplayStart.y;
						mapClickPosition.y -= Tile.SIZE;
					}
				}
			}
			else if (tilesetCurrentlyClicked && mode == Mode.Map) {
				if (input.mouse.x - tilesetClickPosition.x >= Tile.SIZE) {
					if (tilesetDisplayStart.x > 0) {
						--tilesetDisplayStart.x;
						tilesetClickPosition.x += Tile.SIZE;
					}
				}
				else if (tilesetClickPosition.x - input.mouse.x >= Tile.SIZE) {
					if (tilesetDisplayStart.x < tileset.getNbTileW() - Tileset.nbDisplayTileW) {
						++tilesetDisplayStart.x;
						tilesetClickPosition.x -= Tile.SIZE;
					}
				}
				
				if (input.mouse.y - tilesetClickPosition.y >= Tile.SIZE) {
					if (tilesetDisplayStart.y > 0) {
						--tilesetDisplayStart.y;
						tilesetClickPosition.y += Tile.SIZE;
					}
				}
				else if (tilesetClickPosition.y - input.mouse.y >= Tile.SIZE) {
					if (tilesetDisplayStart.y < tileset.getNbTileH() - Tileset.nbDisplayTileH) {
						++tilesetDisplayStart.y;
						tilesetClickPosition.y -= Tile.SIZE;
					}
				}
			}
		}
		
		if (input.rightButton.released) {
			mapCurrentlyClicked = false;
			tilesetCurrentlyClicked = false;
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
		
		if (mode == Mode.Map) {
			if (input.left.pressed) {
				
				if (currentTile.ordinal() % tileset.getNbTileW() > 0) {
					currentTile = Tile.values()[currentTile.ordinal() - 1];
					
					if (currentTile.ordinal() % tileset.getNbTileW() < tilesetDisplayStart.x)
						--tilesetDisplayStart.x;
				}
	
			}
			else if (input.right.pressed) {
				if (currentTile.ordinal() % tileset.getNbTileW() < tileset.getNbTileW() - 1) {
					currentTile = Tile.values()[currentTile.ordinal() + 1];
					
					if (currentTile.ordinal() % tileset.getNbTileW() >= tilesetDisplayStart.x + Tileset.nbDisplayTileW)
						++tilesetDisplayStart.x;
				}
			}
			else if (input.up.pressed) {
				if (currentTile.ordinal() / tileset.getNbTileW() > 0) {
					currentTile = Tile.values()[currentTile.ordinal() - tileset.getNbTileW()];
					
					if (currentTile.ordinal() / tileset.getNbTileW() < tilesetDisplayStart.y)
						--tilesetDisplayStart.y;
				}
			}
			else if (input.down.pressed) {
				if (currentTile.ordinal() / tileset.getNbTileW() < tileset.getNbTiles() / tileset.getNbTileW() - 1) {
					currentTile = Tile.values()[currentTile.ordinal() + tileset.getNbTileW()];
					
					if (currentTile.ordinal() / tileset.getNbTileW() >= tilesetDisplayStart.y + Tileset.nbDisplayTileH)
						++tilesetDisplayStart.y;
				}
			}
		}
		
		Point p = input.mouse;
		BUTTON_X_DECR.update(p);
		BUTTON_X_INCR.update(p);
		BUTTON_Y_DECR.update(p);
		BUTTON_Y_INCR.update(p);
		BUTTON_MODE.update(p);
	}

	private void displayInfoEntity() {
		int x = (input.mouse.x - POS_MAP_X) / Tile.SIZE + mapDisplayStart.x;
		int y = (input.mouse.y - POS_MAP_Y) / Tile.SIZE + mapDisplayStart.y;
		Point p = new Point(x, y);
		
		for (EntityXML e : entities) {
			if (e.getPos().equals(p)) {
				currentSelectedEntityXML = e;
				break;
			}
		}
		
		System.out.println(currentSelectedEntityXML);
		
		// TODO Display info of the entity clicked
		// TODO Une var pour stocker l'entity où on doit afficher l'info ?
		// TODO Draw function for that.
	}

	private void selectEntity() {
		int x = (input.mouse.x - POS_TILESET_X) / Tile.SIZE + tilesetDisplayStart.x;
		int y = (input.mouse.y - POS_TILESET_Y) / Tile.SIZE + tilesetDisplayStart.y;
		
		if ( y * NB_COL_ENTITY_AREA + x < EnemyType.values().length) {
			if (currentEntity == EnemyType.values()[y * NB_COL_ENTITY_AREA + x]) {
				inSelectionOfEntity = !inSelectionOfEntity;
				if (inSelectionOfEntity)
					currentSelectedEntityXML = null;
			}
			else {
				currentEntity = EnemyType.values()[y * NB_COL_ENTITY_AREA + x];
				inSelectionOfEntity = true;
				currentSelectedEntityXML = null;
			}
		}
	}

	private void putEntity() {
		int x = (input.mouse.x - POS_MAP_X) / Tile.SIZE + mapDisplayStart.x;
		int y = (input.mouse.y - POS_MAP_Y) / Tile.SIZE + mapDisplayStart.y;
		Point p = new Point(x,y);
		
		for(EntityXML e : entities) {
			if (e.getPos().equals(p)) {
				return;
			}
		}
		
		entities.add(new EntityXML(new Point(x,y), currentEntity));
	}
	
	private void removeEntity() {
		int x = (input.mouse.x - POS_MAP_X) / Tile.SIZE + mapDisplayStart.x;
		int y = (input.mouse.y - POS_MAP_Y) / Tile.SIZE + mapDisplayStart.y;
		Point p = new Point(x,y);
		
		for(EntityXML e : entities) {
			if (e.getPos().equals(p)) {
				entities.remove(e);
				return;
			}
		}
	}

	private void putTile() {
		int x = (input.mouse.x - POS_MAP_X) / Tile.SIZE + mapDisplayStart.x;
		int y = (input.mouse.y - POS_MAP_Y) / Tile.SIZE + mapDisplayStart.y;
		
		tab[x][y] = currentTile;
		map.putTile(currentTile, x, y);
	}

	private void selectTile() {
		int x = (input.mouse.x - POS_TILESET_X) / Tile.SIZE + tilesetDisplayStart.x;
		int y = (input.mouse.y - POS_TILESET_Y) / Tile.SIZE + tilesetDisplayStart.y;
		
		currentTile = Tile.values()[y * tileset.getNbTileW() + x]; 
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
		boxMap = new Box(POS_MAP_X, POS_MAP_Y, Math.min(tab.length, MAP_DISPLAY_NB_TILE_X) * Tile.SIZE, Math.min(tab[0].length, MAP_DISPLAY_NB_TILE_Y)  * Tile.SIZE);
	}
	
	private void copy(Tile m1[][], Tile m2[][], int x, int y) {
		int i, j;
		
		for (i = 0; i < x; ++i) {
			for (j = 0; j < y; ++j) {
				m1[i][j] = m2[i][j];
			}
		}
	}
	
	@Override
	public void draw(Renderer r) {
		map.draw(r, POS_MAP_X , POS_MAP_Y, boxMap.width / 16, boxMap.height / 16, mapDisplayStart.x, mapDisplayStart.y);
		
		BUTTON_MODE.drawUpdate(r);
		drawMapArea(r);
		
		if (mode == Mode.Map) {
			tileset.drawTileset(r, tilesetDisplayStart.x, tilesetDisplayStart.y, POS_TILESET_X, POS_TILESET_Y);
			drawSelectedTile(r);
			drawButtonIncrDecr(r);
		}
		else {
			drawEntityXMLInfo(r);
			drawEntitiesType(r);
			drawSelectedEntity(r);
			drawEntitiesOnMap(r);
			if (currentSelectedEntityXML != null) {
				BUTTON_X_DECR.drawUpdate(r);
				BUTTON_X_INCR.drawUpdate(r);
				BUTTON_Y_DECR.drawUpdate(r);
				BUTTON_Y_INCR.drawUpdate(r);
			}
		}
		
		
	}
	
	public void drawButtonIncrDecr(Renderer r) {
		Color prevColor = r.getGraphics().getColor();
		
		BUTTON_X_DECR.drawUpdate(r);
		BUTTON_X_INCR.drawUpdate(r);
		BUTTON_Y_DECR.drawUpdate(r);
		BUTTON_Y_INCR.drawUpdate(r);
		
		r.setColor(Color.black);
		r.setFontSize(10);
		r.drawText("Width : " + tab.length, BOX_MENU.x + 40, 12);
		r.drawText("Height : " + tab[0].length, BOX_MENU.x + 40, 28);
		r.drawText("Origin : " + mapDisplayStart.x + "," + mapDisplayStart.y, BOX_MENU.x + 100, 12);
		r.getGraphics().setColor(prevColor);
	}
	
	public void drawSelectedTile(Renderer r) {
		int x = (currentTile.ordinal() % tileset.getNbTileW() - tilesetDisplayStart.x) * Tile.SIZE + POS_TILESET_X ;
		int y = (currentTile.ordinal() / tileset.getNbTileW() - tilesetDisplayStart.y) * Tile.SIZE + POS_TILESET_Y;
		Color prevColor = r.getGraphics().getColor();
		Stroke prevStroke = r.getGraphics().getStroke();
		r.getGraphics().setColor(Color.red);
		r.getGraphics().setStroke(new BasicStroke(1));
		r.getGraphics().drawRoundRect(x, y, Tile.SIZE, Tile.SIZE, 0, 0);
		r.getGraphics().setStroke(prevStroke);
		r.getGraphics().setColor(prevColor);
	}
	
	public void drawMapArea(Renderer r) {
		Color prevColor = r.getGraphics().getColor();
		Stroke prevStroke = r.getGraphics().getStroke();
		r.getGraphics().setColor(Color.black);
		r.getGraphics().setStroke(new BasicStroke(1));
		r.getGraphics().drawRoundRect(boxMap.x, boxMap.y, MAP_DISPLAY_NB_TILE_X * Tile.SIZE - 1, MAP_DISPLAY_NB_TILE_Y * Tile.SIZE - 1, 0, 0);
		r.getGraphics().setStroke(prevStroke);
		r.getGraphics().setColor(prevColor);
	}
	
	public void drawEntitiesType(Renderer r) {
		Graphics g = r.getGraphics();
		
		for (int i = 0; i < EnemyType.values().length; ++i) {
			g.setColor(new Color(0,0,0, 0.75f));
			g.fillRect((i % NB_COL_ENTITY_AREA) * Tile.SIZE, i / NB_COL_ENTITY_AREA * Tile.SIZE, Tile.SIZE, Tile.SIZE);
			g.setColor(new Color(255,255,255));
			r.drawCenteredText(EnemyType.values()[i].toString().substring(0, 2), (i % NB_COL_ENTITY_AREA) * Tile.SIZE, i / NB_COL_ENTITY_AREA * Tile.SIZE + 6, Tile.SIZE);
		}
	}
	
	public void drawSelectedEntity(Renderer r) {
		if (!inSelectionOfEntity)
			return;
		
		int x = (currentEntity.ordinal() % NB_COL_ENTITY_AREA) * Tile.SIZE + POS_TILESET_X ;
		int y = (currentEntity.ordinal() / NB_COL_ENTITY_AREA) * Tile.SIZE + POS_TILESET_Y;
		Color prevColor = r.getGraphics().getColor();
		Stroke prevStroke = r.getGraphics().getStroke();
		r.getGraphics().setColor(Color.red);
		r.getGraphics().setStroke(new BasicStroke(1));
		r.getGraphics().drawRoundRect(x, y, Tile.SIZE, Tile.SIZE, 0, 0);
		r.getGraphics().setStroke(prevStroke);
		r.getGraphics().setColor(prevColor);
	}
	
	public void drawEntitiesOnMap(Renderer r) {
		Graphics g = r.getGraphics();
		
		for (EntityXML e : entities) {
			if (e.getPos().x >= mapDisplayStart.x && e.getPos().x < mapDisplayStart.x + MAP_DISPLAY_NB_TILE_X
				&& e.getPos().y >= mapDisplayStart.y && e.getPos().y < mapDisplayStart.y + MAP_DISPLAY_NB_TILE_Y) {
				
				int x = e.getPos().x - mapDisplayStart.x;
				int y = e.getPos().y - mapDisplayStart.y;
				
				g.setColor(new Color(0,0,0, 0.75f));
				g.fillRect(boxMap.x + x * Tile.SIZE,boxMap.y +  y * Tile.SIZE, Tile.SIZE, Tile.SIZE);
				g.setColor(new Color(255,255,255));
				r.drawCenteredText(e.getEnemyType().toString().substring(0, 2), boxMap.x + x * Tile.SIZE, boxMap.y + y * Tile.SIZE + 6, Tile.SIZE);
			}
		}
	}
	
	public void drawEntityXMLInfo(Renderer r) {
		if (currentSelectedEntityXML == null)
			return;
		
		EntityXML e = currentSelectedEntityXML;
		r.setColor(Color.black);
		r.drawText("Position:\t" + e.getPos().x + "," + e.getPos().y, BOX_MENU.x+32, BOX_MENU.y+24);
		r.drawText("Type:\t" + e.getEnemyType(), BOX_MENU.x+32, BOX_MENU.y+32);
		r.drawText("ID:\t" + e.getIDWalkingText() + " [walking text (0 = No text)]", BOX_MENU.x+32, BOX_MENU.y+40);
		r.drawText("ID:\t" + e.getIDSpeakingText() + " [text (When we speak to him)]", BOX_MENU.x+32, BOX_MENU.y+48);
	}
}
