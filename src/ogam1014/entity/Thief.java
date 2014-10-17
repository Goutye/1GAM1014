package ogam1014.entity;

public class Thief extends Enemy {
	private int direction = 1;
	
	public Thief() {
		this.w = 15;
		this.h = 31;
		time = random.nextDouble() * 2;
	}
	
	public void update(double dt) {
		super.update(dt);
		
		if(Math.floor(time) % 2 == 0)
			direction = -1;
		else
			direction = 1;
		
		dx = direction * SPEED;
	}
}
