package ogam1014.mapeditor;

import java.awt.Point;

import ogam1014.entity.EnemyType;

public class EntityXML {
	private EnemyType type;
	private Point p;

	public EntityXML(Point p, EnemyType type) {
		this.p = p;
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
}
