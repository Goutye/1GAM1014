package ogam1014.mapeditor;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import ogam1014.Level;
import ogam1014.Tile;
import ogam1014.entity.Enemy;
import ogam1014.entity.EnemyType;
import ogam1014.entity.Thief;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
			Enemy e;
			
			switch(eXML.getEnemyType()) {
			case Thief: 
				e = new Thief();
				break;
			default:
				e = new Enemy();
				break;
			}
			
			e.setPosition(new Point(eXML.getPos().x * Tile.SIZE, eXML.getPos().y * Tile.SIZE));
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			Document doc;

			if (!(eXML.IDSpeakingText == 0 && eXML.IDWalkingText == 0)) {
				try {
					DocumentBuilder builder = factory.newDocumentBuilder();
					doc = builder.parse(new File("assets/texts.xml"));
				
					NodeList list = doc.getElementsByTagName("text");
					
					for (int i = 0; i < list.getLength(); ++i) {
						Node n = list.item(i);
						int id = Integer.parseInt(n.getAttributes().getNamedItem("id").getNodeValue());
						
						if ( id == eXML.IDSpeakingText )
							e.setSpeakingText(n.getTextContent());
						else if (id == eXML.IDWalkingText)
							e.setWalkingText(n.getTextContent());
					}
				
				} catch (ParserConfigurationException e1) {
					e1.printStackTrace();
				} catch (SAXException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					System.out.println("XML Problem! Please redownload the full-game.");
					e1.printStackTrace();
				}
			}

			e.setLevel(level);
			level.addEntity(e);
		}
	}
}
