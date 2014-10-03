package ogam1014;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class InputHandler implements KeyListener {
	public class Key {
		public int presses, absorbs;
		public boolean down, clicked;

		public Key() {
			keys.add(this);
		}

		public void toggle(boolean pressed) {
			if (pressed != down) {
				down = pressed;
			}
			if (pressed) {
				presses++;
			}
		}

		public void tick() {
			if (absorbs < presses) {
				absorbs++;
				clicked = true;
			} else {
				clicked = false;
			}
		}
	}

	public List<Key> keys = new ArrayList<Key>();

	public Key up = new Key();
	public Key down = new Key();
	public Key left = new Key();
	public Key right = new Key();
	public Key fireUp = new Key();
	public Key fireDown = new Key();
	public Key fireLeft = new Key();
	public Key fireRight = new Key();
	public Key validate = new Key();

	public void releaseAll() {
		for (int i = 0; i < keys.size(); i++) {
			keys.get(i).down = false;
		}
	}

	public void update() {
		for (int i = 0; i < keys.size(); i++) {
			keys.get(i).tick();
		}
	}

	public InputHandler(Engine engine) {
		engine.addKeyListener(this);
	}

	public void keyPressed(KeyEvent ke) {
		toggle(ke, true);
	}

	public void keyReleased(KeyEvent ke) {
		toggle(ke, false);
	}

	private void toggle(KeyEvent ke, boolean pressed) {
		if (ke.getKeyCode() == KeyEvent.VK_Z) up.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_S) down.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_Q) left.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_D) right.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_UP) fireUp.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_DOWN) fireDown.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_LEFT) fireLeft.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_RIGHT) fireRight.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_ENTER) validate.toggle(pressed);
	}

	public void keyTyped(KeyEvent ke) {
	}
}
