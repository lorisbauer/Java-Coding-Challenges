import java.awt.*;
import java.util.ArrayList;
import java.util.PriorityQueue;
import javax.swing.*;

public class Panel extends JPanel {
	final int SIZE = 900;
	final int NTILES = 150;
	final int TILE_SIZE = SIZE / NTILES;
	final int SEED = (int) (Math.random() * 100);
	final int CSTEPS = 100;
	final double F1 = 0.1d;
	final double F2 = 0.095d;
	
	ArrayList<Color> Gradient;
	int reps = 100;
	
	Graph G;
	Djikstra djikstra;
	Graphics2D g2D;
	
	Panel() {
		configPanel();
		Gradient = Panel.generateGradient(new Color(0, 0, 0), new Color(255, 255, 255), CSTEPS);
		generateGrid();
		
		int x1 = 0;
		int y1 = 0;
		int x2 = NTILES - 1;
		int y2 = NTILES - 1;
		djikstra = new Djikstra(G.grid[x1][y1], G.grid[x2][y2], G);
	}
	
	public void paint(Graphics g) {
		g2D = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(
	             RenderingHints.KEY_ANTIALIASING,
	             RenderingHints.VALUE_ANTIALIAS_ON);
	    g2D.setRenderingHints(rh);
	    
	    G.draw(g2D);
	    if (!djikstra.found) {
	    	for (int i = 0; i < reps; i++) {
	    		djikstra.shortestPath();
	    	}
	    }
	    repaint();
	}
	
	public static double lerp(double x, double s1, double e1, double s2, double e2) {
		if (x > e1) return e2;
		if (x < s1) return s2;
		return s2 + (x - s1) * (e2 - s2) / (e1 - s1); 
	}
	
	public void generateGrid() {
		Node[][] Grid = new Node[NTILES][NTILES];
		for (int x = 0; x < NTILES; x++) {
		    for (int y = 0; y < NTILES; y++) {
		    	double v1 = OpenSimplexNoise.noise3_ImproveXY(SEED, x * F1, y * F1, F1);
		    	double v2 = OpenSimplexNoise.noise3_ImproveXY(SEED, x * F2, y * F2, F2);
		    	Color color = getColor(v1 + v2);
		    	Node node = new Node(x, y, TILE_SIZE);
		    	node.setColor(color);
		    	Grid[x][y] = node;
		    }
		}
		G = new Graph(NTILES, NTILES, Grid);
	}
	
	public Color getColor(double value) {
		int i = (int) lerp(value, -0.7, 1, 0, CSTEPS);
		return Gradient.get(i);
	}
	
	public void configPanel() {
		this.setPreferredSize(new Dimension(SIZE, SIZE));
	}
	
	public static ArrayList<Color> generateGradient(Color startColor, Color endColor, double steps) {
        ArrayList<Color> gradientColors = new ArrayList<>();
        
        for (int i = 0; i <= steps; i++) {
            double percentage = (double) i / steps;
            int interpolatedRed = (int) (startColor.getRed() + (endColor.getRed() - startColor.getRed()) * percentage);
            int interpolatedGreen = (int) (startColor.getGreen() + (endColor.getGreen() - startColor.getGreen()) * percentage);
            int interpolatedBlue = (int) (startColor.getBlue() + (endColor.getBlue() - startColor.getBlue()) * percentage);
            int interpolatedAlpha = (int) (startColor.getAlpha() + (endColor.getAlpha() - startColor.getAlpha()) * percentage);
            
            Color interpolatedColor = new Color(interpolatedRed, interpolatedGreen, interpolatedBlue, interpolatedAlpha);
            gradientColors.add(interpolatedColor);
        }      
        return gradientColors;
    }	
}