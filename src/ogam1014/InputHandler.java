package ogam1014;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class InputHandler {
	public class Key {
		public boolean down;
		public boolean pressed;
		public boolean released;
		private boolean reset;

		public Key() {
			keys.add(this);
		}

		public void set(boolean press) {
			down = press;
			released = reset ? !press : false;
			pressed = reset ? press : false;
			reset = false;
		}

		public void update() {
			if (reset) {
				pressed = released = false;
			}
			reset = true;
		}
	}

	private List<Key> keys = new ArrayList<Key>();

	public Key up = new Key();
	public Key down = new Key();
	public Key left = new Key();
	public Key right = new Key();
	public Key fireUp = new Key();
	public Key fireDown = new Key();
	public Key fireLeft = new Key();
	public Key fireRight = new Key();
	public Key validate = new Key();

	public InputHandler(Engine engine) {
		engine.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) {
				set(e, false);
			}

			@Override
			public void keyPressed(KeyEvent e) {
				set(e, true);
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
	}

	public void releaseAll() {
		for (Key k : keys) {
			k.down = false;
		}
	}

	public void update() {
		for (Key k : keys) {
			k.update();
		}
	}

	public void keyPressed(KeyEvent ke) {
		set(ke, true);
	}

	public void keyReleased(KeyEvent ke) {
		set(ke, false);
	}

	private void set(KeyEvent ke, boolean pressed) {
		if (ke.getKeyCode() == KeyEvent.VK_Z)
			up.set(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_S)
			down.set(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_Q)
			left.set(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_D)
			right.set(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_UP)
			fireUp.set(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_DOWN)
			fireDown.set(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_LEFT)
			fireLeft.set(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_RIGHT)
			fireRight.set(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_ENTER)
			validate.set(pressed);
	}

}
