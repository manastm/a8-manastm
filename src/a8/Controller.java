package a8;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Timer;

import a8.View.Grid;

//import a7.OthelloWidget.Dir;

public class Controller implements CellListener{

	private Model model;
	private View view;
	private boolean[][] changeBoard;
	private boolean autoMode;
	private int delay;
	
	public Controller (Model model, View view) {
		this.model = model;
		this.view = view;
		this.view.setListener(this);
		autoMode = false;
		changeBoard = new boolean[model.getSizeX()][model.getSizeY()];

		for (int i = 0; i < model.getSizeX(); i++) {
			for (int j = 0; j<model.getSizeY(); j++) {
				changeBoard[i][j] = false;
			}
		}
		
		this.clear();
		
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					if (autoMode) {
						updateGen();
						
						try {
							Thread.sleep(delay);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					try {
						Thread.sleep(2);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}); 
		
		t.start();
	}
	
	public void resizeBoard() {
		model.setSizeX(view.getGridSize());
		model.setSizeY(view.getGridSize());
		view.resizeGrid(model.getSizeX());
		
		changeBoard = new boolean[model.getSizeX()][model.getSizeY()];

		for (int i = 0; i < model.getSizeX(); i++) {
			for (int j = 0; j<model.getSizeY(); j++) {
				changeBoard[i][j] = false;
			}
		}
		this.clear();
	}
	
	// Clears game
	public void clear() {
		boolean[][] gBools = view.getBoardGrid().getGridBools();

		for (int i = 0; i < model.getSizeX(); i++) {
			for (int j = 0; j < model.getSizeY(); j++) {

				view.getBoardGrid().getGridColors()[i][j] = Color.WHITE;

				gBools[i][j] = false;
			}

		}
		view.getBoardGrid().repaint();
	}
	 
	// Updates Generation w/o torus 
	public void updateGen() {
		
		int black;
		int row; 
		int col;
		boolean[][] gBools = view.getBoardGrid().getGridBools();
		for (int i = 0; i < model.getSizeX(); i++) {
			for (int j =0; j< model.getSizeY(); j++) {
				black = 0;

				for (int r = 0; r < 8; r++) {
					Dir d = allDir[r];
					row = i + d.r;
					col = j + d.c;

					if (row < 0 || row >= model.getSizeX() || col < 0 || col >= model.getSizeY()) {

					} else {
						if (gBools[row][col]) {
							black++;
						}
					}
				}
				
				if (gBools[i][j]) {
					if (black >= model.getLowSurv() && black <= model.getHighSurv()) {
						
					}
					else {
						changeBoard[i][j] = true;
					}
				}
				else {
					if (black >= model.getLowBirth() && black <= model.getHighBirth()) {
						changeBoard[i][j] = true;
					}
				}
			}
		}
		
		for (int i = 0; i < model.getSizeX(); i++) {
			for (int j = 0; j < model.getSizeY(); j++) {

				if (changeBoard[i][j]) {
					if (view.getBoardGrid().getGridColors()[i][j] == Color.WHITE) {
						view.getBoardGrid().getGridColors()[i][j] = Color.BLACK;
					}
					else {
						view.getBoardGrid().getGridColors()[i][j] = Color.WHITE;
					}
					
					gBools[i][j] = !gBools[i][j];
					changeBoard[i][j] = false;
				}

			}
		}
		
		view.getBoardGrid().repaint();
	}
	
	static class Dir {
		public int r;
		public int c;
		
		public Dir (int row, int col) {
			r = row;
			c = col;
		}
	}
	
	public static Dir[] allDir = new Dir[] {
			new Dir(1, 0), 
			new Dir(1, 1), 
			new Dir(0, 1), 
			new Dir(-1, 1), 
			new Dir(-1, 0), 
			new Dir(-1, -1), 
			new Dir(0, -1), 
			new Dir(1, -1)
	};
	
	// Random Fill
	public void randomFill() {

		this.clear();
		
		boolean[][] gBools = view.getBoardGrid().getGridBools();
		
		for (int i = 0; i < model.getSizeX(); i++) {
			for (int j = 0; j < model.getSizeY(); j++) {
				
				if (Math.random() > 0.8) {
					view.getBoardGrid().getGridColors()[i][j] = Color.BLACK;

					gBools[i][j] = true;
				}

			}
		}

		view.getBoardGrid().repaint();
	}
	
	@Override
	public void eventHandle(ViewEvent e) {
		// TODO Auto-generated method stub
		if (e.isUpdate) {
			if (!model.getTorus()) {
				this.updateGen();
			}
			else
				this.updateGenTorus();
		}
		else if (e.isRandom) {
			this.randomFill();
		}
		else if (e.isClear) {
			this.clear();
		}
		else if (e.isTorus) {
			model.setTorus(true);
		}
		else if (e.isChangeSize) {
			this.resizeBoard();
		}
		else if (e.isSpeed) {
			delay = e.getSpeed();
		}
		
		autoMode = e.isStart;
	}
	
	//Update with Torus
	public void updateGenTorus() {
		int black;
		int row; 
		int col;
		boolean[][] gBools = view.getBoardGrid().getGridBools();
		for (int i = 0; i < model.getSizeX(); i++) {
			for (int j =0; j< model.getSizeY(); j++) {
				black = 0;

				for (int r = 0; r < 8; r++) {
					Dir d = allDir[r];
					row = i + d.r;
					col = j + d.c;
					
					if (row < 0) {
						row = model.getSizeX()-1;
					}
					else if (row >= model.getSizeX()) {
						row = 0;
					}
					if (col < 0) {
						col = model.getSizeY()-1;
					}
					else if (col >= model.getSizeY() ) {
						col = 0;
					}
					
					if (gBools[row][col]) {
						black++;
					}
				}
				
				if (gBools[i][j]) {
					if (black >= model.getLowSurv() && black <= model.getHighSurv()) {
						
					}
					else {
						changeBoard[i][j] = true;
					}
				}
				else {
					if (black >= model.getLowBirth() && black <= model.getHighBirth()) {
						changeBoard[i][j] = true;
					}
				}
			}
		}
		
		for (int i = 0; i < model.getSizeX(); i++) {
			for (int j = 0; j < model.getSizeY(); j++) {

				if (changeBoard[i][j]) {
					if (view.getBoardGrid().getGridColors()[i][j] == Color.WHITE) {
						view.getBoardGrid().getGridColors()[i][j] = Color.BLACK;
					}
					else {
						view.getBoardGrid().getGridColors()[i][j] = Color.WHITE;
					}
					
					gBools[i][j] = !gBools[i][j];
					changeBoard[i][j] = false;
				}

			}
		}
		
		view.getBoardGrid().repaint();
	}
}
