package ogam1014.equipment;

import ogam1014.entity.Player;
import ogam1014.entity.Swing;

public class MeleeWeaponItem extends AttributeItem implements IUsableItem {

	public MeleeWeaponItem(String name) {
		super(name);
	}

	@Override
	public void use(Player player) {
		Swing swing = new Swing(player, 1);
		player.spawnSwing(swing);
	}

}
