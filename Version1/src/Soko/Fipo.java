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
	
	// CHANGER LE NOM CAR CONFUSION POSSIBLE ENTRE LA METHODE ET L APPEL AUTOMATIQUE
	private void add(Node node) { // new node to check after to add in our fifo
		// add the case of the node
		fifo.add(node);
		// check if the node is added
		if (fifo.contains(node) != true)
			System.out.println("Not in the file");
	}
	
	// CHANGE THE NAME FOR DON T HAVE ANY CONFUSION
	private void remove(Node node) {
		// delete the node observe
		fifo.remove(fifo.indexOf(node));
	}
	
	
	public void nodeCheck(ArrayList fifo, Node actualNode, Node nextNode) {
		fifo.add(nextNode); // add the new node in the file
		if(fifo.contains(actualNode) == true) // check if the node check is in the file
			fifo.remove(actualNode); // delete the node who's check
		/**System.out.println("______Fifo______");
		for(int i = 0; i < fifo.size() ; i++) 
			nextNode.printNode((Node)fifo.get(i));
		System.out.println("__________________");*/
	}
}
