package Soko;

public class Coordonate {
	int column, // number of the coordonate  for line and column
		line;
	boolean state; // state of our object
	
	public Coordonate() { // implements at null all value
		column = 0;
		line = 0;
		state = false;
	}
	
	public Coordonate(int pColumn, int pLine, boolean pState) { // implements the coordonate
		column = pColumn;
		line = pLine;
		state = pState;
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
