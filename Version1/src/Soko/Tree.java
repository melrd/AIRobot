package Soko;

import java.util.*;

import Soko.main.DIRECTION;

public class Tree {
	Node node;
	
	public Tree() {
		
	}
	
	public Tree(int pLine, int pColumn, int pGoals) {
		node = new Node(pLine, pColumn, pGoals);
		node.printNode(node);
	}
	
	public void Tree() {

	}
	
	public void addNode(Node actual, int changeColumn, int changeLine) {
		actual.next.add(new Node(actual.column + changeColumn, actual.line + changeLine, actual.goalsFree, actual));
	}
	
	public boolean GameRules(char[][]map, int checkColumn, int checkLine, boolean state, DIRECTION direction) {
		//check the movement of the robot
		if(map[checkLine][checkColumn] == 'X' ||
				map[checkLine][checkColumn] == ' ' ||
				map[checkLine][checkColumn] == 'J' ||
				map[checkLine][checkColumn] == 'M')
			return false;
		
		// check if we can move the diamonds
		else if(state == true) {
			if(direction == DIRECTION.UP) {
				if(checkLine - 1 < 0)
					return false;
				else if(map[checkLine - 1][checkColumn] == 'X' ||
						map[checkLine - 1][checkColumn] == ' ' ||
						map[checkLine - 1][checkColumn] == 'J')
					return false;
				else
					return true;
			}
			else if(direction == DIRECTION.DOWN) {
				if(checkLine +1 < map.length)
					return false;
				else if(map[checkLine+1][checkColumn] == 'X' ||
						map[checkLine+1][checkColumn] == ' ' ||
						map[checkLine+1][checkColumn] == 'J')
					return false;
				else
					return true;
			}
			else if(direction == DIRECTION.LEFT) {
				if(checkColumn -1 < 0)
					return false;
				else if(map[checkLine][checkColumn-1] == 'X' ||
						map[checkLine][checkColumn-1] == ' ' ||
						map[checkLine][checkColumn-1] == 'J')
					return false;
				else
					return true;
			}
			else if(direction == DIRECTION.RIGHT) {
				if(checkColumn +1 < map[checkLine].length)
					return false;
				else if(map[checkLine][checkColumn+1] == 'X' ||
						map[checkLine][checkColumn+1] == ' ' ||
						map[checkLine][checkColumn+1] == 'J')
					return false;
				else
					return true;
			}
		}
		else
			return true;
		return false;
	}

	
}
