package ogam1014.equipment;

import ogam1014.attributes.PlayerAttributes.Attr;

public abstract class ItemFactory {
	
	public static Item make(String descriptor) {
		Item item = null;
		if(descriptor.startsWith("potion.")) {
			item = makePotion(descriptor);
		}
		else if(descriptor.startsWith("armor.")) {
			item = makeArmor(descriptor);
		}
		else if(descriptor.startsWith("meleeweapon.")) {
			item = makeMeleeWeapon(descriptor);
		}
		else if(descriptor.startsWith("rangeweapon.")) {
			item = makeRangeWeapon(descriptor);
		}
		else if(descriptor.startsWith("shield.")) {
			item = makeShield(descriptor);
		}
		else if(descriptor.startsWith("spell.")) {
			item = makeSpell(descriptor);
		}

		if (item == null)
			throw new RuntimeException("Item " + descriptor + " unknown");

		item.setDescriptor(descriptor);
		return item;
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
		RangeWeaponItem rw = null;
		switch (descriptor) {
		case "rangeweapon.simplegun":
			rw = new RangeWeaponItem("Gun");
			rw.setSpreadAngle(Math.PI / 36);
			rw.setCooldown(0.1);
			rw.setBulletType(BulletType.SMALL);
			break;
		case "rangeweapon.shotgun":
			rw = new RangeWeaponItem("Shotgun");
			rw.setSpreadAngle(Math.PI / 8);
			rw.setNumBullets(8);
			rw.setCooldown(0.8);
			rw.setBulletType(BulletType.SMALL);
			break;
		}
		return rw;
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
