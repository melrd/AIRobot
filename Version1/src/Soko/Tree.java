package Soko;

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
	
	public void addNode(Node actual, int changeColumn, int changeLine, DIRECTION direction) {
		switch (direction) {
		case  UP :
			actual.up = new Node(actual.column + changeColumn, actual.line + changeLine, actual.goalsFree, actual);
			if(actual.up == null)
				System.out.print("No add");
			break;
		case DOWN :
			actual.down = new Node(actual.column + changeColumn, actual.line + changeLine, actual.goalsFree, actual);
			if(actual.down == null)
				System.out.print("No add");
			break;
		case LEFT:
			actual.left = new Node(actual.column + changeColumn, actual.line + changeLine, actual.goalsFree, actual);
			if(actual.left == null)
				System.out.print("No add");
			break;
		case RIGHT:
			actual.right = new Node(actual.column + changeColumn, actual.line + changeLine, actual.goalsFree, actual);
			if(actual.right == null)
				System.out.print("No add");
			break;
		default:
			System.out.println("No direction found");
		}
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

	/**public boolean GameRules(char[][]map, int checkColumn, int checkLine, boolean state, DIRECTION direction) {
		//check the movement of the robot
		if(movementCans(map, checkLine, checkColumn) == false ||
				map[checkLine][checkColumn] == 'M')
			return false;
		

		// check if we can move the diamonds
		else if(state == true) {
			switch (direction) {
				case UP :
					if(checkLine - 1 < 0)
						return false;
					else 
						return movementCans(map, checkLine-1, checkColumn);
					break;
				case DOWN :
					if(checkLine +1 < map.length)
						return false;
					else 
						return movementCans(map, checkLine+1, checkColumn);
					break;
				case LEFT :
					if(checkColumn -1 < 0)
						return false;
					else 
						return movementCans(map, checkLine, checkColumn -1);
					break;
				case RIGHT : 
					if(checkColumn +1 < map[checkLine].length)
						return false;
					else 
						return movementCans(map, checkLine, checkColumn +1);
					break;
				default :
					return true;
		}
		return false;
	}
	
	private boolean movementCans(char[][] map, int checkLine, int checkColumn) {
		if(map[checkLine][checkColumn] == 'X' ||
				map[checkLine][checkColumn] == ' ' ||
				map[checkLine][checkColumn] == 'J')
			return false;
		else
			return true;
	}
	*/
}
