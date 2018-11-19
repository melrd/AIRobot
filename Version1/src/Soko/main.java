package Soko;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class main {
	enum DIRECTION{
		UP,
		DOWN,
		LEFT,
		RIGHT,
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub	
		char[][] map = null;
		int startColumn = 0,
			startLine = 0,
			numberGoal = 0;
		Tree tree = null;
		Fipo fifo = new Fipo();
		
		map = readingFile();
		
		for(int i = 0;i < 9;i++) {
			for(int j = 0;j<10;j++) {
				System.out.print(map[i][j]);
				if(map[i][j] == 'M') {
					startColumn = j;
					startLine = i;
				}
			}
			System.out.println("\t");
		}
		
		tree = new Tree(startLine,startColumn,numberGoal); // start of the tree at M
		
		test(tree, map, fifo);

	}
	
		
	public static char[][] readingFile() {
		ArrayList<String> extractFile = new ArrayList<String>();
		char[][] map = null; 
		int numberColumn = 0, 
			numberLine = 0,
			numberGoal = 0;
		try{
		    File f = new File ("C:\\Users\\Asus\\Documents\\GitHub\\AIRobot\\Version1\\scheme1.txt");
		    FileReader fr = new FileReader (f);
		    BufferedReader br = new BufferedReader (fr);
		 
		    try	{// open file and read line by line, memorise everything in the array
		        String line = br.readLine(); 
		 
		        while (line != null) {
		            extractFile.add(line);
		            line = br.readLine();
		        }
		 
		        br.close();
		        fr.close();
		    }
		    catch (IOException exception){
		        System.out.println ("Error of reading : " + exception.getMessage());
		    }
		}
		catch (FileNotFoundException exception){
		    System.out.println ("File not found");
		}
		

		for(int i=0;i <extractFile.size(); i++) {
			if(i == 0) { // for the first line we extract the parameters of the map and initialize the map table
				String[] parts = extractFile.get(i).split(Pattern.quote(" "));
				numberColumn = Integer.parseInt(parts[0]); 
				numberLine = Integer.parseInt(parts[1]); 
				numberGoal = Integer.parseInt(parts[2]); 
				map = new char[numberLine][numberColumn]; 
			
			}else {// we put all the data in the map
				for(int j = 0;j < numberColumn; j++) {
					map[i-1][j] = extractFile.get(i).charAt(j);
				}
			}
		}
		return map;
	}           

	/**
	 * fonctionnel mais ultra sale
	 * private static void test(Tree tree, char[][] map) {
		System.out.println("--------------START---------------------");
		if (tree.GameRules(map,true,DIRECTION.DOWN, tree.node) == true) {
			if(tree.roundTrip(tree.node, DIRECTION.DOWN) == true)
				tree.addNode(tree.node, 0, +1,DIRECTION.DOWN);
			// ajouté dans la file
		}
		tree.node.printNode(tree.node.down);
		if (tree.GameRules(map,false,DIRECTION.LEFT, tree.node) == true) {
			if(tree.roundTrip(tree.node, DIRECTION.LEFT) == true)
				tree.addNode(tree.node, -1, 0,DIRECTION.LEFT);
			//ajouté dans la file
		}
		tree.node.printNode(tree.node.left);
		if (tree.GameRules(map,false,DIRECTION.UP, tree.node) == true) {
			if(tree.roundTrip(tree.node, DIRECTION.UP) == true)
				tree.addNode(tree.node, 0, -1,DIRECTION.UP);
			// ajouté dans la file
		}
		tree.node.printNode(tree.node.up);
		if (tree.GameRules(map,false,DIRECTION.RIGHT, tree.node) == true) { 
			if(tree.roundTrip(tree.node, DIRECTION.RIGHT) == true)
				tree.addNode(tree.node, +1, 0,DIRECTION.RIGHT);
			//ajouté dans la file
		}
		tree.node.printNode(tree.node.right);
		
		
		System.out.println("--------------GAUCHE---------------------");
		if (tree.GameRules(map,false,DIRECTION.DOWN, tree.node.left) == true) {
			if(tree.roundTrip(tree.node.left, DIRECTION.DOWN) == true)
				tree.addNode(tree.node.left, 0, +1,DIRECTION.DOWN);
			// ajouté dans la file
		}
		tree.node.printNode(tree.node.left.down);
		if (tree.GameRules(map,true,DIRECTION.LEFT, tree.node.left) == true) { 
			if(tree.roundTrip(tree.node.left, DIRECTION.LEFT) == true)
				tree.addNode(tree.node.left, -1, 0,DIRECTION.LEFT);
			//ajouté dans la file
		}
		tree.node.printNode(tree.node.left.left);
		if (tree.GameRules(map,false,DIRECTION.UP, tree.node.left) == true) {
			if(tree.roundTrip(tree.node.left, DIRECTION.UP) == true)
				tree.addNode(tree.node.left, 0, -1,DIRECTION.UP);
			// ajouté dans la file
		}
		tree.node.printNode(tree.node.left.up);
		if (tree.GameRules(map,false,DIRECTION.RIGHT, tree.node.left) == true) { 
			if(tree.roundTrip(tree.node.left, DIRECTION.RIGHT) == true)
				tree.addNode(tree.node.left, +1, 0,DIRECTION.RIGHT);
			//ajouté dans la file
		}
		tree.node.printNode(tree.node.left.right);
		
		System.out.println("---------------DROITE--------------------");
		if (tree.GameRules(map,false,DIRECTION.DOWN, tree.node.left.right) == true) {
			if(tree.roundTrip(tree.node.left.right, DIRECTION.DOWN) == true)
				tree.addNode(tree.node.left.right, 0, +1,DIRECTION.DOWN);
			// ajouté dans la file
		}
		tree.node.printNode(tree.node.left.right.down);
		if (tree.GameRules(map,true,DIRECTION.LEFT, tree.node.left.right) == true) { 
			if(tree.roundTrip(tree.node.left.right, DIRECTION.LEFT) == true)
				tree.addNode(tree.node.left.right, -1, 0,DIRECTION.LEFT);
			//ajouté dans la file
		}
		tree.node.printNode(tree.node.left.right.left);
		if (tree.GameRules(map,false,DIRECTION.UP, tree.node.left.right) == true) {
			if(tree.roundTrip(tree.node.left.right, DIRECTION.UP) == true)
				tree.addNode(tree.node.left.right, 0, -1,DIRECTION.UP);
			// ajouté dans la file
		}
		tree.node.printNode(tree.node.left.right.up);
		if (tree.GameRules(map,false,DIRECTION.RIGHT, tree.node.left.right) == true) { 
			if(tree.roundTrip(tree.node.left.right, DIRECTION.RIGHT) == true)
				tree.addNode(tree.node.left.right, +1, 0,DIRECTION.RIGHT);
			//ajouté dans la file
		}
		tree.node.printNode(tree.node.left.right.right);
	} */
	
	private static void test(Tree tree, char[][] map, Fipo fifo) {
		System.out.println("--------------START---------------------");
		tree.node.printNode(tree.node);
		testTest(map,tree.node, fifo);
		
		System.out.println("--------------GAUCHE---------------------");
		tree.node.printNode(tree.node.left);
		testTest(map,tree.node.left, fifo);

		System.out.println("---------------DROITE--------------------");
		tree.node.printNode(tree.node.left.right);
		testTest(map,tree.node.left.right, fifo);
	}
	
	private static void testTest(char[][] map, Node node, Fipo fifo) {
		if (node.GameRules(map,false,DIRECTION.DOWN, node) == true) {
			System.out.println("-- test D --");
			if(node.roundTrip(node, DIRECTION.DOWN) == true)
				node.addNode(node,DIRECTION.DOWN);
				fifo.nodeCheck(fifo.fifo, node, node.down);
		}
		node.printNode(node.down);
		if (node.GameRules(map,false,DIRECTION.LEFT, node) == true) {
			System.out.println("-- test L --");
			if(node.roundTrip(node, DIRECTION.LEFT) == true)
				node.addNode(node,DIRECTION.LEFT);
			fifo.nodeCheck(fifo.fifo, node, node.left);
		}
		node.printNode(node.left);
		if (node.GameRules(map,false,DIRECTION.UP, node) == true) {
			System.out.println("-- test U --");
			if(node.roundTrip(node, DIRECTION.UP) == true)
				node.addNode(node,DIRECTION.UP);
			fifo.nodeCheck(fifo.fifo, node, node.up);
		}
		node.printNode(node.up);
		if (node.GameRules(map,false,DIRECTION.RIGHT, node) == true) {
			System.out.println("-- test R --");
			if(node.roundTrip(node, DIRECTION.RIGHT) == true)
				node.addNode(node,DIRECTION.RIGHT);
			fifo.nodeCheck(fifo.fifo, node, node.right);
		}
		node.printNode(node.right);	
	}
}
