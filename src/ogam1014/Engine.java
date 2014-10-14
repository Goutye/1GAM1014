package ogam1014;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ogam1014.graphics.Renderer;
import ogam1014.screen.Menu;
import ogam1014.screen.Screen;

public class Engine extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	public static final String NAME = "1GAM1014";
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	public static final int WIDTH = (int) (WINDOW_WIDTH * 0.6);
	public static final int HEIGHT = (int) (WINDOW_HEIGHT * 0.6);

	private boolean running = false;
	private InputHandler input = new InputHandler(this);
	private Screen screen;
	private Renderer renderer = new Renderer(WIDTH, HEIGHT, WINDOW_WIDTH, WINDOW_HEIGHT);

	public void setScreen(Screen screen) {
		this.screen = screen;
		screen.init(this, input);

		System.out.println("Set screen: " + screen);
	}

	public void start() {
		running = true;
		new Thread(this).start();
	}

	public void stop() {
		running = false;
	}

	private void init() {
		setScreen(new Menu());
	}

	public void run() {
		long lastTime = System.nanoTime();
		double unprocessed = 0;
		double nsPerTick = 1000000000.0 / 60;
		int frames = 0;
		int ticks = 0;
		long lastTimer1 = System.currentTimeMillis();

		init();

		while (running) {
			long now = System.nanoTime();
			boolean shouldRender = false;
			
			unprocessed += (now - lastTime) / nsPerTick;
			lastTime = now;
			
			while (unprocessed >= 1) {
				ticks++;
				update(1 / 60.);
				unprocessed -= 1;
				shouldRender = true;
			}

			if (shouldRender) {
				frames++;
				draw();
			}

			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (System.currentTimeMillis() - lastTimer1 > 1000) {
				lastTimer1 += 1000;
				System.out.println(ticks + " ticks, " + frames + " fps");
				frames = 0;
				ticks = 0;
			}
		}
	}

	public void update(double dt) {
		if (!hasFocus()) {
			input.releaseAll();
		} else {
			input.update();
		}
		screen.update(dt);
	}
	
	public void draw() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			requestFocus();
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		renderer.clear();
		screen.draw(renderer);
		renderer.flip(g);

		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		Engine engine = new Engine();

		JFrame frame = new JFrame(Engine.NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setResizable(false);
		
		JPanel panel = (JPanel) frame.getContentPane();
		
		panel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		panel.add(engine, BorderLayout.CENTER);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		engine.start();
	}

}
