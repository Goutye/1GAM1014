package ogam1014.equipment;

import ogam1014.entity.Player;

public class RangeWeaponItem extends Item implements IUsableItem {
	
	private int attackDamage;
	
	public RangeWeaponItem(String name, int attackDamage) {
		super(name);
		this.attackDamage = attackDamage;
	}

	@Override
	public void use(Player player) {
		// TODO Fire projectile according to attackDamage and remove ammo from player's inventory
	}
}
