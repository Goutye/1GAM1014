package ogam1014.equipment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ogam1014.entity.Player;

public class PlayerInventory implements Serializable {

	private static final long serialVersionUID = 1L;
	private Player player;
	private List<Item> items = new ArrayList<Item>();
	private Map<ArmorType, ArmorItem> armor = new HashMap<ArmorType, ArmorItem>();
	private IUsableItem[] equipped = new IUsableItem[3];

	public PlayerInventory(Player player) {
		equipped[0] = null;
		equipped[1] = null;
		equipped[2] = null;

		for (ArmorType type : ArmorType.values()) {
			armor.put(type, null);
		}

		this.player = player;
	}

	public void update(double dt) {
		for (Item item : items) {
			if (item instanceof IUpdatableItem) {
				((IUpdatableItem) item).update(dt);
			}
		}
	}

	public void use(int slot) {
		if (slot < 0 || slot > 2)
			return;

		if (equipped[slot] != null) {
			equipped[slot].use(player);
		}
	}

	public void equipItem(int slot, IUsableItem item) {
		if (slot < 0 || slot > 2)
			return;

		equipped[slot] = item;
	}

	public void addItem(Item item) {
		items.add(item);
	}

	public float getTotalArmorProtection() {
		float total = 0;

		for (ArmorType type : ArmorType.values()) {
			total += armor.get(type).getProtection();
		}

		return total;
	}
}
