import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class MapComponent extends JComponent {
	
	Image trailImage;
	Path drawPath;
	
	public MapComponent(){
		this.drawPath = null;
		try {
			trailImage = ImageIO.read(getClass().getResource("trailsCropped.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		trailImage = trailImage.getScaledInstance(
				(int)(trailImage.getWidth(null)*Main.SCALE_FACTOR), 
				(int)(trailImage.getHeight(null)*Main.SCALE_FACTOR), Image.SCALE_DEFAULT);
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(trailImage, 0, 0, trailImage.getWidth(null),trailImage.getHeight(null),null);
		if (drawPath != null) drawPath.draw(g2);
	}

	public void setPath(Path drawPath) {
		this.drawPath = drawPath;
	}
	
}
