package ogam1014.equipment;

import ogam1014.entity.Player;

public class PotionItem extends Item implements IUsableItem {

	// TODO: add modifiers
	
	public PotionItem(String name) {
		super(name, 1);
	}

	@Override
	public void use(Player player) {
		if(quantity == 0)
			return;
		
		quantity--;
		
		// TODO: apply modifiers to the player
	}
}
