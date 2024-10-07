import java.util.*;

public class Graph {
	ArrayList<Node> V;
	ArrayList<Edge> E;
	UnionFind UF = new UnionFind();
	
	public Graph(ArrayList<Node> V, ArrayList<Edge> E) {
		this.V = V;
		this.E = E;
		E.sort(null);
	}
	
	public ArrayList<Edge> Kruskal() {
		ArrayList<Edge> F = new ArrayList<Edge>();
		
		for (Edge edge : E) {
			Node u = edge.u;
			Node v = edge.v;
			if (!UF.same(u, v)) {
				F.add(edge);
				UF.union(u, v);
			}
		}
		
		return F;
	}
}
