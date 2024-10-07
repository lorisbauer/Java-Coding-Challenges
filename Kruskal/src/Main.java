import java.util.*;

public class Main {
	
	public static void main(String[] args) {
		Node A = new Node("A", 1);
		Node B = new Node("B", 2);
		Node C = new Node("C", 3);
		Node D = new Node("D", 4);
		Node E = new Node("E", 5);
		Node F = new Node("F", 6);
		Node G = new Node("G", 7);
		
		Edge e1 = new Edge(A, B, 2);
		Edge e2 = new Edge(A, C, 9);
		Edge e3 = new Edge(B, D, 1);
		Edge e4 = new Edge(B, E, 3);
		Edge e5 = new Edge(D, E, 4);
		Edge e6 = new Edge(D, F, 3);
		Edge e7 = new Edge(C, D, 2);
		Edge e8 = new Edge(C, G, 4);
		Edge e9 = new Edge(F, G, 5);
		
		ArrayList<Node> V = new ArrayList<Node>(Arrays.asList(A, B, C, D, E, F));
		ArrayList<Edge> Edges = new ArrayList<Edge>(Arrays.asList(e1, e2, e3, e4, e5, e6, e7, e8, e9));
			
		Graph Graph = new Graph(V, Edges);
		System.out.println(Graph.Kruskal());
	} 
}
