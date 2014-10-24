package ogam1014.entity;

import java.util.List;

import ogam1014.InputHandler;
import ogam1014.Tile;
import ogam1014.attributes.PlayerAttributes;
import ogam1014.attributes.PlayerAttributes.Attr;
import ogam1014.equipment.AmmoPackItem;
import ogam1014.equipment.BulletType;
import ogam1014.equipment.Item;

import java.util.ArrayList;

import ogam1014.collide.Box;
import ogam1014.collide.Collide;
import ogam1014.equipment.ItemFactory;
import ogam1014.equipment.PlayerInventory;
import ogam1014.equipment.RangeWeaponItem;
import ogam1014.graphics.Renderer;

public class Player extends LivingEntity {
	private static final long serialVersionUID = 1L;

	transient private InputHandler input;
	private PlayerInventory inventory;
	private PlayerAttributes attributes;
	
	private double lastShootDirX = 1;
	private double lastShootDirY = 0;
	private boolean speaking = false;
	
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
		inventory.addItem(ItemFactory.make("ammopack.smallx20"));
		inventory.addItem(ItemFactory.make("ammopack.smallx20"));
		inventory.addItem(ItemFactory.make("ammopack.smallx20"));
		inventory.addItem(ItemFactory.make("ammopack.smallx20"));
		inventory.addItem(ItemFactory.make("ammopack.smallx20"));

		attributes = new PlayerAttributes();

		this.hIgnored = Math.max( this.h * PERSPECTIVE, this.h - Tile.SIZE);
	}
	
	public void setInput(InputHandler input){
		this.input = input;
	}

	@Override
	public void update(double dt) {
		double speed = attributes.get(Attr.SPEED);
		if (!speaking) {
			
			if (input.up.down) {
				dy = -speed;
				lastShootDirY = -speed;
				dir = Direction.UP;
			}
	
			if (input.down.down) {
				dy = speed;
				lastShootDirY = speed;
				dir = Direction.DOWN;
			}
			
			if(!input.up.down && !input.down.down) {
				lastShootDirY /= speed * 10;
			}
	
			if (input.right.down) {
				dx = speed;
				lastShootDirX = speed;
				dir = Direction.RIGHT;
			}
	
			if (input.left.down) {
				dx = -speed;
				lastShootDirX = -speed;
				dir = Direction.LEFT;
			}
			
			if(!input.right.down && !input.left.down) {
				lastShootDirX /= speed * 10;
			}
	
			if (input.slot1.down) {
				inventory.use(0);
			} else if (input.slot2.down) {
				inventory.use(1);
			} else if (input.slot3.down) {
				inventory.use(2);
			}
		
			if (input.validate.released) {
				Box boxAbove = new Box(box.x, box.y - box.height, box.width, box.height);
				ArrayList<Entity> entities = getEntitiesIn(boxAbove);
				
				if(entities.size() > 0) {
					speaking = true;
					entities.get(0).speaks(input, this);
				}
			}
		}
		
		inventory.update(dt);
		
		if(inventory.needsAttributesUpdate())
			updateAttributes(dt);
		
		super.update(dt);
	}
	
	private void updateAttributes(double dt) {
		attributes.resetAll();
		inventory.applyModifiers(attributes);
		inventory.acknowledgeUpdate();
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
				int frameNumber = (int)(Math.ceil(time*8) % 6);
				if (frameNumber == 0){
					r.blit(IMAGE, x, y, w, h, 240, 0);
				} else if (frameNumber == 1){
					r.blit(IMAGE, x, y, w, h, 255, 0);
				} else if (frameNumber == 2){
					r.blit(IMAGE, x, y, w, h, 270, 0);
				} else if (frameNumber == 3){
					r.blit(IMAGE, x, y, w, h, 285, 0);
				} else if (frameNumber == 4){
					r.blit(IMAGE, x, y, w, h, 270, 0);
				} else {
					r.blit(IMAGE, x, y, w, h, 255, 0);
				}
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
		return Math.atan2(lastShootDirY, lastShootDirX);
	}

	public void spawnBullet(Bullet b) {
		level.addEntity(b);
	}


	public AmmoPackItem retrieveAmmoPack(BulletType bulletType) {
		List<Item> list = inventory.getAll("ammopack.");
		AmmoPackItem ap = null;
		
		for (Item i : list) {
			AmmoPackItem pack = (AmmoPackItem) i;
			if (pack.getBulletType() == bulletType) {
				if ((ap == null || pack.getAmount() > ap.getAmount()) && pack.getQuantity() > 0) {
					ap = (AmmoPackItem) pack.makeClone(1);
				}
			}
		}
		
		if (ap != null)
			inventory.removeItem(ap);
		
		return ap;
	}

	@Override
	protected boolean collidesWith(Entity e) {
		return super.collidesWith(e) || e instanceof DroppedItem;
	}

	@Override
	protected void onCollision(Entity other) {
		super.onCollision(other);
		if (other instanceof DroppedItem) {
			DroppedItem drop = (DroppedItem) other;
			Item it = drop.popItem();
			inventory.addItem(it);
		}
	}

	private ArrayList<Entity> getEntitiesIn(Box box){
		ArrayList<Entity> entities = new ArrayList<Entity>();
		
		for(Entity e : level.getEntities()){
			if (Collide.AABB_AABB(e.getBox(), box))
				entities.add(e);
		}
		
		return entities;
	}
	
	
	public void stopSpeaking() {
		speaking = false;
	}
}
