package ogam1014.equipment;

import ogam1014.entity.Player;

public class AttributePotionItem extends AttributeItem implements IUsableItem {
	
	private float duration;
	
	public AttributePotionItem(String name, float duration) {
		super(name, 1);
		this.duration = duration;
	}

	@Override
	public void use(Player player) {
		if(quantity == 0)
			return;
		
		quantity--;
		
		// TODO: apply modifiers to the player (buff system ?)
	}
}
