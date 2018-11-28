package Soko;

import java.util.ArrayList;

import Soko.main.DIRECTION;

public class Graph {

	private int [][] G ; //values table (x,y) = (column, line)
	//private boolean [] visited ; // visited and existed vertex
	public final static int alpha = 999 ; //constant corresponding to infinity
	public ArrayList<int []> path = new ArrayList<>();
	
	//default constructor
	public Graph() {
		int Nmax = 30;
		G = new int [Nmax][Nmax];
		//visited = new boolean [Nmax];
		for (int i = 0 ; i<G.length ; i++) {
			//visited[i] = false;
			for (int j = 0 ; j<G.length ; j++) {
				G[i][j] = alpha;
			}
		}
	}
	
	//constructor with distances
	public Graph(Node node, char [][] map) {
		int Nmax = map.length;
		G = new int [Nmax][Nmax];
		//visited = new boolean [Nmax];
		for (int i = 0; i<G.length ; i++) {
			//
			/*if (i<map.length) {
				//visited[i] = true;
			}
			else {
				//visited[i] = false;
			}*/
			for (int j = 0; j<G.length; j++) {
				//copy values from the matrix for its size
				if ((i<map.length) && (j<map.length)) {
					G[i][j] = distance(map,node,j,i) ;
				}
				else {
					G[i][j] = alpha;
				}
			}
		}
	}
	
	//is this vertex exists = is the case is free
	public boolean isVertex(Node node, char [][] map) {
		return (node.movement(map, node.line, node.column));
	}
	
	//number of vertex = size of path 
	public int vertexCount() {
		return (path.size());
	}
	
	//is this distance exists
	public boolean isPath(Node nodeA, Node nodeB, char[][] map) {
		return ((isVertex(nodeA,map)) && (isVertex(nodeB,map)) && (G[nodeB.line][nodeB.column] != alpha));
	} 
	
	//returns table of successful vertex
	/*public int[] success(int i) {
		if ((i<0) || (i >= G.length)){
			System.out.println("Graph::success list : Error: out of limits.");
			System.exit(-1);
		}
		if (!isVertex(i)){
			System.out.println("Graph::success list : Error : this vertex does not exist.");
			System.exit(-1);
		}
		
		int [] table = new int [vertexCount()];
		int y = 0;
		
		while (y<vertexCount()) {
			for (int j = 0; j<G[i].length; j++) {
				if (G[i][j] != alpha) {
					table[y] = G[i][j];
					y++;
				}
			}
		}
		return table;
	}*/
	
	//get the distance's value of the node
	public int getDistance(Node nodeA, Node nodeB, char [][] map) {
		if ( (nodeB.line<0) || (nodeB.line >= G.length) || (nodeB.column<0) || (nodeB.column >= G.length) ) {
			System.out.println("Graph::getDistance : Error: out of limits.");
			System.exit(-1);
		}
		if (!isPath(nodeA, nodeB, map)){
			System.out.println("Graph::getDistance : Error: not a distance.");
			System.exit(-1);
		}
		return G[nodeB.line][nodeB.column];
	}
	
	//getter of the value table
	public int [][] getG(){
		return G;
	}
	
	//calculates the mathematical distance between two points
	public double mathDistance(int x1, int y1, int x2, int y2) {
		double xA = (double) x1;
		double xB = (double) x2;
		double yA = (double) y1;
		double yB = (double) y2;
		
		double distance = ( Math.sqrt (Math.pow((xB-xA),2) + Math.pow((yB-yA),2) )) ;
		
		return distance;
	}
	
