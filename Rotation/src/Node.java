import java.awt.*;
import java.awt.geom.*;
import java.util.*;

public class Node {
	double[] pos;
	double[] projPos;
	int size;
	int radius = 10;
	int stroke = 2;
	ArrayList<Node> neighbors = new ArrayList<>();
	
	Node(double[] pos, int size) {
		this.pos = pos;
		this.size = size;
	}	
	public void addNeighbor(Node neighbor) {
		neighbors.add(neighbor);
	}
	
	public double[][] convertPos(double[] position) {
		double x = position[0];
		double y = position[1];
		double z = position[2];
		return new double[][] {{x}, {y}, {z}};
	}
	
	public double[] revertPos(double [][] position) {
		double x = position[0][0];
		double y = position[1][0];
		double z = position[2][0];
		return new double[] {x, y, z};
	}
	
	public void setProjPos(double[][] projPos) {
		this.projPos = revertPos(projPos);
	}
	
	public boolean contains(Node node) {
		if (neighbors.isEmpty()) return false;
		for (Node neighbor : neighbors) {
			if (Arrays.equals(neighbor.pos, node.pos)) {
				return true;
			}
		}
		return false;
	}
	
	public String toString() {
		return Arrays.toString(pos);
	}
	
	public String printNeighbors() {
		String str = "";
		for (Node neighbor : neighbors) {
			str += neighbor + " ";
		}
		return str;
	}
	
	public void draw(Graphics2D g2D) {
		double offset = size / 2;
		g2D.setPaint(new Color(0, 0, 0));
		double x = projPos[0], y = projPos[2];
		
		for (Node node: neighbors) {
			double x1 = node.projPos[0], y1 = node.projPos[2];
			g2D.setStroke(new BasicStroke(stroke));
			g2D.draw(new Line2D.Double(offset + x, offset - y, offset + x1, offset - y1));
		}
				
		Shape Circle = new Ellipse2D.Double(x + offset  - radius, offset - y - radius, 2.0 * radius, 2.0 * radius);
		g2D.fill(Circle);
	}
}