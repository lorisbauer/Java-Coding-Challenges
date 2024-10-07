import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

import javax.swing.*;

public class Frame extends JFrame implements KeyListener {
	int SIZE = 400;
	Panel panel;
	
	Frame() {
		configFrame();
	}
	
	void configFrame() {
		panel = new Panel(SIZE);
		panel.setPreferredSize(new Dimension(SIZE, SIZE));
		
		this.setTitle("3D Rotation");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(panel);
		this.pack();
		this.setLocationRelativeTo(null);
		this.addKeyListener(this);
		this.setVisible(true);
	}	

	@Override
	public void keyPressed(KeyEvent e) {
		panel.pressedKeys.add(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		panel.pressedKeys.remove(e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent e) {		
	}
}
