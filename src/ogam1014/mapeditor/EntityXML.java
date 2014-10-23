package ogam1014.mapeditor;

import java.awt.Point;

import ogam1014.entity.EnemyType;

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
}
