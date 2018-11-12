package Soko;

import java.util.Vector;

import Soko.main.DIRECTION;

public class Node {
	int column, // for know actual position 
		line, //for know actual position
		steps, // for know which way is fast
		goalsFree; // for know when the game is over
	Node previous,
		up,
		down,
		left,
		right;
	//Vector<Node> next;
	
	public Node(){
		column = 0;
		line = 0;
		steps = 0;
		goalsFree = 0;
		previous = null;
		//next = null;
		up = null;
		down = null;
		left = null;
		right = null;
	}
	
	public Node(int pLine, int pColumn, int pGoalsFree){ //first node
		column = pColumn;
		line = pLine;
		steps = 1;
		goalsFree = pGoalsFree;
		previous = null;
		//next = new Vector <Node>();
		up = null;
		down = null;
		left = null;
		right = null;
	}
	
	public Node(int pColumn, int pLine, int pGoalsFree, Node pPrevious){
		column = pColumn;
		line = pLine;
		steps = pPrevious.steps + 1;
		goalsFree = pGoalsFree;
		previous = pPrevious;
		//next = new Vector <Node>();
		up = null;
		down = null;
		left = null;
		right = null;
	}
	
	public void Node() {
		
	}
	
	public static void printNode(Node node) {
		if(node == null)
			System.out.println("No node find");
		else {
			System.out.println("Coordonnée : [" + node.line + "][" + node.column +"]");
			//System.out.println("Step : " + node.steps);
			//System.out.println("Goal non complete : " + node.goalsFree);
		}
	}

	public void addNode(Node actual, int changeColumn, int changeLine, DIRECTION direction) { // simplifier car on a la diection
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
		
		//change coordonate in case of witch way
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
		if(movement(map, line, column) == false)
			return false;
		
		// check if we can move the diamonds
		if(state == true) {
			switch (direction) {
				case UP :
					if(line < 0)
						return false;
					return movement(map, line-1, column);
				case DOWN :
					if(line < map.length)
						return false;
					return movement(map, line+1, column);
				case LEFT :
					if(column < 0)
						return false; 
					return movement(map, line, column-1);
				case RIGHT : 
					if(line < map[line].length)
						return false;
					return movement(map, line, column+1);
				default :
					return false;
			}
		}
		return true;
	}
	
	private boolean movement(char[][] map, int checkLine, int checkColumn) {
		if(map[checkLine][checkColumn] == 'X' ||
				map[checkLine][checkColumn] == ' ' ||
				map[checkLine][checkColumn] == 'J')
			return false;
		else
			return true;
	}
	
	public boolean roundTrip(Node node, DIRECTION direction) {
		if(node.previous == null)
			return true;
		
		switch (direction) {
		case  UP :
			if((node.previous.column == node.column) && (node.previous.line == node.line -1))
				return false;
			return true;
		case DOWN :
			if((node.previous.column == node.column) && (node.previous.line == node.line +1))
				return false;
			return true;
		case LEFT:
			if((node.previous.column == node.column-1) && (node.previous.line == node.line))
				return false;
			return true;
		case RIGHT:
			if((node.previous.column == node.column-1) && (node.previous.line == node.line))
				return false;
			return true;
		default:
			System.out.println("False direction");
			return false;
		}
	}

}
