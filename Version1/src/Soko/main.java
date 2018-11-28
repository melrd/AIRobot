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
		mapClean = new char[mapInitiate.length][mapInitiate[0].length]; // copy the initiate map in the clean one.
		
		
		// run the map for find the robot, in the same way we do the clean map
		for(int i = 0;i < mapInitiate.length;i++) {
			for(int j = 0;j<mapInitiate[i].length;j++) {
				System.out.print(mapInitiate [i][j]);
				if(mapInitiate [i][j] == 'M') {
					startColumn = j;
					startLine = i;
					mapClean[i][j] = '.';
				}
				if(mapInitiate[i][j] == 'G' || mapInitiate[i][j] == 'J')
					mapClean[i][j] = '.';
				mapClean[i][j] = mapInitiate[i][j];
			}
			System.out.println("\t");
		}
		
		// start of the tree at M and create the tab for diamond and goal
		tree = new Tree(startLine,startColumn, createCoordonate(mapInitiate , 'G'), createCoordonate(mapInitiate ,'J')); 
		fifo.fifo.add(tree.node); // WEIRD
		
		// run the fifo with a end point
		while (fifo.fifo.get(0).steps < 6) {
			System.out.println("Step : " + fifo.fifo.get(0).steps);
			// for the first node of fifo we check the movment possible for it
			checkAround(mapInitiate ,fifo.fifo.get(0), fifo);
			System.out.println("\n \n");
			if(fifo.fifo == null)
				break;
		}
		
		//test(tree, map, fifo);
		//Graph graph= new Graph();

	}
	
		
	private static char[][] readingFile() {
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

	private static void checkAround (char[][] map, Node node, Fipo fifo) {
		node.printNode();
		
		/** for the node send
		 * check each direction one by one
		 * we start by looking if we can move in this direction
		 * if we can, we check we don t already pass on it before (round trip)
		 * if we don t, we add this child node and we put this node in the file.
		 * then we print the node
		 */
		if (node.GameRules(map,DIRECTION.DOWN) == true) {
			System.out.println("-- test D --");
			if(node.roundTrip( DIRECTION.DOWN) == true) {
				node.addNode(node,DIRECTION.DOWN);
				fifo.nodeCheck(node, node.down);
			}
		}
		//node.down.printNode();
		printNode(node.down);
		
		if (node.GameRules(map,DIRECTION.LEFT) == true) {
			System.out.println("-- test L --");
			if(node.roundTrip(DIRECTION.LEFT) == true) {
				node.addNode(node,DIRECTION.LEFT);
				fifo.nodeCheck(node, node.left);
			}
		}
		//node.left.printNode();
		printNode(node.left);
		
		if (node.GameRules(map,DIRECTION.UP) == true) {
			System.out.println("-- test U --");
			if(node.roundTrip(DIRECTION.UP) == true) {
				node.addNode(node,DIRECTION.UP);
				fifo.nodeCheck(node, node.up);
			}
		}
		//node.up.printNode();
		printNode(node.up);
		
		if (node.GameRules(map,DIRECTION.RIGHT) == true) {
			System.out.println("-- test R --");
			if(node.roundTrip(DIRECTION.RIGHT) == true) {
				node.addNode(node,DIRECTION.RIGHT);
				fifo.nodeCheck(node, node.right);
			}
		}
		//node.right.printNode();
		printNode(node.right);
	}

	// check if the node is not empty before tho call the function in the node class
	private static void printNode(Node node) {
		if(node != null)
			node.printNode();
		else System.out.println("No node find");
	}

	//dans le while tant fifo != null
	private static void calculation(Fipo fifo, Node node) {
		//if ()
		for (Coordonate e : node.tabDiamond) {
			if(e.state == true) {
				/**
				 * calculer chemin le plus court
				 * mettre dans l arbre
				 * mettre le dernier noeud dans la file 
				 */
			}
		}
	}
	private static ArrayList<Coordonate> createCoordonate (char[][]map, char type) { //create table of coordonate
		ArrayList <Coordonate> tab = new ArrayList();
		boolean state;
		
		if (type == 'G') // adapt the state at each type of object
			state = false;
		else state = true;
		
		// for each box of the table if that correspond of the type who we looking for we added in the table
		for(int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++)
				if(map[i][j] == type) { 
					tab.add(new Coordonate(j,i,state));
				}
		}
		return tab;
	}

}
