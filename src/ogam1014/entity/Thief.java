package ogam1014.entity;

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
		
			if(Math.floor(time) % 2 == 0)
				direction = -1;
			else
				direction = 1;
			
			dx = direction * SPEED;
			
			if( direction != prevDirection && random.nextInt(10) == 0){			
				if (!speaking){
					level.addEntity(new DialogBox(this , "Bonjour ! Comment vas tu ? Moi nickel !\n"
						+ "C'est vrai que je ressemble à rien, mais la vie est faite ainsi !\n"
						+ "Veux tu manger avec moi ?\n"
						+ "Bien sur le coca est gratuite :D"));
					speaking = true;
				}
			}
			
			prevDirection = direction;
		}
		else {
			dx = 0;
			dy = 0;
		}
	}
	
	public void speaks(InputHandler input, Player interlocutor) {
		if (!speaking){
			level.addEntity(new DialogBox(input, this, interlocutor,"Ceci est une validBox.\n"
													+ "Sugoi da na lololoooooool\n"
													+ "Bon sur ce, je parle beaucoup pour agrandir le monde !"));
			speakingToSomeone = true;
		}
		else {
			interlocutor.stopSpeaking();
		}
	}
}
