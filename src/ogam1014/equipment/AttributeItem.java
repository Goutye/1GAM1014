package ogam1014.equipment;

import ogam1014.attributes.PlayerAttributes;

public abstract class AttributeItem extends Item {
	
	private PlayerAttributes rawModifier = new PlayerAttributes();
	private PlayerAttributes relativeModifier = new PlayerAttributes();
	
	public AttributeItem(String name) {
		super(name);
	}
	
	public AttributeItem(String name, int quantity) {
		super(name, quantity);
	}
	
	public void setRawModifier(PlayerAttributes.Attr attr, float value) {
		rawModifier.set(attr, value);
	}
	
	public void setRelativeModifier(PlayerAttributes.Attr attr, float value) {
		relativeModifier.set(attr, value);
	}
	
	public PlayerAttributes getRawModifiers() {
		return rawModifier;
	}
	
	public PlayerAttributes getRelativeModifiers() {
		return relativeModifier;
	}
}
