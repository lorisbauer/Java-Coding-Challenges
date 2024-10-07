import java.awt.Color;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.HashMap;

public class Djikstra {
	Node s;
	Node t;
	int n;
	int m;
	Graph G;
	
	boolean[][] visited;
	int[][] dist;
	boolean finished = false;
	boolean found;
	
	int steps = 1000;
	int alpha = 100;
	
	PriorityQueue<Pair> pq;
	HashMap<Node, Node> cameFrom = new HashMap<Node, Node>(); 
	ArrayList<Color> Gradient = Panel.generateGradient(new Color(200, 0, 0, alpha), new Color(255, 255, 0, alpha), steps);
	
	
	Djikstra(Node s, Node t, Graph G) {
		this.s = s;
		this.t = t;
		this.n = G.n;
		this.m = G.m;
		this.G = G;
		
		init();
	}
	
	public void init() {
		visited = new boolean[n][m];
		dist = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				dist[i][j] = Integer.MAX_VALUE;
			}
		}
		dist[s.x][s.y] = 0;
		
		pq = new PriorityQueue<Pair>(n * m);
	    pq.add(new Pair(0, s));		
	}
	
	public ArrayList<Node> getNeighbors(int n, int m, Node node) {
	    ArrayList<Node> neighbors = new ArrayList<Node>();
	    
	    for (int i = Math.max(0, node.x - 1); i <= Math.min(n - 1, node.x + 1); i++) {
	      for (int j = Math.max(0, node.y - 1); j <= Math.min(m - 1, node.y + 1); j++) {
	        if (!G.grid[i][j].blocked && !(node.x == i && node.y == j) && (node.x == i || node.y == j)) {
	          neighbors.add(G.grid[i][j]);
	        }
	      }
	    }
	    return neighbors;
	}
	
	public void constructPath(Node current) {
		current.isPath = true;
		if (cameFrom.containsKey(current)) {
			constructPath(cameFrom.get(current));
		}
	}
	
	public Color getColor(int dist) {
		int index = (int) Panel.lerp(dist, 0, 4000, 0, steps);
		return Gradient.get(index);
	}
	
	public void shortestPath() {
		if (pq.isEmpty() || finished) {
			found = true;
		}
		
		if (found) {
			constructPath(t);
			return;
		}
		
		Pair current = pq.poll();
    	visited[current.node.x][current.node.y] = true;
    	
    	for (Node other : getNeighbors(n, m, current.node)) {
    		int weight = Math.abs(other.height - current.node.height);
    		
    		if (!visited[other.x][other.y] || dist[current.node.x][current.node.y] + weight < dist[other.x][other.y]) {
    			if (dist[current.node.x][current.node.y] + weight < dist[other.x][other.y]) {
    				cameFrom.put(other, current.node);
    			}
    			dist[other.x][other.y] = Math.min(dist[other.x][other.y], dist[current.node.x][current.node.y] + weight);
    			visited[other.x][other.y] = true;
    			pq.add(new Pair(dist[other.x][other.y], other)); 			    			
    			G.grid[other.x][other.y].visit(getColor(dist[other.x][other.y]));
    			
    			if (other.x == t.x && other.y == t.y) {
        			finished = true;
        			break;
        		} 
    		}
    		  		
    	}
	}
}
