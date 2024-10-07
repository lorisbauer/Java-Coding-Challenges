import java.awt.*;
import java.util.*;

public class Node {
	int x;
	int y;
	int size;
	int height;
	final int alpha = 75;
	
	Color color;
	Color visitColor;
	Color pathColor = new Color(0, 150, 0);
	
	boolean blocked = false;
	boolean visited = false;
	boolean isPath = false;
	
	Node(int x, int y, int size) {
		this.x = x;
		this.y = y;
		this.size = size;
	}
	
	public void setColor(Color color) {
		this.height = color.getRed();
		if (this.height == 0) {
			this.blocked = true;
		}
		this.color = color;
	}
	
	public void visit(Color visitColor) {
		this.visited = true;
		this.visitColor = visitColor;
	}
	
	public void draw(Graphics2D g2D) {
		int stroke = 1;
		g2D.setStroke(new BasicStroke(stroke));
		g2D.setPaint(color);			
		g2D.fillRect(x * size, y * size, size, size);
		g2D.setPaint(Color.black);
		g2D.drawRect(x * size, y * size, size, size);
		
		if (visited && !isPath) {
			g2D.setColor(visitColor);
			g2D.fillRect(x * size, y * size, size, size);
		}
		
		if (isPath) {
			g2D.setColor(pathColor);
			g2D.fillRect(x * size, y * size, size, size);
		}
	}
	
	public String toString() {
		return "((" + x + ", " + y + "), " + height + ")"; 
	}
}
