package ogam1014.equipment;

import ogam1014.entity.Player;

public class RangeWeaponItem extends AttributeItem implements IUsableItem {
	
	public RangeWeaponItem(String name) {
		super(name);
	}

	@Override
	public void use(Player player) {
		// TODO Fire projectile and remove ammo from player's inventory
	}
}
