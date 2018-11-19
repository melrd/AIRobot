package Soko;

public class Coordonate {
	int column,
		line;
	boolean state;
	
	public void Coordonate() {
		column = 0;
		line = 0;
		state = false;
	}
	
	public Coordonate(int pColumn, int pLine, boolean pState) {
		column = pColumn;
		line = pLine;
		state = pState;
	}

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
