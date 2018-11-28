package Soko;

import java.util.ArrayList;

import Soko.main.DIRECTION;

public class Tree {
	Node node;
	
	public Tree() {
		
	}
	
	public Tree(int line, int column, ArrayList <Coordonate> tabGoal, ArrayList <Coordonate> tabDiamond) {
		node = new Node(line, column, tabGoal, tabDiamond);
		node.printNode(node);
	}
	
	public void Tree() {

	}
}
