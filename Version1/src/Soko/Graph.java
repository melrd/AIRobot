package Soko;

public class Graph {

	private int [][] G ; //values table
	private boolean [] visited ; // visited and existed vertex
	public final static int alpha = - 999 ; //constant corresponding to infinity
	
	//default construtor
	public Graph() {
		int Nmax = 1000;
		G = new int [Nmax][Nmax];
		visited = new boolean [Nmax];
		for (int i = 0 ; i<G.length ; i++) {
			visited[i] = false;
			for (int j = 0 ; j<G.length ; j++) {
				G[i][j] = alpha;
			}
		}
	}
	
	//constructor via matrix
	public Graph(int [][] matrix) {
		int Nmax = matrix.length;
		G = new int [Nmax][Nmax];
		visited = new boolean [Nmax];
		for (int i = 0; i<G.length ; i++) {
			//
			if (i<matrix.length) {
				visited[i] = true;
			}
			else {
				visited[i] = false;
			}
			for (int j = 0; j<G.length; j++) {
				//copy values from the matrix for its size
				if ((i<matrix.length) && (j<matrix.length)) {
					G[i][j] = matrix[i][j];
				}
				else {
					G[i][j] = alpha;
				}
			}
		}
	}
	
	//is this vertex exists
	public boolean isVertex(int i) {
		boolean result = false;
		if (!((i>visited.length) || (i<0))) { //check for limits
			result = visited[i];
		}
		return result;
	}
	
	//number of vertex
	public int vertexCount() {
		int count = 0;
		for (int i = 0; i<G.length; i++) {
			if (visited[i]) {
				count ++ ;
			}
		}
		return count;
	}
	
	//is this arch exists
	public boolean isArch(int i, int j) {
		return ((isVertex(i)) && (isVertex(j)) && (G[i][j] != alpha));
	}
	
	//returns table of successful vertex
	public int[] success(int i) {
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
	}
	
	//get the value of this arch
	public int getArchVal(int i, int j) {
		if ((i<0) || (i >= G.length) || (j<0) || (j >= G.length)){
			System.out.println("Graph::getArchVal : Error: out of limits.");
			System.exit(-1);
		}
		if (!isArch(i, j)){
			System.out.println("Graph::getArchVal : Error: not an arch.");
			System.exit(-1);
		}
		return G[i][j];
	}
	
	public int [][] getG(){
		return G;
	}
	
}
