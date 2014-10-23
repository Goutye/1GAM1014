package ogam1014.mapeditor;

import java.awt.Point;
import java.util.ArrayList;

import ogam1014.Level;
import ogam1014.Tile;
import ogam1014.entity.Enemy;
import ogam1014.entity.EnemyType;
import ogam1014.entity.Entity;
import ogam1014.entity.MobEntity;
import ogam1014.entity.Thief;

public class EntityXML {
	private EnemyType type;
	private Point p;
	private int IDWalkingText = 0;
	private int IDSpeakingText = 0;

	public EntityXML(Point p, EnemyType type) {
		this.p = p;
		this.type = type;
	}
	
	public EntityXML(EnemyType type, Point pos, int idSpeaking, int idWalking) {
		this.p = pos;
		this.IDSpeakingText = idSpeaking;
		this.IDWalkingText = idWalking;
		this.type = type;
	}

	public Point getPos() {
		return p;
	}
	
	public void setPos(Point p) {
		this.p = p;
	}

	public EnemyType getEnemyType() {
		return type;
	}

	public int getIDWalkingText() {
		return IDWalkingText;
	}

	public int getIDSpeakingText() {
		return IDSpeakingText;
	}

	public void setIDWalkingText(int i) {
		IDWalkingText = i;
	}

	public void setIDSpeakingText(int i) {
		IDSpeakingText = i;
	}
	
	public static void convertToEntities(Level level, ArrayList<EntityXML> entitiesXML) {		
		for (EntityXML eXML : entitiesXML) {
			MobEntity e;
			
			switch(eXML.getEnemyType()) {
			case Thief: 
				e = new Thief();
				break;
			default:
				e = new Enemy();
				break;
			}
			
			e.setPosition(new Point(eXML.getPos().x * Tile.SIZE, eXML.getPos().y * Tile.SIZE));
			
			/* TODO eXML.getIDWalkingText
			 * TODO eXML.getIDSpeakingText
			 */
			e.setLevel(level);
			level.addEntity(e);
		}
	}
}
