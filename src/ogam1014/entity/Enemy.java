package ogam1014.entity;

import ogam1014.InputHandler;
import ogam1014.Tile;
import ogam1014.graphics.Renderer;

public class Enemy extends LivingEntity {
	private static final long serialVersionUID = -1125801212253913516L;
	protected DialogBox dialogBox;
	protected boolean speaking = false;
	protected boolean speakingToSomeone = false;
	protected String speakingText;
	protected String walkingText;
	
	public Enemy() {
		this.h = 31;
		this.w = 15;
		this.hIgnored = Math.max( this.h * PERSPECTIVE, this.w - Tile.SIZE);
	}
	
	@Override
	public void update(double dt) {
		super.update(dt);
	}
	
	@Override
	public void draw(Renderer r) {
		r.blit(IMAGE, x, y, 15, 31, 0, 0);
	}
	
	protected void tryToSpeak() {
		if (!speaking && speakingText != null){
			dialogBox = new DialogBox(this, speakingText);
			level.addEntity(dialogBox);
			speaking = true;
		}
	}
	
	public void setSpeakingText(String textContent) {
		speakingText = textContent;
		
	}

	public void setWalkingText(String textContent) {
		walkingText = textContent;
	}
	
	public void stopSpeaking() {
		speaking = false;
		speakingToSomeone = false;
	}
	
	public void speaks(InputHandler input, Player interlocutor) {
		if (!speaking && walkingText != null){
			dialogBox = new DialogBox(input, this, interlocutor, walkingText);
			level.addEntity(dialogBox);
			speakingToSomeone = true;
			speaking = true;
		}
		else {
			interlocutor.stopSpeaking();
		}
	}
}
