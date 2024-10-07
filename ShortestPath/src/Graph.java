import java.awt.Graphics2D;

public class Graph {
	int n;
	int m;
	Node[][] grid;
	
	Graph(int n, int m, Node[][] grid) {
		this.n = n;
		this.m = m;
		this.grid = grid;
	}
	
	public void draw(Graphics2D g2D) {
		for (int x = 0; x < n; x++) {
		    for (int y = 0; y < m; y++) {
		    	Node node = grid[x][y];
		    	node.draw(g2D);
		    }
		}
	}
}
