import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DirectionsFrame {
	
	private Path path;
	
	public DirectionsFrame(Path p) {
		this.path = p;
	}

	public void display() {
		
		JFrame frame = new JFrame();
		frame.setTitle("Directions");
		JPanel holder = new JPanel();
		holder.setLayout(new BoxLayout(holder, BoxLayout.Y_AXIS));
		holder.setBackground(new Color(200,229,255));
		
		if (this.path != null) {
			if(path.isEmpty()) {
				JLabel noPath = new JLabel("No Path Found");
				if(path.firstAid()) noPath = new JLabel("You are already at a first aid station.");
				noPath.setFont(new Font("SansSerif", Font.PLAIN, 30));
				noPath.setAlignmentX(Component.CENTER_ALIGNMENT);
				holder.add(noPath);
			}else {
				ArrayList<String> directions = this.path.generateDirections();
				for (String string : directions) {
					JLabel step = new JLabel(string);
					step.setFont(new Font("SansSerif", Font.PLAIN, 30));
					step.setAlignmentX(Component.CENTER_ALIGNMENT);
					holder.add(step);
				}
			}
		} else {
			JLabel noPath = new JLabel("No Path Found");
			noPath.setFont(new Font("SansSerif", Font.PLAIN, 30));
			noPath.setAlignmentX(Component.CENTER_ALIGNMENT);
			holder.add(noPath);
		}
		
		frame.add(holder);
		frame.pack();
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
