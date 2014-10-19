package ogam1014.equipment;

import java.util.Random;

import ogam1014.entity.Bullet;
import ogam1014.entity.Player;

public class RangeWeaponItem extends AttributeItem implements IUsableItem, IUpdatableItem {
	final static private Random random = new Random();

	private double spreadAngle;
	private int numBullets = 1;
	private double cooldown;
	private double cooldownTimer;
	private BulletType bulletType;
	private int bulletsLoaded;

	RangeWeaponItem(String name) {
		super(name);
	}

	@Override
	public void use(Player player) {
		if (cooldownTimer > 0)
			return;
		if (bulletsLoaded == 0)
			tryReload(player);
		if (bulletsLoaded == 0)
			return;

		double angle = player.getShootingAngle();
		for (int i = 0; i < Math.min(numBullets, bulletsLoaded - numBullets); i++) {
			double offangle = random.nextDouble() * spreadAngle - spreadAngle / 2;
			double dx = Math.cos(angle + offangle);
			double dy = Math.sin(angle + offangle);
			Bullet b = new Bullet(player, bulletType, dx * Bullet.SPEED, dy * Bullet.SPEED);
			player.spawnBullet(b);
		}

		bulletsLoaded = Math.max(0, bulletsLoaded - numBullets);
		cooldownTimer = cooldown;
	}

	private void tryReload(Player player) {
		AmmoPackItem pack = player.retrieveAmmoPack(bulletType);
		if (pack != null) {
			bulletsLoaded = pack.getAmount();
			System.out.println("*shack shack* Reloading");
		} else {
			System.out.println("*click click* Can't found ammopack for " + bulletType);
		}
	}

	@Override
	public void update(double dt) {
		if (cooldownTimer > 0) {
			cooldownTimer = Math.max(0, cooldownTimer - dt);
		}
	}

	public void setSpreadAngle(double angle) {
		this.spreadAngle = angle;
	}

	public void setNumBullets(int num) {
		this.numBullets = num;
	}

	public void setCooldown(double cooldown) {
		this.cooldown = cooldown;
	}

	public void setBulletType(BulletType bulletType) {
		this.bulletType = bulletType;
	}

}
