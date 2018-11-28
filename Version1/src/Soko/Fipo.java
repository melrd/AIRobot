package Soko;

import java.util.ArrayList;

public class Fipo {

	ArrayList<Node> fifo;
	
	public Fipo() {
		// implements an array for the file
		fifo = new ArrayList();
	}
	
	private void clear() { // to make the fifo empty
		fifo = null;
	}
	
	public void addNode(Node node) { // new node to check after to add in our fifo
		// add the case of the node in the List
		fifo.add(node);
		// check if the node is added
		if (fifo.contains(node) != true)
			System.out.println("Not in the file");
	}
	
	private void removeNode(Node node) {
		// delete the node observe in the table
		fifo.remove(fifo.indexOf(node));
	}
	
	
	public void nodeCheck(Node actualNode, Node nextNode) {
		addNode(nextNode); // add the new node in the file
		if(fifo.contains(actualNode) == true) // check if the node check is in the file
			removeNode(actualNode); // delete the node who's check
		System.out.println("______Fifo______");
		for(int i = 0; i < fifo.size() ; i++) 
			fifo.get(i).printNode();
		System.out.println("__________________");
	}
}
