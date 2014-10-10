package ogam1014.equipment;

import ogam1014.entity.Player;

/**
 * Defines an item that can be equipped in the item slots.
 */
public interface IUsableItem {
	
	/**
	 * Called when used by the player.
	 */
	public void use(Player player);
}
