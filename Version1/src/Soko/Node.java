package Soko;

import java.util.Vector;

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
	
	public Node(int pColumn, int pLine, int pGoalsFree){ //first node
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
			System.out.println("Step : " + node.steps);
			//System.out.println("Goal non complete : " + node.goalsFree);
		}
	}
}
