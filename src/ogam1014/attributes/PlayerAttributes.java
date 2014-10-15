package ogam1014.attributes;

public class PlayerAttributes {
	
	public enum Attr {
		MAX_HEALTH,
		SPEED,
		DEXTERITY,
		STRENGTH,
		ACCURACY
	}
	
	private static final int NB_ATTR = Attr.values().length;
	
	private float[] base = new float[NB_ATTR];
	private float[] raw = new float[NB_ATTR];
	private float[] relative = new float[NB_ATTR];
	
	public PlayerAttributes() {
		resetAll();
	}
	
	public void resetAll() {
		resetModifiers();
		resetBase();
	}
	
	public void resetModifiers() {
		for(int i = 0; i < NB_ATTR; i++) {
			raw[i] = 0;
			relative[i] = 1;
		}
	}
	
	public void resetBase() {
		/**
		 * TODO: complete with base attributes
		 */
		base[Attr.MAX_HEALTH.ordinal()] = 10;
		base[Attr.SPEED.ordinal()] = 200;
		base[Attr.DEXTERITY.ordinal()] = 1;
		base[Attr.STRENGTH.ordinal()] = 1;
		base[Attr.ACCURACY.ordinal()] = 1;
	}
	
	
	/**
	 * Performs additive modification to attributes.
	 * Ex: maxHealth + 3 ==> attr.maxHealth = 3.
	 */
	public void applyRaw(PlayerAttributes attr) {
		for(int i = 0; i < NB_ATTR; i++) {
			raw[i] += attr.base[i];
		}
	}

	/**
	 * Performs multiplicative modification to attributes.
	 * Ex: speed + 10% ==> attr.speed = 1.1;
	 * 	   speed - 5%  ==> attr.speed = 0.95;
	 */
	public void applyRelative(PlayerAttributes attr) {
		for(int i = 0; i < NB_ATTR; i++) {
			relative[i] *= attr.base[i];
		}
	}
	
	public void set(Attr attribute, float value) {
		base[attribute.ordinal()] = value;
	}
	
	public float get(Attr attribute) {
		return base[attribute.ordinal()] * relative[attribute.ordinal()] + raw[attribute.ordinal()];
	}
}
