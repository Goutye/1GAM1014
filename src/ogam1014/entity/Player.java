package ogam1014.entity;

import ogam1014.InputHandler;
import ogam1014.equipment.PlayerInventory;
import ogam1014.Tile;
import ogam1014.graphics.Renderer;

public class Player extends LivingEntity {
	static public double SPEED = 200;

	private InputHandler input;
	
	private PlayerInventory inventory;
	
	public Player(InputHandler input) {
		this.input = input;
		this.w = 15;
		this.h = 31;
		
		inventory = new PlayerInventory(this);
		this.hIgnored = Math.max( this.h * PERSPECTIVE, this.h - Tile.SIZE);
	}

	@Override
	public void update(double dt) {
		if (input.up.down) {
			dy = -SPEED;
			dir = Direction.UP;
		}

		if (input.down.down) {
			dy = SPEED;
			dir = Direction.DOWN;
		}

		if (input.right.down) {
			dx = SPEED;
			dir = Direction.RIGHT;
		}

		if (input.left.down) {
			dx = -SPEED;
			dir = Direction.LEFT;
		}

		boolean fire = false;
		double fireDx = 0;
		double fireDy = 0;

		if (input.fireUp.down) {
			fire = true;
			fireDy = -1;
		}

		if (input.fireDown.down) {
			fire = true;
			fireDy = 1;
		}

		if (input.fireLeft.down) {
			fire = true;
			fireDx = -1;
		}

		if (input.fireRight.down) {
			fire = true;
			fireDx = 1;
		}

		if (fire) {
			Bullet b = new Bullet(this, fireDx * Bullet.SPEED, fireDy * Bullet.SPEED);
			level.addEntity(b);
		}

		super.update(dt);		
		inventory.update(dt);
	}

	@Override
	public void draw(Renderer r) {
		if (Math.abs(dx) > SPEED/2 || Math.abs(dy) > SPEED/2 ){
			if (dir == Direction.UP){
				r.blit(IMAGE, x, y, w, h, 15, 0);
			} else if (dir == Direction.LEFT) {
				r.blit(IMAGE, x, y, w, h, 30, 0);
			} else if (dir == Direction.RIGHT) {	
				r.blit(IMAGE, x, y, w, h, 45, 0);
			} else {
				if (Math.ceil(time*5) % 4 == 0){
					r.blit(IMAGE, x, y, w, h, 60, 0);
				} else if (Math.ceil(time*5) % 4 == 2){
					r.blit(IMAGE, x, y, w, h, 75, 0);
				} else {
					r.blit(IMAGE, x, y, w, h, 90, 0);
				}
			}
		} else {
			if (dir == Direction.UP){
				r.blit(IMAGE, x, y, w, h, 15, 0);
			} else if (dir == Direction.LEFT) {
				r.blit(IMAGE, x, y, w, h, 30, 0);
			} else if (dir == Direction.RIGHT) {	
				r.blit(IMAGE, x, y, w, h, 45, 0);
			} else {
				r.blit(IMAGE, x, y, w, h, 0, 0);
			}
		}
	}

}
