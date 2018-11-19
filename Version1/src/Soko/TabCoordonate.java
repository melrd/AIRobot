package Soko;

import java.util.ArrayList;

public class TabCoordonate {
	
	ArrayList <Coordonate> tabCoordonate;

	public void TabCoordonate() {
		tabCoordonate = new ArrayList();
	}
	
	private void addCoordonate(char type, int line, int column, boolean state) {
		Coordonate buffer = new Coordonate(column, line, type, state);
		if (tabCoordonate.contains(buffer) == false)
			if (tabCoordonate.add(buffer) == false)
				System.out.println("No add in the table");
	}
	
	
}
