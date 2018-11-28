package Soko;

import java.util.ArrayList;
import Soko.main.DIRECTION;

public class Node {
	int column, // for know actual position 
		line, //for know actual position
		steps; // for know which way is fast
	Node previous, // child node for each direction possible 
		up,
		down,
		left,
		right;
	ArrayList <Coordonate> tabGoal; // list of all goal with the coordonate & state
	ArrayList <Coordonate> tabDiamond; // list of all diamond with the coordonate & state
	
	public Node(){ // implements the node without any parameters
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
	
	//implements the first node because no previous one
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
	
	// implement all the nodes excepts the first one, because of the previous
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
	
	public static void printNode(Node node) { // permit to show the node we want
		if(node == null) // verify we have a node in the memory
			System.out.println("No node find");
		else { // print the coordonate
			System.out.println("Coordonnée : [" + node.line + "][" + node.column +"]");
			//System.out.println("Step : " + node.steps);
			//System.out.println("Goal non complete : " + node.goalsFree);
		}
	}

	public void addNode(Node actual, DIRECTION direction) {// add node according to the direction
		//for the direction send, we add a new node with the good coordonate then we check the memory
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

	public boolean GameRules(char[][] map, boolean state, DIRECTION direction, Node node) { //check if the movment is possible
		int line = 0,
			column = 0;
		
		//look the direction send, change coordonate who we looking for in comparaison with the actual one
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
		
		//check if the movement of the robot is possible or say it can't go in this direction
		if(movement(map, line, column) == false)
			return false;
		
		// check if we can move the diamonds : check if we are still in the map with the diamonds 
		//& if the movement is possible with the new coordonate of the diamond
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
	
	/* A VOIR SI ON SUPPRIME ?!?
	public boolean movement(char[][] map, int checkLine, int checkColumn) {
		if(map[checkLine][checkColumn] == 'X' ||
				map[checkLine][checkColumn] == ' ' ||
				map[checkLine][checkColumn] == 'J')
			return false;
		else
			return true;
	}*/
	
	public boolean movement(char[][] map, int checkLine, int checkColumn) {
		// for the coordonate send we check on the map if we are able to move in it
		if(map[checkLine][checkColumn] == 'X' ||
				map[checkLine][checkColumn] == ' ')
			return false;
		
		// check on the table of diamond if we don t have any diamond on our  box 
		for(Coordonate e : tabDiamond) {
			if (e.line == checkLine && e.column == checkColumn)
				return false;
		}
		return true;
	}
	
	public boolean roundTrip(Node node, DIRECTION direction) { // prevent round trip between 2 boxes
		if(node.previous == null)
			return true;
		
		// for each direction, that look the node previous and observe if the coordinate are different or not
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

	// verifier que ca fonctionne
	public boolean checkEnd() { // check if all diamonds are put in goals
		//observe all goal of our node and check if anyone is free or not
		for (Coordonate e : tabGoal) {
			if(e.state == false)
				return false;
		}
		return true;
	}
	
	// A MODIFIER PAS OK DU TOUT CORRESPOND PAS A CE QU ON veut
	public void changeGoal(int column, int line) {// change the state of the goal and the diamonds
		for(Coordonate e : tabGoal) { // run all the goal of th table
			if((e.column == column) && (e.line == line)) { // if coordonate of a goal correspond of the new coordonate we change the state f it
				e.state = true;
				for(Coordonate f : tabDiamond) { // run all diamond
					if((f.column == column) && (f.line == line)) // when we
						f.state = false;
				}
			}
		}
	}
}
