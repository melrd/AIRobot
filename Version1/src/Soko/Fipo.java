package Soko;

import java.util.ArrayList;

public class Fipo {

	ArrayList<Node> fifo;
	
	public Fipo() {
		fifo = new ArrayList();
	}
	
	private void clear() {
		fifo = null;
	}
	
	private void add(Node node) {
		fifo.add(node);
		if (fifo.contains(node) != true)
			System.out.println("Not in the file");
	}
	
	private void remove(Node node) {
		if(fifo.contains(node) == true)
			fifo.remove(fifo.indexOf(node));
	}
	
	
	public void nodeCheck(ArrayList fifo, Node actualNode, Node nextNode) {
		fifo.add(nextNode);
		fifo.remove(actualNode);
		System.out.println("______Fifo______");
		for(int i = 0; i < fifo.size() ; i++) 
			nextNode.printNode((Node)fifo.get(i));
		System.out.println("__________________");
	}
}
