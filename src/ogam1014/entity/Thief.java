package ogam1014.entity;

import java.awt.Point;

import ogam1014.equipment.ItemFactory;
import ogam1014.graphics.Renderer;

public class Thief extends Enemy {
	private static final long serialVersionUID = 8877449123438883245L;
	private int prevDirection = 1;

	public Thief() {
		this.w = 15;
		this.h = 31;
		time = random.nextDouble() * 2;
	}

	public void update(double dt) {
		super.update(dt);

		if (!speakingToSomeone) {

			Point p = moveTowardPlayer();

			if (p == null) {
				if (Math.floor(time) % 2 == 0)
					dir_x = -1;
				else
					dir_x = 1;

				if (Math.floor(time) % 2 == 0)
					dir_y = -1;
				else
					dir_y = 1;
			}

			else {
				dir_x = p.x;
				dir_y = p.y;
			}
			dy = dir_y * SPEED / 3;
			dx = dir_x * SPEED / 3;

			if (dir_x != prevDirection && random.nextInt(10) == 0) {
				tryToSpeak();
			}

			prevDirection = dir_x;
		} else {
			dx = 0;
			dy = 0;
		}
		if (dy == 0){
			if (dx > 0){
				dir = Direction.RIGHT;
			} else if (dx < 0){
				dir = Direction.LEFT;
			} else {
				dir = Direction.DOWN;
			}
		} else {
			if (dy > 0){
				dir = Direction.DOWN;
			} else {
				dir = Direction.UP;
			}
		}
	}

	@Override
	public void draw(Renderer r) {
		if (Math.abs(dx) > 0 || Math.abs(dy) > 0 ){
			if (dir == Direction.UP){
				int frameNumber = (int)(Math.ceil(time*8) % 6);
				if (frameNumber == 37){
					r.blit(IMAGE, x, y, w, h, 120, 37);
				} else if (frameNumber == 1){
					r.blit(IMAGE, x, y, w, h, 135, 37);
				} else if (frameNumber == 2){
					r.blit(IMAGE, x, y, w, h, 150, 37);
				} else if (frameNumber == 3){
					r.blit(IMAGE, x, y, w, h, 165, 37);
				} else if (frameNumber == 4){
					r.blit(IMAGE, x, y, w, h, 150, 37);
				} else {
					r.blit(IMAGE, x, y, w, h, 135, 37);
				}
			} else if (dir == Direction.LEFT) {
				int frameNumber = (int)(Math.ceil(time*8) % 6);
				if (frameNumber == 37){
					r.blit(IMAGE, x, y, w, h, 180, 37);
				} else if (frameNumber == 1){
					r.blit(IMAGE, x, y, w, h, 195, 37);
				} else if (frameNumber == 2){
					r.blit(IMAGE, x, y, w, h, 210, 37);
				} else if (frameNumber == 3){
					r.blit(IMAGE, x, y, w, h, 225, 37);
				} else if (frameNumber == 4){
					r.blit(IMAGE, x, y, w, h, 210, 37);
				} else {
					r.blit(IMAGE, x, y, w, h, 195, 37);
				}
			} else if (dir == Direction.RIGHT) {	
				int frameNumber = (int)(Math.ceil(time*8) % 6);
				if (frameNumber == 37){
					r.blit(IMAGE, x, y, w, h, 240, 37);
				} else if (frameNumber == 1){
					r.blit(IMAGE, x, y, w, h, 255, 37);
				} else if (frameNumber == 2){
					r.blit(IMAGE, x, y, w, h, 270, 37);
				} else if (frameNumber == 3){
					r.blit(IMAGE, x, y, w, h, 285, 37);
				} else if (frameNumber == 4){
					r.blit(IMAGE, x, y, w, h, 270, 37);
				} else {
					r.blit(IMAGE, x, y, w, h, 255, 37);
				}
			} else {
				int frameNumber = (int)(Math.ceil(time*8) % 6);
				if (frameNumber == 37){
					r.blit(IMAGE, x, y, w, h, 60, 37);
				} else if (frameNumber == 1){
					r.blit(IMAGE, x, y, w, h, 75, 37);
				} else if (frameNumber == 2){
					r.blit(IMAGE, x, y, w, h, 90, 37);
				} else if (frameNumber == 3){
					r.blit(IMAGE, x, y, w, h, 105, 37);
				} else if (frameNumber == 4){
					r.blit(IMAGE, x, y, w, h, 90, 37);
				} else {
					r.blit(IMAGE, x, y, w, h, 75, 37);
				}
			}
		} else {
			if (dir == Direction.UP){
				r.blit(IMAGE, x, y, w, h, 15, 37);
			} else if (dir == Direction.LEFT) {
				r.blit(IMAGE, x, y, w, h, 30, 37);
			} else if (dir == Direction.RIGHT) {	
				r.blit(IMAGE, x, y, w, h, 45, 37);
			} else {
				r.blit(IMAGE, x, y, w, h, 0, 37);
			}
		}
	}
	
	@Override
	protected void onDeath() {
		super.onDeath();
		if (random.nextFloat() < 0.8) {
			DroppedItem drop = new DroppedItem(
					ItemFactory.make("ammopack.smallx20"), x + w / 2, y + h / 2);
			level.addEntity(drop);
		}

		if (speaking) {
			level.removeEntity(dialogBox);
		}
	}
}
