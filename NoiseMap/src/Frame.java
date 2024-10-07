import javax.swing.*;

public class Frame extends JFrame {
	Panel panel;
	
	Frame() {
		configFrame();
	}
	
	void configFrame() {
		panel = new Panel();
		this.setTitle("Noise Map");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(panel);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}
