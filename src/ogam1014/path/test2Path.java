package ogam1014.path;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ogam1014.Engine;

public class test2Path {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Engine engine = new Engine();

		JFrame frame = new JFrame(Engine.NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setResizable(false);
		
		JPanel panel = (JPanel) frame.getContentPane();
		
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		panel.add(engine, BorderLayout.CENTER);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		engine.start();
		

	}

}
