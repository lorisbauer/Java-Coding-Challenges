import java.awt.*;
import javax.swing.*;

public class Panel extends JPanel {
	final int SIZE = 800;
	Cube cube;
		
	Panel() {
		configPanel();
		double[][] projPlane = {
			{1, 0},
			{0, 0},
			{0, 1}
		};
		
		cube = new Cube(100, 100, SIZE, projPlane);
	}
	
	public void paint(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(
	             RenderingHints.KEY_ANTIALIASING,
	             RenderingHints.VALUE_ANTIALIAS_ON);
	    g2D.setRenderingHints(rh);
	    drawBG(g2D);
	    drawCube(g2D);
	    
	}	
	
	public void drawBG(Graphics2D g2D) {
		Color startColor = new Color(50, 50, 50);
		Color endColor = new Color(200, 200, 200);
		
		GradientPaint gradient = new GradientPaint(SIZE / 2, 0, startColor, SIZE / 2, SIZE, endColor);
		g2D.setPaint(gradient);
		g2D.fillRect(0, 0, SIZE, SIZE);
	}
	
	void drawCube(Graphics2D g2D) {
		for (Node node: cube.nodes) {
			node.draw(g2D);
		}
		cube.updateNodes();
		repaint();
	}
	
	public void configPanel() {
		this.setPreferredSize(new Dimension(SIZE, SIZE));
	}
}