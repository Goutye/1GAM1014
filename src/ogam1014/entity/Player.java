package ogam1014.entity;

import ogam1014.InputHandler;
import ogam1014.Tile;
import ogam1014.equipment.ItemFactory;
import ogam1014.equipment.PlayerInventory;
import ogam1014.equipment.RangeWeaponItem;
import ogam1014.graphics.Renderer;
import ogam1014.entity.DialogBox;

public class Player extends LivingEntity {
	private static final long serialVersionUID = 1L;

	transient private InputHandler input;
	private PlayerInventory inventory;
	
	public Player(InputHandler input) {
		this.input = input;
		this.w = 15;
		this.h = 31;
		
		inventory = new PlayerInventory(this);
		RangeWeaponItem gun = (RangeWeaponItem) ItemFactory.make("rangeweapon.simplegun");
		inventory.addItem(gun);
		inventory.equipItem(0, gun);
		RangeWeaponItem shotgun = (RangeWeaponItem) ItemFactory.make("rangeweapon.shotgun");
		inventory.addItem(shotgun);
		inventory.equipItem(1, shotgun);

		this.hIgnored = Math.max( this.h * PERSPECTIVE, this.h - Tile.SIZE);
	}
	
	public void setInput(InputHandler input){
		this.input = input;
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

		if (input.slot1.down) {
			inventory.use(0);
		}
		if (input.slot2.down) {
			inventory.use(1);
		}
		if (input.slot3.down) {
			inventory.use(2);
		}

		super.update(dt);		
		inventory.update(dt);
	}

	@Override
	public void draw(Renderer r) {
		if (Math.abs(dx) > SPEED/2 || Math.abs(dy) > SPEED/2 ){
			if (dir == Direction.UP){
				int frameNumber = (int)(Math.ceil(time*8) % 6);
				if (frameNumber == 0){
					r.blit(IMAGE, x, y, w, h, 120, 0);
				} else if (frameNumber == 1){
					r.blit(IMAGE, x, y, w, h, 135, 0);
				} else if (frameNumber == 2){
					r.blit(IMAGE, x, y, w, h, 150, 0);
				} else if (frameNumber == 3){
					r.blit(IMAGE, x, y, w, h, 165, 0);
				} else if (frameNumber == 4){
					r.blit(IMAGE, x, y, w, h, 150, 0);
				} else {
					r.blit(IMAGE, x, y, w, h, 135, 0);
				}
			} else if (dir == Direction.LEFT) {
				int frameNumber = (int)(Math.ceil(time*8) % 6);
				if (frameNumber == 0){
					r.blit(IMAGE, x, y, w, h, 180, 0);
				} else if (frameNumber == 1){
					r.blit(IMAGE, x, y, w, h, 195, 0);
				} else if (frameNumber == 2){
					r.blit(IMAGE, x, y, w, h, 210, 0);
				} else if (frameNumber == 3){
					r.blit(IMAGE, x, y, w, h, 225, 0);
				} else if (frameNumber == 4){
					r.blit(IMAGE, x, y, w, h, 210, 0);
				} else {
					r.blit(IMAGE, x, y, w, h, 195, 0);
				}
			} else if (dir == Direction.RIGHT) {	
				r.blit(IMAGE, x, y, w, h, 45, 0);
			} else {
				int frameNumber = (int)(Math.ceil(time*8) % 6);
				if (frameNumber == 0){
					r.blit(IMAGE, x, y, w, h, 60, 0);
				} else if (frameNumber == 1){
					r.blit(IMAGE, x, y, w, h, 75, 0);
				} else if (frameNumber == 2){
					r.blit(IMAGE, x, y, w, h, 90, 0);
				} else if (frameNumber == 3){
					r.blit(IMAGE, x, y, w, h, 105, 0);
				} else if (frameNumber == 4){
					r.blit(IMAGE, x, y, w, h, 90, 0);
				} else {
					r.blit(IMAGE, x, y, w, h, 75, 0);
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

	public double getShootingAngle() {
		return Math.atan2(dy, dx);
	}

	public void spawnBullet(Bullet b) {
		level.addEntity(b);
	}

}
