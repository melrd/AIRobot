package Soko;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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
		boolean endOfTree = false;
		ArrayList <Node> temp = new ArrayList();
		
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
				else if(mapInitiate[i][j] == 'G' || mapInitiate[i][j] == 'J')
					mapClean[i][j] = '.';
				else
					mapClean[i][j] = mapInitiate[i][j];
			}
			System.out.println("\t");
		}
		
		
		for(int i = 0;i < mapInitiate.length;i++) {
			for(int j = 0;j<mapInitiate[i].length;j++) {
				System.out.print(mapClean [i][j]);
			}
			System.out.println("\t");
		}
		
		// start of the tree at M and create the tab for diamond and goal
		tree = new Tree(startLine,startColumn, createCoordonate(mapInitiate , 'G'), createCoordonate(mapInitiate ,'J')); 
		fifo.addNode(tree.node);
		
		System.out.println("\n");
		
		
		// run the fifo with a end point
		/**while (fifo.fifo.get(0).steps < 6) {
			// for the first node of fifo we check the movment possible for it
			checkAround(mapInitiate ,fifo.fifo.get(0), fifo);
			if(fifo.fifo == null)
				break;
		}*/
		Node last = new Node();
		
		while(fifo.fifo.size() > 0 && endOfTree == false) {
			// besoin du dernier des noeuds
			Graph graph = new Graph();
			printNode(fifo.fifo.get(0));
			endOfTree = calculation(fifo, mapClean, graph);
			last = graph.finalN;
			try {
	            Thread.sleep(100);
	        } catch (InterruptedException f) {
	            f.printStackTrace();
	        }
		}
		
		
		System.out.println("\n");
		System.out.println("Way");
		//return the way for get it
		temp = fifo.fifo.get(0).listNode(last, temp);
		System.out.println(temp.size());
		for (Node e : temp)
			printNode(e); 
		
		//ecriture du fichier pour le robot
		writtingFile(temp);

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
/**		if (node.GameRules(map,DIRECTION.DOWN) == true) {
//			System.out.println("-- test D --");
			if(node.roundTrip( DIRECTION.DOWN) == true) {
				node.addNode(node,DIRECTION.DOWN);
				fifo.nodeCheck(node, node.down);
			}
		}
//		printNode(node.down);
		
		if (node.GameRules(map,DIRECTION.LEFT) == true) {
//			System.out.println("-- test L --");
			if(node.roundTrip(DIRECTION.LEFT) == true) {
				node.addNode(node,DIRECTION.LEFT);
				fifo.nodeCheck(node, node.left);
			}
		}
//		printNode(node.left);
		
		if (node.GameRules(map,DIRECTION.UP) == true) {
//			System.out.println("-- test U --");
			if(node.roundTrip(DIRECTION.UP) == true) {
				node.addNode(node,DIRECTION.UP);
				fifo.nodeCheck(node, node.up);
			}
		}
//		printNode(node.up);
		
		if (node.GameRules(map,DIRECTION.RIGHT) == true) {
//			System.out.println("-- test R --");
			if(node.roundTrip(DIRECTION.RIGHT) == true) {
				node.addNode(node,DIRECTION.RIGHT);
				fifo.nodeCheck(node, node.right);
			}
		}
		//printNode(node.right);
	*/}

	// check if the node is not empty before tho call the function in the node class
	private static void printNode(Node node) {
		if(node != null)
			node.printNode();
		else System.out.println("No node find");
	}


	private static boolean calculation(Fipo fifo, char [][] map, Graph graph) {
		ArrayList<Node> temp = new ArrayList();
		int positionDiamond = 999;
		if(fifo.fifo.get(0) == null)
			return false;
		
		Node node = fifo.fifo.get(0);
		
		if (node.coordinate.state == false) { // observe if we transport a diamond or not
			for (Coordonate e : node.tabDiamond) { // we don t have any diamond so we are looking for one
				if(e.state == false) { // looking for a diamond who we can move
					//calcul du chemin retourne une liste de noeud
					temp = graph.bestDistance(map, node, node.tabDiamond.get(node.tabDiamond.indexOf(e)));
					//temp.add(temp.size(),graph.finalN);
//					System.out.println("best distance");
					for (Node z : temp)
						printNode(z);
					// add the way in the tree and add the last node of the way ine the file
					fifo.nodeCheck(node, node.copyShortWay(node, temp));
					System.out.println("Diamond \n\n");
					//printNode(temp.get(temp.size()-1));
			        try {
			            Thread.sleep(100);
			        } catch (InterruptedException f) {
			            f.printStackTrace();
			        }
					}
			}
		}
		else { // we have a diamond so we are looking for a goal
			for (Coordonate e : node.tabGoal) {
				if(e.state == false) {
					// calcul du chemin
					temp = graph.bestDistance(map, node, node.tabGoal.get(node.tabGoal.indexOf(e)));
//					temp.add(temp.size(),graph.finalN);
					for (Coordonate f : node.tabDiamond) {
						if(f.line == temp.get(0).coordinate.line && f.column == temp.get(0).coordinate.column)
							positionDiamond = node.tabDiamond.indexOf(f);
					}
					// add the way in the tree and add the last node of the way int the file
					fifo.nodeCheck(node, node.copyShortWay(node, temp, positionDiamond, node.tabGoal.indexOf(e)));
					System.out.println("Goal \n\n");
			        try {
			            Thread.sleep(100);
			        } catch (InterruptedException f) {
			            f.printStackTrace();
			        }
				}
			}
		}
		
//		System.out.println("Main file");
//		for(Node e : fifo.fifo)
//			printNode(e);
		
		if(node.checkEnd())
			return true;
		else return false;
	}
	
	private static ArrayList<Coordonate> createCoordonate (char[][]map, char type) { //create table of coordonate
		ArrayList <Coordonate> tab = new ArrayList();
		
		// for each box of the table if that correspond of the type who we looking for we added in the table
		for(int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++)
				if(map[i][j] == type) { 
					tab.add(new Coordonate(j,i));
				}
		}
		return tab;
	}
	
	
	private static void writtingFile(ArrayList <Node> way){
		File f = new File("Solution");
		
		try {
			 FileWriter fw = new FileWriter (f);
			
			 for(Node e : way) {
				 fw.write(e.coordinate.line + ";" + e.coordinate.column + ";");
				 if(e.coordinate.state == true)
					 fw.write("0;");
				 else
					 fw.write("1; \r\n");
			 }
			fw.close();
		}
		catch(IOException exception){
			System.out.println("Error for writting" + exception.getMessage());
		}
		
	}
}
