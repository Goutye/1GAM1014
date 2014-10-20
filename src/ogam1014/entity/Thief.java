package ogam1014.entity;

import java.awt.Point;

import ogam1014.equipment.ItemFactory;
import ogam1014.InputHandler;

public class Thief extends Enemy {
	private static final long serialVersionUID = 8877449123438883245L;
	private int direction = 1;
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

			else{
				dir_x = p.x;
				dir_y = p.y; 
			}
			dy = dir_y * SPEED / 3;
			dx = dir_x * SPEED / 3;
			
			if( dir_x != prevDirection && random.nextInt(10) == 0){			
				if (!speaking){
					dialogBox = new DialogBox(this , "Bonjour ! Comment vas tu ? Moi nickel !\n"
							+ "C'est vrai que je ressemble à rien, mais la vie est faite ainsi !\n"
							+ "Veux tu manger avec moi ?\n"
							+ "Bien sur le coca est gratuite :D");
					level.addEntity(dialogBox);
					speaking = true;
				}
			}
			
			prevDirection = dir_x;
		}
		else {
			dx = 0;
			dy = 0;
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

	public void speaks(InputHandler input, Player interlocutor) {
		if (!speaking){
			dialogBox = new DialogBox(input, this, interlocutor,"Ceci est une validBox.\n"
					+ "Sugoi da na lololoooooool\n"
					+ "Bon sur ce, je parle beaucoup pour agrandir le monde !");
			level.addEntity(dialogBox);
			speakingToSomeone = true;
			speaking = true;
		}
		else {
			interlocutor.stopSpeaking();
		}
	}

}
