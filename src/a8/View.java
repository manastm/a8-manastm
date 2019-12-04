package a8;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


// TODO: To handle button click events, implement ActionListener
// TODO: To handle keyboard events, implement KeyListener
public class View extends JPanel implements ActionListener, KeyListener, ChangeListener{

	public static final int MAX_BOARD = 800;
	
	//ui components
	private JTextField gridSize;
	private JButton changeSize;
	private JButton update;
	private JButton random;
	private JButton clear;
	private JPanel updatePanel;
	private Grid boardGrid;
	private JCheckBox torus;
	private JSlider speed;
	private JButton start;
	private JButton stop;
	private int numBox;
	
	private CellListener listener;
	
	// Constructor sets up the view
	public View() {
		
		updatePanel = new JPanel();
		
		changeSize = new JButton("Change Size");
		changeSize.setName("size");
		changeSize.addActionListener(this);
		
		update = new JButton("New Generation");
		update.setName("update");
		update.addActionListener(this);
		
		random = new JButton("Random Fill");
		random.setName("random");
		random.addActionListener(this);
		
		clear = new JButton("Clear");
		clear.setName("clear");
		clear.addActionListener(this);
		
		torus = new JCheckBox("Torus Mode");
		torus.setName("torus");
		torus.addActionListener(this);
		
		speed = new JSlider(10, 1000, 50);
		speed.setName("speed");
		speed.addChangeListener(this);
		
		start = new JButton("Start");
		start.setName("start");
		start.addActionListener(this);
		
		stop = new JButton("Stop");
		stop.setName("stop");
		stop.addActionListener(this);
		
		gridSize = new JTextField("10");
		
		updatePanel.add(changeSize);
		updatePanel.add(gridSize);
		updatePanel.add(update);
		updatePanel.add(random);
		updatePanel.add(clear);
		updatePanel.add(torus);
		updatePanel.add(speed);
		updatePanel.add(start);
		updatePanel.add(stop);
		this.add(updatePanel);
		
		numBox = 10;
		boardGrid = new Grid(numBox, numBox, MAX_BOARD/numBox);
		this.add(boardGrid);
		
		
	}
	
	/*Getters*/ 
	public JButton getUpdateButton() {
		return update;
	}
	
	public int getGridSize() {
		numBox = Integer.parseInt(gridSize.getText());
		return numBox;
	}
	
	public Grid getBoardGrid() {
		return boardGrid;
	}
	
	/*Setters*/
	
	public void setBoardGrid(Grid grid) {
		boardGrid = grid;
	}
	
	//necessary methods
	
	public void resizeGrid(int size) {
		numBox = size;
		gridSize.setText("" + size);
		this.remove(boardGrid);
		boardGrid = new Grid(size, size, MAX_BOARD/size);
		this.add(boardGrid);
		revalidate();
		repaint();
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		listener.eventHandle(new ViewEvent(e));
	}
	
	public void setListener(CellListener cell) {
		this.listener = cell;
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		listener.eventHandle(new ViewEvent(e));
	}

	class Grid extends JPanel implements MouseListener {

		
		private int gridRows; // Number of rows of squares.
		private int gridCols; // Number of columns of squares.
		private Color[][] gridColor; /* gridColor[r][c] is the color for square in row r, column c; 
		                                if it  is null, the square has the panel's background color.*/
		private Color lineColor; // Color for lines drawn between squares; if null, no lines are drawn.
		private boolean[][] gridBools;

		public Grid(int rows, int columns, int preferredSquareSize) {
			gridColor = new Color[rows][columns]; // Create the array that stores square colors.
			gridRows = rows;
			gridCols = columns;
			lineColor = Color.BLACK;
			setPreferredSize( new Dimension(preferredSquareSize*columns, 
					preferredSquareSize*rows) );
			setBackground(Color.WHITE); // Set the background color for this panel.
			addMouseListener(this);     // Mouse actions will call methods in this object.
			gridBools = new boolean[gridRows][gridCols];
			for (int i = 0; i<gridRows; i++) {
				for (int j=0; j<gridCols; j++) {
					gridBools[i][j] = false;
				}
			}
		}
		
		//Getters
		public boolean[][] getGridBools() {
			return gridBools;
		}
		
		public Color[][] getGridColors() {
			return gridColor;
		}
		
		//Setters
		public void setGridBools(boolean[][] gridBools) {
			this.gridBools = gridBools;
		}
		
		public void setGridColors(Color[][] gridColors) {
			this.gridColor = gridColors;
			repaint();
		}
		
		private int findRow(int pixelY) {
			return (int)(((double)pixelY)/getHeight()*gridRows);
		}
		

		private int findColumn(int pixelX) {
			return (int)(((double)pixelX)/getWidth()*gridCols);
		}
		

		public void mousePressed(MouseEvent evt) {
			int row, col; // the row and column in the grid of squares where the user clicked.
			row = findRow( evt.getY() );
			col = findColumn( evt.getX() );
			if (gridColor[row][col] == Color.BLACK) {
				gridColor[row][col] = Color.WHITE;
				gridBools[row][col] = false;
			}
			else {
				gridColor[row][col] = Color.BLACK;
				gridBools[row][col] = true;
			}
			repaint(); // Causes the panel to be redrawn, by calling the paintComponent method.
		}
		

		protected void paintComponent(Graphics g) {
			g.setColor(getBackground());
			g.fillRect(0,0,getWidth(),getHeight());
			int row, col;
			double cellWidth = (double)getWidth() / gridCols;
			double cellHeight = (double)getHeight() / gridRows;
			for (row = 0; row < gridRows; row++) {
				for (col = 0; col < gridCols; col++) {
					if (gridColor[row][col] != null) {
						int x1 = (int)(col*cellWidth);
						int y1 = (int)(row*cellHeight);
						int x2 = (int)((col+1)*cellWidth);
						int y2 = (int)((row+1)*cellHeight);
						g.setColor(gridColor[row][col]);
						g.fillRect( x1, y1, (x2-x1), (y2-y1) );
					}
				}
			}
			if (lineColor != null) {
				g.setColor(lineColor);
				for (row = 1; row < gridRows; row++) {
					int y = (int)(row*cellHeight);
					g.drawLine(0,y,getWidth(),y);
				}
				for (col = 1; col < gridRows; col++) {
					int x = (int)(col*cellWidth);
					g.drawLine(x,0,x,getHeight());
				}
			}
		}
		

		public void mouseClicked(MouseEvent evt) { }
		public void mouseEntered(MouseEvent evt) {	}
		public void mouseExited(MouseEvent evt) { }
		public void mouseReleased(MouseEvent evt) { }

		
	} // end class Grid

	
}
