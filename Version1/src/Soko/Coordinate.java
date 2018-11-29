package Soko;

public class Coordinate {
	int column, // number of the coordinate  for line and column
		line;
	boolean state; // state of our object or if you transport something or not
	
	public Coordinate() { // implements at null all value
		column = 0;
		line = 0;
		state = false;
	}
	
	public Coordinate(int pColumn, int pLine) { // implements the coordinate
		column = pColumn;
		line = pLine;
		state = false;
	}

	//getter & setter for all variable of this class
	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}
	

}
