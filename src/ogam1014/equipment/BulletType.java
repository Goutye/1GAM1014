package ogam1014.equipment;

public enum BulletType {
	SMALL(1), HEAVY(4);

	private double damage;

	private BulletType(double dmg) {
		this.damage = dmg;
	}

	public double getDamage() {
		return damage;
	}
}
