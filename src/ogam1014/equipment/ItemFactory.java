package ogam1014.equipment;

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
		// TODO Example
		
		switch(descriptor.substring("armor.".length())) {
		case "iron.helmet":
			return new ArmorItem("Iron", ArmorType.HELMET, 0.3f);
		}
		
		return null;
	}

	private static Item makePotion(String descriptor) {
		// TODO Auto-generated method stub
		return null;
	}
}
