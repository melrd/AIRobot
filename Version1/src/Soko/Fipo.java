package Soko;

import java.util.ArrayList;

public class Fipo {

	ArrayList<Node> fifo;
	
	public Fipo() {
		fifo = new ArrayList();
	}
	
	// return if the fifo is empty
	public boolean empty() {
		return fifo.isEmpty();
	}
	
	private void clear() {
		fifo = null;
	}
	
	private void add(Node node) {
		fifo.add(node);
		if (fifo.contains(node) != true)
			System.out.println("Not ine the file");
	}
	
	private void remove(Node node) {
		if((fifo.contains(node) == true) && (node == fifo.get(1))) {
			fifo.remove(1);
		}
	}
	
	public void nodeCheck(ArrayList fifo, Node nextNode, Node actualNode) {
		fifo.add(nextNode);
		fifo.remove(actualNode);
		System.out.println("Fifo : ");
		for(int i = 0; i < fifo.size() ; i++) 
			nextNode.printNode((Node)fifo.get(i));
		System.out.println("______");
	}
}
