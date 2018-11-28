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
		char[][] mapInitiate = null, //map for the reading part
				mapClean = null; // map without any diamonds or goal
		int startColumn = 0,
			startLine = 0,
			numberGoal = 0;
		Tree tree = null; // decision tree
		Fipo fifo = new Fipo(); // file for run the tree
		
		mapInitiate  = readingFile(); // reading the file and give the map of the game 
		
		
		mapClean = 	mapInitiate; // copy the initiate map in the clean one.
		// run the map for find the robot, in the same way we do the clean map
		for(int i = 0;i < mapInitiate.length;i++) {
			for(int j = 0;j<mapInitiate[i].length;j++) {
				System.out.print(mapInitiate [i][j]);
				if(mapInitiate [i][j] == 'M') {
					startColumn = j;
					startLine = i;
				}
				if(mapClean[i][j] == 'G' || mapClean[i][j] == 'J' || mapClean[i][j] == 'M')
					mapClean[i][j] = '.';
			}
			System.out.println("\t");
		}
		
		// start of the tree at M and create the tab for diamond and goal
		tree = new Tree(startLine,startColumn, createGoal(mapInitiate , 'G'), createGoal(mapInitiate ,'J')); 
		fifo.fifo.add(tree.node); // WEIRD
		
		// run the fifo with a end point
		while ((fifo.fifo.get(0).steps < 4) && (fifo.fifo != null)) {
			System.out.println("Step : " + fifo.fifo.get(0).steps);
			// for the first node of fifo we check the movment possible for it
			checkAround(mapInitiate ,fifo.fifo.get(0), fifo);
			System.out.println("\n \n");
		}
		
		//test(tree, map, fifo);
		//Graph graph= new Graph();

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

	/** NOT USEFUL ANYMORE
	private static void test(Tree tree, char[][] map, Fipo fifo) {
		System.out.println("--------------START---------------------");
		tree.node.printNode(tree.node);
		checkAround(map,tree.node, fifo);
		
		System.out.println("--------------LEFT---------------------");
		tree.node.printNode(tree.node.left);
		checkAround(map,tree.node.left, fifo);

		System.out.println("---------------RIGHT--------------------");
		tree.node.printNode(tree.node.left.right);
		checkAround(map,tree.node.left.right, fifo);
	}
	*/
	
	private static void checkAround (char[][] map, Node node, Fipo fifo) {
		node.printNode();
		
		/** for the node send
		 * check each direction one by one
		 * we start by looking if we can move in this direction
		 * if we can, we check we don t already pass on it before (round trip)
		 * if we don t, we add this child node and we put this node in the file.
		 * then we print the node
		 */
		if (node.GameRules(map,false,DIRECTION.DOWN) == true) {
			System.out.println("-- test D --");
			if(node.roundTrip( DIRECTION.DOWN) == true) {
				node.addNode(node,DIRECTION.DOWN);
				fifo.nodeCheck(node, node.down);
			}
		}
		//node.down.printNode();
		printNode(node.down);
		
		if (node.GameRules(map,false,DIRECTION.LEFT) == true) {
			System.out.println("-- test L --");
			if(node.roundTrip(DIRECTION.LEFT) == true) {
				node.addNode(node,DIRECTION.LEFT);
				fifo.nodeCheck( node, node.left);
			}
		}
		//node.left.printNode();
		printNode(node.left);
		
		if (node.GameRules(map,false,DIRECTION.UP) == true) {
			System.out.println("-- test U --");
			if(node.roundTrip(DIRECTION.UP) == true) {
				node.addNode(node,DIRECTION.UP);
				fifo.nodeCheck(node, node.up);
			}
		}
		//node.up.printNode();
		printNode(node.up);
		
		if (node.GameRules(map,false,DIRECTION.RIGHT) == true) {
			System.out.println("-- test R --");
			if(node.roundTrip(DIRECTION.RIGHT) == true) {
				node.addNode(node,DIRECTION.RIGHT);
				fifo.nodeCheck(node, node.right);
			}
		}
		//node.right.printNode();
		printNode(node.right);
	}

	
	// BETTER TO DO IN THE CLASS BUT HOW 
	// check if the node is not empty before tho call the function in the node class
	private static void printNode(Node node) {
		if(node != null)
			node.printNode();
		else System.out.println("No node find");
	}

	//CHANGER LE NOM POUR CREATECOorDONATE
	private static ArrayList<Coordonate> createGoal (char[][]map, char type) { //create table of coordonate
		ArrayList <Coordonate> tab = new ArrayList();
		boolean state;
		
		if (type == 'G') // adapt the state at each type of object
			state = false;
		else state = true;
		
		// for each box of the table if that correspond of the type who we ooking for we added in the table
		for(int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++)
				if(map[i][j] == type) { 
					tab.add(new Coordonate(j,i,state));
				}
		}
		return tab;
	}

}
