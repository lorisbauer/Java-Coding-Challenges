import java.util.*;

public class UnionFind {
	public boolean same(Node u, Node v) {
		return u.rep == v.rep;
	}
	
	public void union(Node u, Node v) {
		ArrayList<Node> M = null;
		ArrayList<Node> N = null;
		int rep = -1;
		
		
		if (u.members.size() > v.members.size()) {
			M = u.members;
			N = v.members;	
			rep = u.rep;
		}
		else {
			M = v.members;
			N = u.members;	
			rep = v.rep;
		}
		
		for (Node element : N) {
			element.rep = rep;
			M.add(element);
		}
		
		for (Node element : N) {
			element.members = M;
		}
	}
}
