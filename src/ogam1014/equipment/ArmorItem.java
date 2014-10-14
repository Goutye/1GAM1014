package ogam1014.equipment;

public class ArmorItem extends AttributeItem {
	
	private float protection;
	private ArmorType type;

	public ArmorItem(String material, ArmorType type, float protection) {
		super(material + type);
		this.type = type;
		this.protection = protection;
	}
	
	public float getProtection() {
		return protection;
	}
	
	public ArmorType getType() {
		return type;
	}
}
