package a8;


public class Model {
	private int sizeX;
	private int sizeY;
	private int lowSurv;
	private int highSurv;
	private int lowBirth;
	private int highBirth;
	private boolean[][] changeBoard;
	private boolean torus;

	/* Constructors */
	public Model() {
		sizeX = 10;
		sizeY = 10;
		lowBirth = 3;
		highBirth = 3;
		lowSurv = 2;
		highSurv = 3;
		torus = false;
		changeBoard = new boolean[this.getSizeX()][this.getSizeY()];

		for (int i = 0; i < this.getSizeX(); i++) {
			for (int j = 0; j<this.getSizeY(); j++) {
				changeBoard[i][j] = false;
			}
		}
	}
	
	public int getLowSurv() {
		return lowSurv;
	}

	public void setLowSurv(int lowSurv) {
		this.lowSurv = lowSurv;
	}

	public int getHighSurv() {
		return highSurv;
	}

	public void setHighSurv(int highSurv) {
		this.highSurv = highSurv;
	}

	public int getLowBirth() {
		return lowBirth;
	}

	public void setLowBirth(int lowBirth) {
		this.lowBirth = lowBirth;
	}

	public int getHighBirth() {
		return highBirth;
	}

	public void setHighBirth(int highBirth) {
		this.highBirth = highBirth;
	}

	public Model(int x, int y) {
		this.sizeX = x;
		this.sizeY = y;
	}


	/* Getters */
	public int getSizeX() {
		return sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}
	
	public boolean getTorus() {
		return torus;
	}

	/* Setters */
	public void setSizeX(int x) {
		sizeX = x;
	}
	
	public void setSizeY(int y) {
		sizeY = y;
	}
	
	public void setBirth(int b) {
		lowBirth = b;
	}
	
	public void setSurvive(int s) {
		lowSurv = s;
	}
	
	public void setTorus(boolean t) {
		torus = t;
	}
	
}
