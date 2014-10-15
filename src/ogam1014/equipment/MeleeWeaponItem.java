package ogam1014.equipment;

import ogam1014.entity.Player;

public class MeleeWeaponItem extends AttributeItem implements IUsableItem {
	
	public MeleeWeaponItem(String name) {
		super(name);
	}

	@Override
	public void use(Player player) {
		// TODO Hurt entities according to attackDamage
	}
}
