package ogam1014.equipment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import ogam1014.attributes.PlayerAttributes.Attr;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

final public class ItemFactory {

	static private HashMap<String, Node> nodesByDescriptor = new HashMap<>();
	static {
		reload();
	}

	public static Item make(String descriptor) {
		Item item = null;
		Node node = nodesByDescriptor.get(descriptor);
		
		if (node == null)
			throw new RuntimeException("Item " + descriptor + " unknown");

		if(descriptor.startsWith("potion.")) {
			item = makePotion(node);
		}
		else if(descriptor.startsWith("armor.")) {
			item = makeArmor(node);
		}
		else if(descriptor.startsWith("meleeweapon.")) {
			item = makeMeleeWeapon(node);
		}
		else if(descriptor.startsWith("rangeweapon.")) {
			item = makeRangeWeapon(node);
		}
		else if(descriptor.startsWith("shield.")) {
			item = makeShield(node);
		}
		else if (descriptor.startsWith("ammopack.")) {
			item = makeAmmoPack(node);
		}

		if (item instanceof AttributeItem) {
			AttributeItem attrItem = (AttributeItem) item;
			for (Node c : childrenOf(node)) {
				if (c.getNodeName().equals("relativeModifier")) {
					String attrStr = getTextElement(c, "attribute", "").toUpperCase();
					Attr attr = Attr.valueOf(attrStr);
					float value = getFloatElement(c, "value", 0);
					attrItem.setRelativeModifier(attr, value);
				}
				if (c.getNodeName().equals("rawModifier")) {
					String attrStr = getTextElement(c, "attribute", "").toUpperCase();
					Attr attr = Attr.valueOf(attrStr);
					float value = getFloatElement(c, "value", 0);
					attrItem.setRawModifier(attr, value);
				}
			}
		}

		item.setDescriptor(descriptor);
		return item;
	}

	private static Item makeAmmoPack(Node node) {
		AmmoPackItem ap = new AmmoPackItem(getAttribute(node, "display"));
		ap.setAmount(getIntElement(node, "amount", 0));
		ap.setBulletType(BulletType.valueOf(getTextElement(node, "bulletType", "small").toUpperCase()));
		return ap;
	}

	private static Item makeShield(Node node) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Item makeRangeWeapon(Node node) {
		RangeWeaponItem rw = new RangeWeaponItem(getAttribute(node, "display"));
		float spread = getFloatElement(node, "spread", 0);
		rw.setSpreadAngle(spread == 0 ? 0 : Math.PI / spread);
		rw.setCooldown(getFloatElement(node, "cooldown", 0));
		rw.setBulletType(BulletType.valueOf(getTextElement(node, "bulletType", "small").toUpperCase()));
		rw.setNumBullets(getIntElement(node, "numBullets", 1));
		return rw;
	}

	private static Item makeMeleeWeapon(Node node) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Item makeArmor(Node node) {
		String display = getAttribute(node, "display");
		float protect = getFloatElement(node, "protection", 0);
		ArmorType type = ArmorType.valueOf(node.getParentNode().getNodeName());
		ArmorItem armor = new ArmorItem(display, type, protect);
		return armor;
	}

	private static Item makePotion(Node node) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void reload() {
		nodesByDescriptor = new HashMap<>();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse("assets/items.xml");
			NodeList items = document.getElementsByTagName("item");
			for (int i = 0; i < items.getLength(); i++) {
				Node item = items.item(i);
				String descriptor = getDescriptor(item);
				nodesByDescriptor.put(descriptor, item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getDescriptor(Node item) {
		String name = getAttribute(item, "name");
		String category = "";
		Node parent = item.getParentNode();
		do {
			category = parent.getNodeName() + '.' + category;
			parent = parent.getParentNode();
		} while (parent.getNodeName() != "categories");
		return category + name;
	}

	private static String getAttribute(Node node, String str) {
		NamedNodeMap attrs = node.getAttributes();
		return attrs.getNamedItem(str).getTextContent();
	}

	private static List<Node> childrenOf(Node node) {
		NodeList children = node.getChildNodes();
		List<Node> list = new ArrayList<Node>(children.getLength());
		for (int i = 0; i < children.getLength(); i++) {
			list.add(children.item(i));
		}
		return list;
	}
	
	private static Node getElement(Node node, String str) {
		for (Node child : childrenOf(node)) {
			if (child.getNodeName().equals(str)) {
				return child;
			}
		}
		return null;
	}

	private static String getTextElement(Node node, String str, String def) {
		Node n = getElement(node, str);
		return n != null ? n.getTextContent() : def;
	}

	private static int getIntElement(Node node, String str, int def) {
		String value = getTextElement(node, str, "" + def);
		return Integer.parseInt(value);
	}

	private static float getFloatElement(Node node, String str, float def) {
		String value = getTextElement(node, str, "" + def);
		return Float.parseFloat(value);
	}

}
