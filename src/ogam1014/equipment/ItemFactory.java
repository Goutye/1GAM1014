package ogam1014.equipment;

import ogam1014.attributes.PlayerAttributes.Attr;

public abstract class ItemFactory {
	
	public static Item make(String descriptor) {
		if(descriptor.startsWith("potion.")) {
			return makePotion(descriptor);
		}
		else if(descriptor.startsWith("armor.")) {
			return makeArmor(descriptor);
		}
		else if(descriptor.startsWith("meleeweapon.")) {
			return makeMeleeWeapon(descriptor);
		}
		else if(descriptor.startsWith("rangeweapon.")) {
			return makeRangeWeapon(descriptor);
		}
		else if(descriptor.startsWith("shield.")) {
			return makeShield(descriptor);
		}
		else if(descriptor.startsWith("spell.")) {
			return makeSpell(descriptor);
		}
		else {
			return null;
		}
	}

	private static Item makeSpell(String descriptor) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Item makeShield(String descriptor) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Item makeRangeWeapon(String descriptor) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Item makeMeleeWeapon(String descriptor) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Item makeArmor(String descriptor) {
		String name = descriptor.substring("armor.".length());
		
		// Example
		if(name == "helmet.iron") {
			ArmorItem helmet = new ArmorItem("Iron", ArmorType.HELMET, 0.3f);
			helmet.setRelativeModifier(Attr.SPEED, 0.9f); // slows you down whan worn
			return helmet;
		}
		
		return null;
	}

	private static Item makePotion(String descriptor) {
		// TODO Auto-generated method stub
		return null;
	}
}