import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import javax.swing.*;

public class Panel extends JPanel {
	final int SIZE = 800;
	final double depth = 13;
	final double branchLength = 100;
	final int baseHeight = 600;
	double buildSpeed = Math.pow(2, 5);
	int treeIndex = 0;
	boolean completedBuild = false;
	Tree tree;
	ArrayList<Color> treeGradient;
	
	Panel() {
		tree = new Tree(depth, branchLength, new Point2D.Double((int) (SIZE / 2), baseHeight));
		treeGradient = Panel.generateGradient(new Color(255, 255, 255), new Color(150, 0, 0), depth);
		configPanel();
	}
	
	public void paint(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(
	             RenderingHints.KEY_ANTIALIASING,
	             RenderingHints.VALUE_ANTIALIAS_ON);
	    g2D.setRenderingHints(rh);
		drawBG(g2D);
		drawTree(g2D);
	}
	
	public void drawBG(Graphics2D g2D) {
		Color startColor = new Color(50, 50, 50, 100);
		Color endColor = new Color(200, 200, 200, 100);
		
		GradientPaint gradient = new GradientPaint(SIZE / 2, 0, startColor, SIZE / 2, SIZE, endColor);
		g2D.setPaint(gradient);
		g2D.fillRect(0, 0, SIZE, SIZE);
	}
	
	public void configPanel() {
		this.setPreferredSize(new Dimension(SIZE, SIZE));
	}
	
	public void drawTree(Graphics2D g2D) {
		int index = 0;
		for (ArrayList<Point2D.Double> branch : tree.branches) {
			if (index > treeIndex) {
				break;
			}
			Point2D.Double start = branch.get(0);
			Point2D.Double end = branch.get(1);
			double value = tree.treeDepths.get(index);
			
			int stroke = (int) Tree.lerp(value, 1, depth - 1, 1, 6);
			int colorIndex = (int) (depth - value);
			Color color = treeGradient.get(colorIndex);
			double x1 = start.getX(), y1 = start.getY(), x2 = end.getX(), y2 = end.getY();
			
			g2D.setPaint(color);
			g2D.setStroke(new BasicStroke(stroke));
			g2D.draw(new Line2D.Double(x1, y1, x2, y2));
			index++;
		}
		
		if (treeIndex + buildSpeed < tree.branches.size()) {
			treeIndex += buildSpeed;
		}
		else {
			completedBuild = true;
			treeIndex = tree.branches.size() - 1;
		}
		
		if (completedBuild) {
			tree.branches.clear();
			tree.modify();
		}
		repaint();
	}
	
	public static ArrayList<Color> generateGradient(Color startColor, Color endColor, double steps) {
        ArrayList<Color> gradientColors = new ArrayList<>();
        
        for (int i = 0; i <= steps; i++) {
            double percentage = (double) i / steps;
            int interpolatedRed = (int) (startColor.getRed() + (endColor.getRed() - startColor.getRed()) * percentage);
            int interpolatedGreen = (int) (startColor.getGreen() + (endColor.getGreen() - startColor.getGreen()) * percentage);
            int interpolatedBlue = (int) (startColor.getBlue() + (endColor.getBlue() - startColor.getBlue()) * percentage);
            
            Color interpolatedColor = new Color(interpolatedRed, interpolatedGreen, interpolatedBlue);
            gradientColors.add(interpolatedColor);
        }
        
        return gradientColors;
    }
}