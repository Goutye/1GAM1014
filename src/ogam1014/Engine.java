package ogam1014;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ogam1014.entity.Entity;
import ogam1014.entity.Player;
import ogam1014.graphics.Renderer;
import ogam1014.path.PathFinder;
import ogam1014.screen.Game;
import ogam1014.screen.Menu;
import ogam1014.screen.Screen;

public class Engine extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	public static final String NAME = "1GAM1014";
	public static final int PIXEL_SIZE = 2;
	public static final int WIDTH = 400;
	public static final int HEIGHT = 300;
	public static final int WINDOW_WIDTH = WIDTH * PIXEL_SIZE;
	public static final int WINDOW_HEIGHT = HEIGHT * PIXEL_SIZE;
	
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
		//drawPath(g);
		g.dispose();
		bs.show();
	}
	
	
	public void drawPath(Graphics g) {
		try {
			// System.out.println("écran trouvé !");
			Game game = ((Game) this.screen);
			Player p = game.getPlayer();

			List<Entity> ennemies = game.getLevel().getEntities();

			for (Entity e : ennemies) {

				// LANCEMENT DE A_STAR
				if (e != p) {
					PathFinder pF = new PathFinder(e, p, game.getLevel()
							.getMap());
					ArrayList<Point> lp = pF.A_star();
System.out.println("A_STAR");
					Point precedent = new Point(pF.getPosEnd());
					int diffX = WIDTH / 2 - precedent.x*Tile.SIZE;
					int diffY = HEIGHT / 2 - precedent.y*Tile.SIZE;
					// tracé de la droite
					g.drawLine(WIDTH / 2, HEIGHT / 2,
							(int) (e.getX() - p.getX()) + WIDTH / 2,
							(int) (e.getY() - p.getY()) + HEIGHT / 2);
					// tracé du tableau
					for (Point point : lp) {
						//System.out.println("precedent : (" + precedent.x*Tile.SIZE + Tile.SIZE/2 + "," + precedent.y*Tile.SIZE + Tile.SIZE/2 +")");
						//System.out.println("courant : (" + point.x*Tile.SIZE + Tile.SIZE/2 + "," + point.y*Tile.SIZE + Tile.SIZE/2 +")");
						//System.out.println(precedent.x + " , " + precedent.x);
/*System.out.println((precedent.x*Tile.SIZE + Tile.SIZE/2 + diffX) 
		+ " , " + (precedent.y*Tile.SIZE + Tile.SIZE/2 + diffY)
		+ " -> " + (point.x*Tile.SIZE + Tile.SIZE/2 + diffX)
		+ " , " + (point.y*Tile.SIZE + Tile.SIZE/2 + diffY));*/
						g.drawLine(precedent.x*Tile.SIZE + Tile.SIZE/2 + diffX,
								precedent.y*Tile.SIZE + Tile.SIZE/2 + diffY,
								point.x*Tile.SIZE + Tile.SIZE/2 + diffX,
								point.y*Tile.SIZE + Tile.SIZE/2 + diffY);
						precedent = point;

					}
					//System.out.println("last Pos : " + precedent.toString());

				}
				// System.out.println(e);
			}
		} catch (ClassCastException cce) {
			System.err.println("Cet écran n'est une game !");
		}
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
