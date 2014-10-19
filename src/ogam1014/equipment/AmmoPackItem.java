package ogam1014.equipment;

public class AmmoPackItem extends Item {

	private BulletType bulletType;
	private int amount;

	AmmoPackItem(String name) {
		super(name);
	}

	public BulletType getBulletType() {
		return bulletType;
	}

	public void setBulletType(BulletType bulletType) {
		this.bulletType = bulletType;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getAmount() {
		return amount;
	}

}
