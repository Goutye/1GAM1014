package ogam1014.equipment;

import java.util.Random;

import ogam1014.entity.Bullet;
import ogam1014.entity.Player;

public class RangeWeaponItem extends AttributeItem implements IUsableItem, IUpdatableItem {
	final static private Random random = new Random();

	private double spreadAngle;
	private int numBullets = 1;
	private double cooldown = 0;
	private double cooldownTimer = 0;

	public RangeWeaponItem(String name) {
		super(name);
	}

	@Override
	public void use(Player player) {
		if (cooldownTimer > 0)
			return;

		double angle = player.getShootingAngle();
		for (int i = 0; i < numBullets; i++) {
			double offangle = random.nextDouble() * spreadAngle - spreadAngle / 2;
			double dx = Math.cos(angle + offangle);
			double dy = Math.sin(angle + offangle);
			Bullet b = new Bullet(player, dx * Bullet.SPEED, dy * Bullet.SPEED);
			player.spawnBullet(b);
		}

		cooldownTimer = cooldown;
		// TODO remove ammo from player's inventory
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

}
