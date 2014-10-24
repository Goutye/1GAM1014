package ogam1014.equipment;

import ogam1014.entity.Player;
import ogam1014.entity.Swing;

public class MeleeWeaponItem extends AttributeItem implements IUsableItem, IUpdatableItem {

	private double cooldown;
	private double cooldownTimer;
	private double duration;
	private double repulsePower;
	private double damage;
	private int size;
	
	public MeleeWeaponItem(String name) {
		super(name);
	}

	@Override
	public void use(Player player) {
		if (cooldownTimer > 0)
			return;

		Swing swing = new Swing(player, size, player.getShootingAngle());
		swing.setDamage(damage);
		swing.setDuration(duration);
		swing.setRepulsePower(repulsePower);
		player.spawnSwing(swing);
		cooldownTimer = cooldown;
	}

	@Override
	public void update(double dt) {
		if (cooldownTimer > 0) {
			cooldownTimer = Math.max(0, cooldownTimer - dt);
		}
	}

	public void setCooldown(double cooldown) {
		this.cooldown = cooldown;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

	public void setRepulsePower(double repulsePower) {
		this.repulsePower = repulsePower;
	}

}
