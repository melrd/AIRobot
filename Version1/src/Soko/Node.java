package Soko;

import java.util.ArrayList;

import Soko.main.DIRECTION;

public class Node {
	int steps; // for know which way is fast
	Coordonate coordinate; // give position and if he transport something or not
	Node previous, // child node for each direction possible 
		up,
		down,
		left,
		right;
	ArrayList <Coordonate> tabGoal; // list of all goal with the coordonate & state
	ArrayList <Coordonate> tabDiamond; // list of all diamond with the coordonate & state
	
	public Node(){ // implements the node without any parameters
		steps = 0;
		
		coordinate = null;
		
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
		steps = 1;
		
		coordinate = new Coordonate(pColumn, pLine, false);
		
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
		steps = pPrevious.steps + 1;
		
		coordinate = new Coordonate(pColumn, pLine, false);
		
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
	
	public void printNode() { // permit to show the node we want
		System.out.println("Coordinate : [" + coordinate.line + "][" + coordinate.column+"]");
		//System.out.println("Step : " + node.steps);
		//System.out.println("Goal non complete : " + node.goalsFree);
	}

	public void addNode(Node actual, DIRECTION direction) {// add node according to the direction
		//for the direction send, we add a new node with the good coordonate then we check the memory
		switch (direction) {
		case  UP :
			up = new Node(coordinate.column, coordinate.line-1, actual);
			if(up == null)
				System.out.print("No add");
			break;
		case DOWN :
			down = new Node(coordinate.column, coordinate.line+1, actual);
			if(down == null)
				System.out.print("No add");
			break;
		case LEFT:
			left = new Node(coordinate.column-1, coordinate.line, actual);
			if(left == null)
				System.out.print("No add");
			break;
		case RIGHT:
			right = new Node(coordinate.column+1, coordinate.line, actual);
			if(right == null)
				System.out.print("No add");
			break;
		default:
			System.out.println("No direction found");
		}
	}

	public boolean GameRules(char[][] map, DIRECTION direction) { //check if the movment is possible
		int lineChange = 0,
			columnChange  = 0;
		
		//look the direction send, change coordonate who we looking for in comparaison with the actual one
		switch (direction) {
			case UP :
				lineChange  = coordinate.line- 1;
				columnChange  = coordinate.column;
				break;
			case DOWN :
				lineChange  = coordinate.line +1;
				columnChange = coordinate.column;
				break;
			case LEFT :
				lineChange = coordinate.line;
				columnChange = coordinate.column-1;
				break;
			case RIGHT :
				lineChange = coordinate.line;
				columnChange = coordinate.column +1;
				break;
			default :
				return false;
		}
		
		//check if the movement of the robot is possible or say it can't go in this direction
		if(movement(map, lineChange, columnChange) == false)
			return false;
		
		// check if we can move the diamonds : check if we are still in the map with the diamonds 
		//& if the movement is possible with the new coordonate of the diamond
		if(coordinate.state == true) {
			switch (direction) {
				case UP :
					if(lineChange < 0)
						return false;
					return movement(map, lineChange-1, columnChange);
				case DOWN :
					if(lineChange < map.length)
						return false;
					return movement(map, lineChange+1, columnChange);
				case LEFT :
					if(columnChange < 0)
						return false; 
					return movement(map, lineChange, columnChange-1);
				case RIGHT : 
					if(lineChange < map[lineChange].length)
						return false;
					return movement(map, lineChange, columnChange+1);
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
	}
	*/
	
	private boolean movement(char[][] map, int checkLine, int checkColumn) {
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
	
	public boolean roundTrip(DIRECTION direction) { // prevent round trip between 2 boxes
		if(previous == null)
			return true;
		
		// for each direction, that look the node previous and observe if the coordinate are different or not
		switch (direction) {
		case  UP :
			if((previous.coordinate.column == coordinate.column) && (previous.coordinate.line == coordinate.line -1))
				return false;
			return true;
		case DOWN :
			if((previous.coordinate.column == coordinate.column) && (previous.coordinate.line == coordinate.line +1))
				return false;
			return true;
		case LEFT:
			if((previous.coordinate.column == coordinate.column-1) && (previous.coordinate.line == coordinate.line))
				return false;
			return true;
		case RIGHT:
			if((previous.coordinate.column == coordinate.column-1) && (previous.coordinate.line == coordinate.line))
				return false;
			return true;
		default:
			System.out.println("False direction");
			return false;
		}
	}

	// verifier que ca fonctionne
	private boolean checkEnd() { // check if all diamonds are put in goals
		//observe all goal of our node and check if anyone is free or not
		for (Coordonate e : tabGoal) {
			if(e.state == false)
				return false;
		}
		return true;
	}
	
	// A MODIFIER PAS OK DU TOUT CORRESPOND PAS A CE QU ON veut
	private void changeGoal(int column, int line) {// change the state of the goal and the diamonds
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
