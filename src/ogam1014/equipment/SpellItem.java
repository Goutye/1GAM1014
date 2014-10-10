package ogam1014.equipment;

import ogam1014.entity.Player;

/**
 * Behavior non type-dependent --> needs a class per magic spell.
 */
public abstract class SpellItem extends Item implements IUsableItem, IUpdatableItem {
	
	private float cooldown;
	private float lastUsed;
	
	public SpellItem(String name, float cooldown) {
		super(name);
		this.cooldown = cooldown;
		lastUsed = cooldown;
	}
	
	public boolean canBeUsed() {
		return lastUsed >= cooldown;
	}
	
	/**
	 * Needs to be called by the subclasses.
	 */
	@Override
	public void use(Player player) {
		if(!canBeUsed())
			return;
		
		lastUsed = 0;
	}
	
	@Override
	public void update(double dt) {
		lastUsed += dt;
	}
}
