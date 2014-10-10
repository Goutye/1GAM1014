package ogam1014.equipment;

import ogam1014.entity.Player;

public class MeleeWeaponItem extends Item implements IUsableItem {
	
	private int attackDamage;
	
	public MeleeWeaponItem(String name, int attackDamage) {
		super(name);
		this.attackDamage = attackDamage;
	}

	@Override
	public void use(Player player) {
		// TODO Hurt entities according to attackDamage
	}
}
