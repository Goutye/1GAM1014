package ogam1014.equipment;

public enum ArmorType {
	CHESTPLATE, LEGGINGS, BOOTS, HELMET;
	
	public String toString() {
		switch(this) {
		case CHESTPLATE:
			return "Chestplate";
		case LEGGINGS:
			return "Leggings";
		case BOOTS:
			return "Boots";
		case HELMET:
			return "Helmet";
		default:
			return "";
		}
	}
}
