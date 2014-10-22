package ogam1014.mapeditor;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import ogam1014.mapeditor.EntityXML;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ConverterEntitiesToXML {
	
	public static void convert(String name, ArrayList<EntityXML> entities)  {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Document doc;
		System.out.println(name);
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.newDocument();
		
			Element n = doc.createElement("entities");
			doc.appendChild(n);
			
			
			for (EntityXML e : entities) {
				Element child = doc.createElement("entity");
				
				Element type = doc.createElement("type");
				type.appendChild(doc.createTextNode(e.getEnemyType() + ""));
				
				Element position = doc.createElement("position");
				Attr attrX = doc.createAttribute("x");
				Attr attrY = doc.createAttribute("y");
				attrX.setValue(e.getPos().x + "");
				attrY.setValue(e.getPos().y + "");
				position.setAttributeNode(attrX);
				position.setAttributeNode(attrY);
				
				Element idWalkingText = doc.createElement("idWalking");
				Element idSpeakingText = doc.createElement("idSpeaking");
				idWalkingText.appendChild(doc.createTextNode(e.getIDWalkingText() + ""));
				idSpeakingText.appendChild(doc.createTextNode(e.getIDSpeakingText() + ""));
	
				child.appendChild(type);
				child.appendChild(position);
				child.appendChild(idSpeakingText);
				child.appendChild(idWalkingText);
				
				n.appendChild(child);
			}
		
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer;
			
			transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("assets/maps/" + name + ".entities"));
		
			transformer.transform(source, result);
		} catch (TransformerConfigurationException e1) {
			e1.printStackTrace();
		} catch (TransformerException e1) {
			e1.printStackTrace();
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		}
		
		System.out.println("Entities saved!");
	}
}
