import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

public class Panel extends JPanel {
	final int SIZE = 800;
	final int UNIT_WIDTH = 3;
	final int GAP = SIZE / 10;
	final double K = 0.8d;
	final int SPACE = (int) ((SIZE - 2 * GAP) * K);
	final int LENGTH = SPACE / UNIT_WIDTH, RANGE = SIZE / 2;
	int index = 0;
	MergeSort mergeSort;
	ArrayList<double[]> progression;
	
	Panel() {
		configPanel();
		mergeSort = new MergeSort(LENGTH, RANGE);
		progression = mergeSort.progression;
	}
	
	public void paint(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(
	             RenderingHints.KEY_ANTIALIASING,
	             RenderingHints.VALUE_ANTIALIAS_ON);
	    g2D.setRenderingHints(rh);
		drawBG(g2D);
		drawRects(g2D);
	}
	
	public void drawBG(Graphics2D g2D) {
		Color startColor = new Color(50, 50, 50);
		Color endColor = new Color(200, 200, 200);
		
		GradientPaint gradient = new GradientPaint(SIZE / 2, 0, startColor, SIZE / 2, SIZE, endColor);
		g2D.setPaint(gradient);
		g2D.fillRect(0, 0, SIZE, SIZE);
	}
	
	public void configPanel() {
		this.setPreferredSize(new Dimension(SIZE, SIZE));
	}
	
	public void drawRects(Graphics2D g2D) {
		int i = 0;
		int stroke = 1;
		g2D.setStroke(new BasicStroke(stroke));
		for (double element : progression.get(index)) {
			int height = (int) element;
			
			g2D.setPaint(Color.white);
			g2D.fillRect((SIZE - LENGTH * UNIT_WIDTH) / 2 + i * UNIT_WIDTH, SIZE - RANGE / 2 - height, UNIT_WIDTH, height);
			g2D.setPaint(Color.black);
			g2D.drawRect((SIZE - LENGTH * UNIT_WIDTH) / 2 + i * UNIT_WIDTH, SIZE - RANGE / 2 - height, UNIT_WIDTH, height);
			i++;
		}
		
		if (index < progression.size() - 1) {
			index++;
		}
		repaint();
	}
}