	//calculates the practical distance between two points
	public int distance(char [][] map, Node node, int finalcolumn, int finalline) {
		
		Coordonate F = new Coordonate(finalcolumn,finalline, false);
		Coordonate C = new Coordonate(node.column,node.line,false); //abstract A to follow the path
		double dist = 0.0;
		int count = 0; //counts steps
		int xCo = 0; //change of column's coordinate : 0 or 1 or -1
		int yCo = 0; //change of line's coordinate : 0 or 1 or -1
		int [] coordinates = null; //chosen coordinates to add to path (line, column)
		
		while (C != F) { //travel of the path not finished
			//up
			if (node.movement(map, C.line, C.column + 1)) { //check if movement possible
				if (dist >= mathDistance(C.line, C.column+1, F.line, F.column)) { //shortest mathematical distance?
					dist = mathDistance(C.line, C.column+1, F.line, F.column);
					System.out.println(dist+" up");
					xCo = +1;					//save the best change of coordinate for C
					yCo = 0;
				}
			}
			//down
			if (node.movement(map, C.line, C.column - 1)) {
				if (dist >= mathDistance(C.line, C.column-1, F.line, F.column)) { 
					dist = mathDistance(C.line, C.column-1, F.line, F.column);
					System.out.println(dist+" down");
					xCo = -1;
					yCo = 0;
				}
			}
			//right
			if (node.movement(map, C.line + 1, C.column)) {
				if (dist >= mathDistance(C.line+1, C.column, F.line, F.column)) { 
					dist = mathDistance(C.line+1, C.column, F.line, F.column);
					System.out.println(dist+" right");
					xCo = 0;
					yCo = +1;
				}
			}
			//left
			if (node.movement(map, C.line - 1, C.column)) {
				if (dist >= mathDistance(C.line-1, C.column, F.line, F.column)) { 
					dist = mathDistance(C.line-1, C.column, F.line, F.column);
					System.out.println(dist+" left");
					xCo = 0;
					yCo = -1;
				}
			}
			
			C.setColumn(C.column + xCo); //change of coordinates for C
			C.setLine(C.line + yCo);
			coordinates[0] = C.line + yCo; 
			coordinates[1] = C.column + xCo;
			path.add(coordinates); //add coordinates to path
			count++;
			
			G[C.column + xCo][C.line + yCo] = count;
		}
		
		return count;
		
	}
	

	//returns the path composed of nodes
	public ArrayList<Node> bestDistance(char [][] map, Node startNode, Node goalNode){
		Tree tree = new Tree(startNode.coordinate.line, startNode.coordinate.column, null, null); // tree of possibles paths
		Fipo fifo = new Fipo(); // file to save the path
		ArrayList <Node> path = new ArrayList<>(); // list of all the nodes
		
		int [][] indicator = new int [map.length][map[0].length]; // table of values to find the path
		for (int i = 0; i<map.length; i++) {
			for (int j = 0; j<map[0].length;j++) {
				indicator [i][j] = alpha; //everyone has a high value to begin
			}
		}	
		indicator[startNode.coordinate.column][startNode.coordinate.line] = startNode.steps; // the value of the beginning
		
		for (Node currentNode : fifo.fifo) {
			if (!checkArrived(currentNode,goalNode)) {
				//up
				if (currentNode.GameRules(map,DIRECTION.UP) == true) { //check if movement is possible
					System.out.println(" test distance up ");
					if ((currentNode.roundTrip(DIRECTION.UP) == true)  //check if the movement is relevant
					&& (currentNode.steps+1 < indicator[currentNode.coordinate.column][currentNode.coordinate.line - 1])) { //and if the value is the best
						currentNode.addNode(currentNode,DIRECTION.UP); 
						fifo.nodeCheck(currentNode, currentNode.up); // add the nodes in the queue
					}
				}
				
				//down
				if (currentNode.GameRules(map,DIRECTION.DOWN) == true) { 
					System.out.println(" test distance down ");
					if ((currentNode.roundTrip(DIRECTION.DOWN) == true)  
					&& (currentNode.steps+1 < indicator[currentNode.coordinate.column][currentNode.coordinate.line + 1])) { 
						currentNode.addNode(currentNode,DIRECTION.DOWN); 
						fifo.nodeCheck(currentNode, currentNode.down); 
					}
				}
				
				//right
				if (currentNode.GameRules(map,DIRECTION.RIGHT) == true) { 
					System.out.println(" test distance right ");
					if ((currentNode.roundTrip(DIRECTION.RIGHT) == true)  
					&& (currentNode.steps+1 < indicator[currentNode.coordinate.column + 1][currentNode.coordinate.line])) { 
						currentNode.addNode(currentNode,DIRECTION.RIGHT); 
						fifo.nodeCheck(currentNode, currentNode.right); 
					}
				}
				
				//left
				if (currentNode.GameRules(map,DIRECTION.LEFT) == true) { 
					System.out.println(" test distance left ");
					if ((currentNode.roundTrip(DIRECTION.LEFT) == true)  
					&& (currentNode.steps+1 < indicator[currentNode.coordinate.column - 1][currentNode.coordinate.line])) { 
						currentNode.addNode(currentNode,DIRECTION.LEFT); 
						fifo.nodeCheck(currentNode, currentNode.left); 
					}
				}
			}
		}
			
			
		
		
		
		
		
		
		
		return path;
	}

	
	public boolean checkArrived (Node actual, Node goal) {
		if (actual == goal) {
			return true;
		}
		else return false;
	}
	
	
	
	//calculates the shortest path between two nodes
	//check if the result is null
	public ArrayList<int[]> pathCalcul(Node node, Coordonate goal, char[][] map) {
			int minDist = distance (map, node, goal.column, goal.line); //distance of the best path
						
			if (minDist == path.size()) {
				return path;
			} 
			else { return null;}
		}
	
}
