import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DirectionsFrame {
	
	private Path path;
	
	public DirectionsFrame(Path p) {
		this.path = path;
	}

	public void display() {
		ArrayList<String> directions = this.path.generateDirections();
		
		JFrame frame = new JFrame();
		frame.setTitle("Directions");
		JPanel holder = new JPanel();
		holder.setLayout(new BoxLayout(holder, BoxLayout.Y_AXIS));
		holder.setBackground(new Color(200,229,255));
		
		for (String string : directions) {
			JLabel step = new JLabel(string);
			step.setFont(new Font("SansSerif", Font.PLAIN, 48));
			step.setAlignmentX(Component.CENTER_ALIGNMENT);
			holder.add(new JLabel(string));
		}
		
		frame.add(holder);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
