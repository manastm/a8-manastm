package a8;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Main{
	public static void main(String[] args) {
		// Create a model to handle the game logic
		Model game = new Model();

		// Create a view to handle the display code
		View ui = new View();
		// Create controller to handle model and view
		Controller master = new Controller(game, ui);		
		
		// Create the main window
		JFrame conwayGame = new JFrame();
		// TODO: Set the window title
		// TODO: Set the default close operation
		// TODO: Disable resizing
		conwayGame.setTitle("Conway's Game of Life");
		conwayGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		conwayGame.setPreferredSize(new Dimension(1000, 1000));

		conwayGame.add(ui);

		// Show the window on the screen
		conwayGame.pack();
		conwayGame.setVisible(true);
	}
}