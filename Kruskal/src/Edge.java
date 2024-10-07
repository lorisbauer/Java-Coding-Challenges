
public class Edge implements Comparable<Edge> {
	Node u;
	Node v;
	int weight;
	
	public Edge(Node u, Node v, int weight) {
		this.u = u;
		this.v = v;
		this.weight = weight;
	}
	
	@Override
	public int compareTo(Edge other) {
		if (this.weight > other.weight) {
			return 1;
		}
		else if (this.weight < other.weight) {
			return -1;
		}
		return 0;
	}
	
	public String toString() {
		return weight + ": (" + u.name + ", " + v.name + ")";
	}
}
