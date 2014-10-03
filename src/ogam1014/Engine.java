package ogam1014;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import ogam1014.screen.MapEditor;
import ogam1014.screen.Menu;
import ogam1014.screen.Screen;

public class Engine extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	public static final String NAME = "1GAM1014";
	public static final int HEIGHT = 380;
	public static final int WIDTH = 480;

	private boolean running = false;
	private InputHandler input = new InputHandler(this);
	private Screen screen;
	private Image image = new BufferedImage(WIDTH, HEIGHT,
			BufferedImage.TYPE_INT_RGB);

	public void setScreen(Screen screen) {
		this.screen = screen;
		screen.init(this, input);
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
			unprocessed += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = false;
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
		
		Graphics g = image.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		screen.draw(g);
		g.dispose();

		Graphics gCanvas = bs.getDrawGraphics();
		gCanvas.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
		gCanvas.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		Engine engine = new Engine();
		engine.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		engine.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		engine.setPreferredSize(new Dimension(WIDTH, HEIGHT));

		JFrame frame = new JFrame(Engine.NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(engine, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		engine.start();
	}

}
