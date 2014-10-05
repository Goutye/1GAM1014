package ogam1014.graphics;

import ogam1014.entity.Entity;

public class Camera {

	private Entity centerOn;
	private double x;
	private double y;

	public void update(double dt) {
		if (centerOn != null) {
			x = centerOn.getX();
			y = centerOn.getY();
		}
	}
	
	public void centerOn(Entity entity) {
		this.centerOn = entity;
	}
	
	public void centerOn(double x, double y) {
		this.x = x;
		this.y = y;
		this.centerOn = null;
	}

	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
}
