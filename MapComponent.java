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
	
	public MapComponent(){
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
		System.out.println("paint");
		Graphics2D g2 = (Graphics2D) g;
		g2.setBackground(Color.BLACK);
		g2.setColor(Color.WHITE);
		g2.drawOval(500, 500, 500, 500);
		g2.drawImage(trailImage, 0, 0, trailImage.getWidth(null),trailImage.getHeight(null),null);
	}
	
}
