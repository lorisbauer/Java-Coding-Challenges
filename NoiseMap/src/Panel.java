import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

public class Panel extends JPanel {
	final int SIZE = 900;
	final int NTILES = 300;
	final int TILE_SIZE = SIZE / NTILES;
	final int SEED = 0;
	final int CSTEPS = 300;
	final double FREQUENCY = 0.006d;
	
	int z = 0;
	double zInc = 20;
	ArrayList<Color> gradient;
	
	
	Panel() {
		configPanel();
		gradient = Panel.generateGradient(new Color(255, 0, 0), new Color(0, 100, 200), CSTEPS);
	}
	
	public void paint(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		drawBG(g2D);
	}
	
	public static double lerp(double x, double s1, double e1, double s2, double e2) {
		if (x > e1) return e2;
		if (x < s1) return s2;
		return s2 + (x - s1) * (e2 - s2) / (e1 - s1); 
	}
	
	
	public void drawBG(Graphics2D g2D) {
		for (int x = 0; x < SIZE; x++) {
		    for (int y = 0; y < SIZE; y++) {
		    	double value = OpenSimplexNoise.noise3_ImproveXY(SEED, x * FREQUENCY, y * FREQUENCY, z * FREQUENCY);
		    	Color color = getColor(value);
				g2D.setPaint(color);			
				g2D.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
		    }
		}
		z+= zInc;
		repaint();
	}
	
	public Color getColor(double value) {
		int i = (int) Panel.lerp(value, -1, 1, 0, CSTEPS - 1);
		return gradient.get(i);
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
            
            Color interpolatedColor = new Color(interpolatedRed, interpolatedGreen, interpolatedBlue);
            gradientColors.add(interpolatedColor);
        }
        
        return gradientColors;
    }
}