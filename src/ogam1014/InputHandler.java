package ogam1014;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

public class InputHandler {
	public class Key {
		public int code;
		public boolean down;
		public boolean pressed;
		public boolean released;
		private boolean reset;

		public Key(int code) {
			this.code = code;
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
	
	public class Button extends Key {
		public Button(int code) {
			super(code);
		}
	}

	private List<Key> keys = new ArrayList<Key>();
	private List<Button> buttons = new ArrayList<Button>();

	public Key up = addKey(KeyEvent.VK_Z);
	public Key down = addKey(KeyEvent.VK_S);
	public Key left = addKey(KeyEvent.VK_Q);
	public Key right = addKey(KeyEvent.VK_D);
	public Key fireUp = addKey(KeyEvent.VK_UP);
	public Key fireDown = addKey(KeyEvent.VK_DOWN);
	public Key fireLeft = addKey(KeyEvent.VK_LEFT);
	public Key fireRight = addKey(KeyEvent.VK_RIGHT);
	public Key validate = addKey(KeyEvent.VK_ENTER);
	public Key pause = addKey(KeyEvent.VK_P);

	public Button rightButton = addButton(MouseEvent.BUTTON3);
	public Button leftButton = addButton(MouseEvent.BUTTON1);
	public Point mouse;

	private Key addKey(int code) {
		Key key = new Key(code);
		keys.add(key);
		return key;
	}
	
	private Button addButton(int code) {
		Button button = new Button(code);
		buttons.add(button);
		return button;
	}
	
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
		
		engine.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				set(e, false);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				set(e, true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		
		engine.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
				mouse = new Point(e.getX(), e.getY());
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				mouse = new Point(e.getX(), e.getY());
			}
		});
	}

	public void releaseAll() {
		for (Key k : keys) {
			k.down = false;
		}
		
		for (Button b : buttons) {
			b.down = false;
		}
	}

	public void update() {
		for (Key k : keys) {
			k.update();
		}
		
		for (Button b : buttons) {
			b.update();
		}
	}

	public void keyPressed(KeyEvent ke) {
		set(ke, true);
	}

	public void keyReleased(KeyEvent ke) {
		set(ke, false);
	}

	private void set(KeyEvent ke, boolean pressed) {
		for (Key k : keys) {
			if (k.code == ke.getKeyCode()) {
				k.set(pressed);
				break;
			}
		}
	}
	
	private void set(MouseEvent me, boolean pressed) {
		for (Button b : buttons) {
			if (b.code == me.getButton()) {
				b.set(pressed);
				break;
			}
		}
	}
}
