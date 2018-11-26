package Soko;



public class Dijkstra {

	public final static int INFINITE = 1000, //Integer.MAX_VALUE;
							alpha = - 999; //representing infinity
	private int x0; //to initialize functions : source
	private int [] V, //best distance from source (vertexes)
				P, //previous vertex of the optimal path (vertexes)
				D; //vertex's values of the Dijkstra path (values)
	private Graph g0;
	private boolean [] markedNodes; 
	private static int currentPathSize; //to simplify results

	
	
	//constructor : call the result of the best path
	public Dijkstra(int x, Graph g, Node nodeA, Node nodeB, char[][] map) {
		x0 = x;
		g0 = g;
		currentPathSize = g0.vertexCount();
		markedNodes = new boolean[currentPathSize];
		V = new int [currentPathSize]; //vertexes
		P = new int [currentPathSize]; //vertexes
		D = new int [currentPathSize]; //distances
		pathCalcul(nodeA, nodeB, map);	
		//g.distance(map, nodeA, nodeB.column, nodeB.line);
	}
	
	//calculates the shortest path
	public void pathCalcul(Node nodeA, Node nodeB, char[][] map) {
		int n = 0;
		for (int a = 0; a<currentPathSize; a++) {
			//markedNodes[a]=false;
			V[a] = -1; //
			P[a] = -1; //nodes do not have previous 
		}
		
		V[n] = x0;
		D[x0] = 0;
		P[x0] = x0;
		initDistMin(nodeA, nodeB, map);
		for (int i = 0; i<currentPathSize; i++) {
			if (!contains(V,i)) {
				//int c = vertexChoice();
				//markedNodes[c] = true;
				for 
				n++;
				V[n]=c;
				//adjDistMin(nodeA, nodeB,map);
			}
		}
	}
	
	//initialize minimum distance values
	public void initDistMin(Node nodeA, Node nodeB, char[][] map) {
		markedNodes[x0] = true;
		for (int i = 0; i< currentPathSize; i++) {
			if (g0.isPath(nodeA, nodeB, map)) {
				D[i] = g0.getG()[x0][i];
				P[i] = x0 ;
			}
		}
	}
	
	//return what is contains in
	public boolean contains(int[] S, int i) {
		return markedNodes[i];
	}
	
	//choose the closest vertex to the source
	/*public int vertexChoice() {
		int valeurMin = INFINITE;
		
		for (int i = 0; i<currentPathSize; i++) {
			if (!markedNodes[i]) {
				if (D[i] <= valeurMin) {
					valeurMin = D[i];
				}
			}
		}
		return valeurMin;
	}*/
	
	//adjust the value to be the closest
	/*public void adjDistMin(Node nodeA, Node nodeB, char[][] map) {
		for (int i = 0; i<currentPathSize; i++) {
			if (!contains(V,i)) {
				if (D[nodeB.line]+ pathDistance(nodeA, nodeB, map) < D[i]) {
					D[i] = D[nodeB.line] + pathDistance(nodeA, nodeB, map);
					P[i] = nodeB.line;
				}
			}
		}
	}*/

	//return the value of the distance
	public int pathDistance (Node nodeA, Node nodeB, char[][] map) {
		if (g0.isPath(nodeA, nodeB, map)) {
			return g0.getG()[nodeB.line][nodeB.column];
		}
		else return INFINITE;
	}
	

}

