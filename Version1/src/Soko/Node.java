package Soko;

import java.util.*;

public class Node {
	int column, // for know actual position 
		line, //for know actual position
		steps, // for know wich way is fast
		goalsFree; // for know when the game is over
	Node beforePrevious,
		previous,
		next;
	
	public Node(){
		column = 0;
		line = 0;
		steps = 0;
		goalsFree = 0;
		previous = null;
		next = null;
		beforePrevious = null;
	}
	
	public Node(int pColumn, int pLine, int pGoalsFree){ //first node
		column = pColumn;
		line = pLine;
		steps = 1;
		goalsFree = pGoalsFree;
		beforePrevious = null;
		previous = null;
		next = new Node();
	}
	public Node(int pColumn, int pLine, int pGoalsFree, Node pPrevious){
		column = pColumn;
		line = pLine;
		steps = pPrevious.steps + 1;
		goalsFree = pGoalsFree;
		beforePrevious = pPrevious.previous;
		previous = pPrevious;
		next = new Node();
	}
	public void Node() {
		
	}
}
