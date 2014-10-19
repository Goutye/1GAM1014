package ogam1014.equipment;

import ogam1014.graphics.Renderer;

public abstract class Item {
	
	private String descriptor;
	private String name;
	private boolean stackable;
	protected int quantity;
	
	Item(String name) {
		this.name = name;
		stackable = false;
		quantity = 1;
	}
	
	Item(String name, int quantity) {
		this.name = name;
		this.quantity = quantity;
		stackable = true;
	}
	
	public void mergeStack(Item item) {
		if(stackable && item.descriptor.equals(descriptor)) {
			quantity += item.quantity;
		}
	}
	
	public boolean isStackable() {
		return stackable;
	}
	
	public String getName() {
		return name;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int i) {
		quantity = i;
	}
	
	public void addQuantity(int i) {
		quantity += i;
	}
	
	public int removeQuantity(int i) {
		int overflowAmmount = 0;
		
		quantity -= i;
		
		if(quantity < 0) {
			overflowAmmount = -quantity;
			quantity = 0;
		}
		
		return overflowAmmount;
	}

	public void setDescriptor(String descriptor) {
		this.descriptor = descriptor;
	}

	public String getDescriptor() {
		return descriptor;
	}

	public void drawIcon(Renderer r) {
	}
	
	public void drawWorld(Renderer r) {
	}
	
	public boolean equals(Object o) {
		if(!(o instanceof Item))
			return false;
		
		return ((Item) o).descriptor.equalsIgnoreCase(descriptor);
	}
	
	public Item makeClone(int quantity) {
		if(!isStackable())
			return null;
		
		Item item = ItemFactory.make(descriptor);
		item.setQuantity(quantity);
		return item;
	}
	
	public Item makeClone() {
		if(isStackable())
			return makeClone(1);
		
		Item item = ItemFactory.make(descriptor);
		return item;
	}
}
