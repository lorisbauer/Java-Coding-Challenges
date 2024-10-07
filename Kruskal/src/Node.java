import java.util.*;

public class Node {
	String name;
	int index;
	int rep;
	ArrayList<Node> members = new ArrayList<Node>();

	public Node(String name, int index) {
		this.name = name;
		this.index = index;
		this.rep = index;
		members.add(this);
	}
	
	public void changeRep(int rep) {
		this.rep = rep;
	}
	
	public String toString() {
		return "(" + index + ")";
	}
}