import java.awt.*;
import java.util.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.Timer;

import org.apache.commons.math3.linear.*;

public class Panel extends JPanel {
	final int SIZE;
	final double SPEED = 5;
	final double FOV = 100;
	final int FPS = 60;
	final int delay = 1000 / FPS;
	
	final double[][] GAUSSIAN_KERNEL = {
            {1.0 / 16, 1.0 / 8, 1.0 / 16},
            {1.0 / 8, 1.0 / 4, 1.0 / 8},
            {1.0 / 16, 1.0 / 8, 1.0 / 16}
    };
	
	Graphics2D g2D;
	Cam cam;
	RayMarchInit RMI;
	Timer timer;
	Set<Integer> pressedKeys = new HashSet<>();
	ArrayList<Geometry> shapes = new ArrayList<Geometry>();
	
	Color BG = new Color(150, 200, 255);
	
		
	Panel(int size) {
		SIZE = size;
		cam = new Cam(new ArrayRealVector(new double[] {0, 0, -FOV}), FOV, SIZE);
		timer = new Timer(delay, new TimerListener());
		timer.start();		
		initShapes();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g2D = (Graphics2D) g;
	    g2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
	    
	    drawBG();    
	    moveCam();
	    
	    RMI.start();
	    drawScreen();
	    updateShapes();
	}
	
	void drawBG() {
		g2D.setPaint(BG);
		g2D.fillRect(0, 0, SIZE, SIZE);
	}
	
	void drawScreen() {
		Image offScreenBuffer = createImage(getWidth(), getHeight());
        Graphics offScreenGraphics = offScreenBuffer.getGraphics();

        offScreenGraphics.setColor(Color.WHITE);
        offScreenGraphics.fillRect(0, 0, getWidth(), getHeight());
        
        for (int x = 0; x < RMI.screen.length; x++) {
            for (int y = 0; y < RMI.screen[x].length; y++) {
            	offScreenGraphics.setColor(RMI.screen[x][y]);
            	offScreenGraphics.fillRect(x, y, 1, 1);
            }
        }
        g2D.drawImage(offScreenBuffer, 0, 0, this);
	}
	
	void moveCam() {
		RealVector mov = new ArrayRealVector(new double[] {0, 0, 0});
		
		if (!pressedKeys.isEmpty()) {
            for (Iterator<Integer> it = pressedKeys.iterator(); it.hasNext();) {
            	int key = it.next();
	            	switch (key) {	    	        
		    	        case KeyEvent.VK_A:
		    	        	mov.addToEntry(0, -1);
		    	        	break;
		    	        case KeyEvent.VK_D:
		    	        	mov.addToEntry(0, 1);
		    	        	break;
		    	        case KeyEvent.VK_SHIFT:
		    	        	mov.addToEntry(1, -1);
		    	        	break;
		    	        case KeyEvent.VK_SPACE:
		    	        	mov.addToEntry(1, 1);
		    	        	break;
		    	        case KeyEvent.VK_W:
		    	        	mov.addToEntry(2, 1);
		    	        	break;
		    	        case KeyEvent.VK_S:
		    	        	mov.addToEntry(2, -1);
		    	        	break;
		    	        case KeyEvent.VK_Z:
		    	        	cam.zooming(SPEED);
		    	        	break;
		    	        case KeyEvent.VK_U:
		    	        	cam.revertZoom();
		    	        	break;
	    	    }
            }
        }
		if (mov.getNorm() != 0) {
			RealVector res = mov.unitVector().mapMultiply(SPEED);
			cam.position = cam.position.add(res);			
		}
	}
	
	void updateShapes() {
		for (Geometry shape : shapes) {
			shape.update();
		}
	}
	
	void initShapes() {
		int groundSize = 100_000;
		int yoff = -50;
		
		Geometry C1 = new Cube(new ArrayRealVector(new double[] {0, 25, 0}), 30, new Color(255, 0, 0));
		C1.changeMov(new ArrayRealVector(new double[] {-0.5, 0, 0}));
		Geometry R1 = new Rectangle(new ArrayRealVector(new double[] {70, 20, 0}), 20, 100, 30, new Color(0, 255, 0));
		Geometry S1 = new Sphere(new ArrayRealVector(new double[] {-100, 50, 0}), 40, new Color(0, 0, 255));
		Geometry GROUND = new Cube(new ArrayRealVector(new double[] {0, -groundSize / 2 + yoff, 0}), groundSize, new Color(150, 150, 150));
		
		shapes.addAll(Arrays.asList(C1, S1, R1, GROUND));
		RMI = new RayMarchInit(cam, shapes, BG);
		
//		int n = 100;
//		long tot = 0;
//		
//		for (int i = 0; i < n; i++) {
//			long start = System.nanoTime();
//			RMI.start();
//			long end = System.nanoTime() - start;
//			
//			System.out.println(1000 / (end / 1_000_000) + " FPS");
//			tot += end;
//		}	
//		
//		System.out.println("==================");
//		System.out.println("Avg: " + 1000 / (tot / n / 1_000_000) + " FPS");
	}
	
	class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	repaint();
        }
    }
}