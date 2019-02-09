import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JComponent;

public class DirectionsComponent extends JComponent {
	
	private Path path;
	
	public DirectionsComponent(Path path) {
		this.path = path;
	}
	
	public ArrayList<String> generateDirections(Path p) {
		return p.generateDirections();
	}
	
	public void drawDirections(Graphics g, Path p) {
		p.highlight(g);
	}
}
