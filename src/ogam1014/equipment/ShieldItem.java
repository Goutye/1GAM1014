package ogam1014.equipment;

import ogam1014.entity.Player;

public class ShieldItem extends AttributeItem implements IUsableItem {
	
	private float protection;
	
	public ShieldItem(String name) {
		super(name);
		this.protection = protection;
	}

	@Override
	public void use(Player player) {
		// TODO reduce damage when used
		// probably won't be used as is, depending on the state of the active slot
		// --> use getProtection from player in player's update
	}
	
	public float getProtection() {
		return protection;
	}
}
