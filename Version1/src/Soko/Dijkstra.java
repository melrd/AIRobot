package Soko;

public class Dijkstra {

	public final static int INFINITE = 1000; //Integer.MAX_VALUE;
	public final static int alpha = - 999; //representing infinity
	private int x0; //to initialize functions : source
	private int [] V; //best distance from source (vertexes)
	private int [] P; //previous vertex of the optimal path (vertexes)
	private Graph g0;
	private int [] D; //vertex's values of the Dijkstra path (values)
	
	private boolean [] markedNodes; 
	private static int currentPathSize; //to simplify calculs
	
	//constructor : call the calcul of the best path
	public Dijkstra(int x, Graph g) {
		x0 = x;
		g0 = g;
		currentPathSize = g0.vertexCount();
		markedNodes = new boolean[currentPathSize];
		V = new int [currentPathSize]; //vertexes
		P = new int [currentPathSize]; //vertexes
		D = new int [currentPathSize]; //distances
		pathCalcul();	
	}
	
	//calculates the shortest path
	public void pathCalcul() {
		int n = 0;
		for (int a = 0; a<currentPathSize; a++) {
			markedNodes[a]=false;
			V[a] = -1; //
			P[a] = -1; //nodes do not have previous 
		}
		
		V[n] = x0;
		D[x0] = 0;
		P[x0] = x0;
		initDistMin();
		for (int i = 0; i<currentPathSize; i++) {
			if (!contains(V,i)) {
				int c = vertexChoice();
				markedNodes[c] = true;
				n++;
				V[n]=c;
				adjDistMin(c);
			}
		}
	}
	
	//initialize minimum distance values
	public void initDistMin() {
		markedNodes[x0] = true;
		for (int i = 0; i< currentPathSize; i++) {
			if (g0.isArch(x0, i)) {
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
	public int vertexChoice() {
		int valeurMin = INFINITE;
		int min = x0;
		
		for (int i = 0; i<currentPathSize; i++) {
			if (!markedNodes[i]) {
				if (D[i] <= valeurMin) {
					min = i ;
					valeurMin = D[i];
				}
			}
		}
		return valeurMin;
	}
	
	//adjust the value to be the closest
	public void adjDistMin(int n) {
		for (int i = 0; i<currentPathSize; i++) {
			if (!contains(V,i)) {
				if (D[n]+distance(n,i) < D[i]) {
					D[i] = D[n] + distance(n,i);
					P[i] = n;
				}
			}
		}
	}

	//return the value of the distance
	public int distance (int t, int s) {
		if (g0.isArch(t, s)) {
			return g0.getG()[t][s];
		}
		else return INFINITE;
	}
	

}

