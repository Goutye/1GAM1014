package ogam1014.entity;

import java.awt.Color;

import ogam1014.InputHandler;
import ogam1014.collide.Box;
import ogam1014.entity.Entity;
import ogam1014.graphics.Renderer;

public class DialogBox extends Entity {
	private static final long serialVersionUID = 1426754327705143607L;
	private static int FONTSIZE = 10;
	private static double TIME_BEFORE_NEXT_STR = 2;
	
	private String[][] texts;
	private MobEntity e;
	private Player interlocutor;
	private InputHandler input;
	private Box box = new Box(0,0,0,0);
	private Boolean validBox; 
	private int currentText = 0;
	private double time = 0;
	
	public DialogBox(MobEntity e, String text) {
		this.validBox = false;
		this.e = e;
		init(text);
	}
	
	public DialogBox(InputHandler input, MobEntity speaker, Player interlocutor, String text) {
		this.validBox = true;
		this.input = input;
		this.e = speaker;
		this.interlocutor = interlocutor;
		init(text);
	}
	
	public void init(String text){
		this.texts = prepareString(text);
		this.passiv = true;
		
		box.width = Math.max(texts[currentText][0].length(), texts[currentText][1].length()) * (FONTSIZE+1) / 2 ;
		box.height = 2 * FONTSIZE + 3;
		
		box.x = (int) e.getX() - (int) (box.width/2);
		box.y = (int) e.getY() - box.height;
	}

	private String[][] prepareString(String text) {
		String texts[][];
		
		String textSplit[] = text.split("\n");
		
		texts = new String[textSplit.length][2];
		
		for(int i = 0; i < textSplit.length; ++i){
			String splitSpace[] = textSplit[i].split(" ");
			
			int maxPerLine = textSplit[i].length() / 2;
			int currentLengthLine = 0;
			texts[i][0] = "";
			texts[i][1] = "";
			
			for(String s : splitSpace){
				currentLengthLine += s.length() + 1;
				
				if (currentLengthLine < maxPerLine)
					texts[i][0] += s + " ";
				else
					texts[i][1] += s + " ";
			}
			
			if (texts[i][0].length() > 0)
				texts[i][0] = texts[i][0].substring(0, texts[i][0].length() - 1);
			if (texts[i][1].length() > 0)
				texts[i][1] = texts[i][1].substring(0, texts[i][1].length() - 1);
		}
		
		return texts;
	}
	
	@Override
	public void update(double dt) {
		time += dt;
		
		box.x = (int) e.getX() - (int) (box.width/2);
		box.y = (int) e.getY() - box.height;
		
		if( !validBox)
			currentText = (int) ((time / TIME_BEFORE_NEXT_STR) % texts.length);
		else
			if(input.validate.pressed || input.leftButton.pressed)
				currentText += 1;
		
		if (currentText >= texts.length || time > TIME_BEFORE_NEXT_STR * texts.length){
			level.removeEntity(this);
			e.stopSpeaking();
			if (validBox)
				interlocutor.stopSpeaking();
			return;
		}
		
		box.width = Math.max(texts[currentText][0].length(), texts[currentText][1].length()) * (FONTSIZE+1) / 2;
	}

	@Override
	public void draw(Renderer r) {
		/* TODO Little )/  ou \( suivant la direction et la bulle
		 */
		r.setColor(new Color(0, 0, 0, 0.8f));
		r.setStroke(1);
		r.getGraphics().drawRoundRect(box.x, box.y, box.width, box.height, 10, 10);
		r.setColor(new Color(0, 0, 0, 0.4f));
		r.getGraphics().fillRoundRect(box.x, box.y, box.width, box.height, 10, 10);
		r.setColor(Color.white);
		r.setFontSize(FONTSIZE);
		r.drawCenteredText(texts[currentText][0], box.x, box.y + 5, box.width);
		r.drawCenteredText(texts[currentText][1], box.x, box.y + 5 + FONTSIZE, box.width);
		
		if (validBox) {
			if (Math.floor(time * 2) % 2 == 0)
				r.drawCenteredText("»", box.x + box.width - 5, box.y + 6);
			else
				r.drawCenteredText(">", box.x + box.width - 5, box.y + 6);
		}
	}
}
