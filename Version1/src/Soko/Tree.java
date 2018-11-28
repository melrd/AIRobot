package Soko;

import java.util.ArrayList;

import Soko.main.DIRECTION;

public class Tree {
	Node node; // start point of our tree
	
	public Tree() {
		
	}
	
	public Tree(int line, int column, ArrayList <Coordonate> tabGoal, ArrayList <Coordonate> tabDiamond) {
		node = new Node(line, column, tabGoal, tabDiamond); // add  our new node, start the tree
		node.printNode();
	}
	
	public void Tree() {

	}
}
