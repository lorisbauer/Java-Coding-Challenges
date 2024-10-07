
public class Pair implements Comparable<Pair> {
	int value;
	Node node;
	
	Pair(int value, Node node) {
		this.value = value;
		this.node = node;
	}
	
	@Override
	public int compareTo(Pair other) {
		if (this.value > other.value) {
	        return 1;
	    }
	    else if (this.value < other.value) {
	        return -1;
	    }
	    return 0;
	}
	
	public String toString() {
		return "(" + value + ", " + node + ")";
	}
}
