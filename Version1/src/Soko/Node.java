package Soko;

import java.util.ArrayList;

import Soko.main.DIRECTION;

public class Node {
	int column, // for know actual position 
		line, //for know actual position
		steps; // for know which way is fast
	Node previous,
		up,
		down,
		left,
		right;
	ArrayList <Coordonate> tabGoal;
	ArrayList <Coordonate> tabDiamond;
	
	public Node(){ // implements the node without parameters
		column = 0;
		line = 0;
		steps = 0;
		
		previous = null;
		up = null;
		down = null;
		left = null;
		right = null;
		
		tabGoal = null;
		tabDiamond = null;
	}
	
	//only for the first node because no previous one
	public Node(int pLine, int pColumn, ArrayList <Coordonate> pTabGoal,ArrayList <Coordonate> pTabDiamond){ 
		column = pColumn;
		line = pLine;
		steps = 1;
		
		previous = null;
		up = null;
		down = null;
		left = null;
		right = null;
		
		tabGoal = pTabGoal;
		tabDiamond = pTabDiamond;
	}
	
	// for all the nodes excepts the first one
	public Node(int pColumn, int pLine, Node pPrevious){ 
		column = pColumn;
		line = pLine;
		steps = pPrevious.steps + 1;
		
		previous = pPrevious;
		up = null;
		down = null;
		left = null;
		right = null;
		
		tabGoal = previous.tabGoal;
		tabDiamond = previous.tabDiamond;
	}
	
	public void Node() {
	}
	
	public static void printNode(Node node) {
		if(node == null) // verify we have a node
			System.out.println("No node find");
		else { // print the coordonate of the node send
			System.out.println("Coordonnée : [" + node.line + "][" + node.column +"]");
			//System.out.println("Step : " + node.steps);
			//System.out.println("Goal non complete : " + node.goalsFree);
		}
	}

	public void addNode(Node actual, DIRECTION direction) {
		//for th good direction 
		switch (direction) {
		case  UP :
			actual.up = new Node(actual.column, actual.line-1, actual);
			if(actual.up == null)
				System.out.print("No add");
			break;
		case DOWN :
			actual.down = new Node(actual.column, actual.line+1, actual);
			if(actual.down == null)
				System.out.print("No add");
			break;
		case LEFT:
			actual.left = new Node(actual.column-1, actual.line, actual);
			if(actual.left == null)
				System.out.print("No add");
			break;
		case RIGHT:
			actual.right = new Node(actual.column+1, actual.line, actual);
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
