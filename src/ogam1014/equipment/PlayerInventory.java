package ogam1014.equipment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ogam1014.attributes.PlayerAttributes;
import ogam1014.entity.Player;

public class PlayerInventory implements Serializable {

	private static final long serialVersionUID = 1L;
	private Player player;
	private List<Item> items = new ArrayList<Item>();
	private Map<ArmorType, ArmorItem> armor = new HashMap<ArmorType, ArmorItem>();
	private IUsableItem[] equipped = new IUsableItem[3];
	private boolean dirty;

	public PlayerInventory(Player player) {
		equipped[0] = null;
		equipped[1] = null;
		equipped[2] = null;

		for (ArmorType type : ArmorType.values()) {
			armor.put(type, null);
		}
		
		dirty = true;

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
		dirty = true;
	}

	public void addItem(Item item) {
		if(item.isStackable()) {
			for(Item i : items) {
				if(i == item) {
					i.addQuantity(item.getQuantity());
					break;
				}
			}
		}
		else {
			items.add(item);
		}
		
		dirty = true;
	}

	public void removeItem(Item item) {
		if(item.isStackable()) {
			for(Item i : items) {
				if(i == item) {
					int ovf = i.removeQuantity(item.getQuantity());
					item.setQuantity(ovf);
					break;
				}
			}
		}
		else {
			items.remove(item);
		}
		
		dirty = true;
	}

	public List<Item> getAll(String descriptor) {
		List<Item> list = new ArrayList<Item>();
		
		for (Item i : items) {
			if (i.getDescriptor().startsWith(descriptor))
				list.add(i);
		}
		
		return list;
	}

	public float getTotalArmorProtection() {
		float total = 0;

		for (ArmorType type : ArmorType.values()) {
			total += armor.get(type).getProtection();
		}

		return total;
	}
	
	public void applyModifiers(PlayerAttributes attr) {
		for(int i = 0; i < 3; i++) {
			if(equipped[i] != null && equipped[i] instanceof AttributeItem) {
				attr.applyRaw(((AttributeItem) equipped[i]).getRawModifiers());
				attr.applyRelative(((AttributeItem) equipped[i]).getRelativeModifiers());
			}
		}
		
		for(ArmorItem item : armor.values()) {
			if(item != null) {
				attr.applyRaw(item.getRawModifiers());
				attr.applyRelative(item.getRelativeModifiers());
			}
		}
	}
	
	public boolean needsAttributesUpdate() {
		return dirty;
	}
	
	public void acknowledgeUpdate() {
		dirty = false;
	}
}
