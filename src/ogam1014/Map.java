package ogam1014;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import ogam1014.graphics.Renderer;
import ogam1014.graphics.Tileset;

public class Map {
	private Tileset tileset;
	private Tile map[][];
	
	public Map(String fileName){
		tileset = new Tileset();
		load(fileName);
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
	
	public void save(String fileName) {
		try{
			FileOutputStream fout = new FileOutputStream(new File("assets/maps/" + fileName));
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(map);
			oos.close();
			System.out.println("Map saved!");
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void load(String fileName) {
		try{
			FileInputStream fint = new FileInputStream("assets/maps/" + fileName);
			ObjectInputStream ois = new ObjectInputStream(fint);
			map = (Tile[][]) ois.readObject();
			ois.close();
			System.out.println("Map loaded!");
		}catch(IOException e){
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	
	public Tile[][] getTab(){
		return map;
	}
}
