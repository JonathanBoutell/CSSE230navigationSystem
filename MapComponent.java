import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class MapComponent extends JComponent {
	
	Image trailImage;
	Path drawPath;
	ArrayList<MapNode> list;
	private boolean showsMap;
	
	public MapComponent(){
		showsMap = true;
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
		if(list != null) drawNodes(g2);
		if (drawPath != null) drawPath.draw(g2);
	}

	public void addNodeList(ArrayList<MapNode> list) {
		this.list = list;
	}
	
	public void setPath(Path drawPath) {
		this.drawPath = drawPath;
	}
	
	private void drawNodes(Graphics g) {
		for(MapNode node : list) {
			node.draw(g);
		}
	}

	public void hideMap() {
		this.showsMap = false;
	}

	public void showMap() {
		this.showsMap = true;
	}

	public boolean getShowsMap() {
		return this.showsMap;
	}
}
