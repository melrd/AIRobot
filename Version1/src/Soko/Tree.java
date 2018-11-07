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
	
	public boolean GameRules(char[][]map, boolean state, DIRECTION direction, Node node) {
		int line = 0,
			column = 0;

		switch (direction) {
			case UP :
				line = node.line- 1;
				column = node.column;
				break;
			case DOWN :
				line = node.line +1;
				column = node.column;
				break;
			case LEFT :
				line = node.line;
				column = node.column-1;
				break;
			case RIGHT :
				line = node.line;
				column = node.column +1;
				break;
			default :
				return false;
		}

		//check the movement of the robot
		if(movementCans(map, line, column) == false ||
				map[node.line][node.column] == 'M')
			return false;

		// check if we can move the diamonds
		if(state == true) {
			switch (direction) {
				case UP :
					if(line < 0)
						return false;
					return movementCans(map, line, column);
				case DOWN :
					if(line < map.length)
						return false;
					return movementCans(map, line, column);
				case LEFT :
					if(column < 0)
						return false; 
					return movementCans(map, line, column);
				case RIGHT : 
					if(line < map[line].length)
						return false;
					return movementCans(map, line, column);
				default :
					return false;
			}
		}
		return true;
	}
	
	private boolean movementCans(char[][] map, int checkLine, int checkColumn) {
		if(map[checkLine][checkColumn] == 'X' ||
				map[checkLine][checkColumn] == ' ' ||
				map[checkLine][checkColumn] == 'J')
			return false;
		else
			return true;
	}
	
}
