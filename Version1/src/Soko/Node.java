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
		
		coordinate = new Coordonate(pColumn, pLine);
		
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
		
		coordinate = new Coordonate(pColumn, pLine);
		
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
		//System.out.println("Step : " + steps);
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

	public boolean GameRules(char[][] map, DIRECTION direction, int goalColumn, int goalLine) { //check if the movment is possible
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
		if(lineChange == goalLine && columnChange == goalColumn) { // check if it's not the goal, if it is we can go, useful for diamond
//			System.out.println("Same!");
			return true;}
		else if(movement(map, lineChange, columnChange) == false)
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

	public boolean checkEnd() { // check if all diamonds are put in goals
		//observe all goal of our node and check if anyone is free or not
		for (Coordonate e : tabGoal) {
			if(e.state == false)
				return false;
		}
		for (Coordonate e : tabDiamond) {
			if(e.state == false)
				return false;
		}
		return true;
	}
	
	
	//send the way
	public static ArrayList <Node> listNode(Node node, ArrayList <Node> temp){
		temp.add(0, node); // add the actual node at the beginning of the tab
		if(node.previous != null) // if parent node, call this function again
			listNode(node.previous,temp);
		return temp; //return the tab with all the node for the wayS
		
	}
	
	public Node copyShortWay(Node currentNode, ArrayList<Node> way) {
		for(Node f : way) {
			if(f.coordinate.column == currentNode.coordinate.column) {
				if(f.coordinate.line == currentNode.coordinate.line -1) {
					if(currentNode.down == null) 
						currentNode.down = new Node(f.coordinate.column,f.coordinate.line, currentNode);
					currentNode = currentNode.down;
				}
					
				else if(f.coordinate.line == currentNode.coordinate.line +1) {
					if(currentNode.up == null) 
						currentNode.up = new Node(f.coordinate.column, f.coordinate.line, currentNode);
					currentNode = currentNode.up;
				}
			}
				
			else if(f.coordinate.line == currentNode.coordinate.line) {
				if(f.coordinate.column == currentNode.coordinate.column -1 ) { 
					if(currentNode.right == null) 
						currentNode.right = new Node(f.coordinate.column, f.coordinate.line, currentNode);
					currentNode = currentNode.right ;
				}
				
				else if(f.coordinate.column == currentNode.coordinate.column +1) {
					if(currentNode.left == null)
						currentNode.left = new Node(f.coordinate.column, f.coordinate.line, currentNode);
					currentNode = currentNode.left;
				}
			}
		}
		currentNode.coordinate.state = true;
		currentNode.printNode();
		return currentNode;
	}

	public Node copyShortWay(Node currentNode, ArrayList<Node> way,  int diamond, int goal) {
		System.out.println(diamond);
		for(Node f : way) {
			if(f.coordinate.column == currentNode.coordinate.column) {
				if(f.coordinate.line == currentNode.coordinate.line -1) {
					if(currentNode.down == null) 
						currentNode.down = new Node(f.coordinate.column,f.coordinate.line, currentNode);
					currentNode = currentNode.down;
					currentNode.tabDiamond.get(diamond).line --;
				}
					
				else if(f.coordinate.line == currentNode.coordinate.line +1) {
					if(currentNode.up == null) 
						currentNode.up = new Node(f.coordinate.column, f.coordinate.line, currentNode);
					currentNode = currentNode.up;
					currentNode.tabDiamond.get(diamond).line++;
				}
			}
				
			else if(f.coordinate.line == currentNode.coordinate.line) {
				if(f.coordinate.column == currentNode.coordinate.column +1 ) { 
					if(currentNode.left == null) 
						currentNode.left = new Node(f.coordinate.column, f.coordinate.line, currentNode);
					currentNode = currentNode.left;
					currentNode.tabDiamond.get(diamond).column--;
				}
				
				else if(f.coordinate.column == currentNode.coordinate.column -1) {
					if(currentNode.right == null)
						currentNode.right = new Node(f.coordinate.column, f.coordinate.line, currentNode);
					currentNode = currentNode.right;
					currentNode.tabDiamond.get(diamond).column++;
				}
			}
		}
		currentNode.tabDiamond.get(diamond).state = true;
		currentNode.coordinate.state = false;
		currentNode.tabGoal.get(goal).state = true;
		return currentNode;
	}

}
