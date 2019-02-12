import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class MapComponent extends JComponent {
	
	Image trailImage;
	Path drawPath;
	Boolean mapMode;
	ArrayList<MapNode> list;
	HashMap<String, MapNode> map;
	
	public MapComponent(){
		this.drawPath = null;
		this.mapMode = true;
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
		if(list != null && map != null && !mapMode) drawEdges(g2);
		if (drawPath != null) drawPath.draw(g2);
	}

	public void setMapMode(Boolean mode) {
		this.mapMode = mode;
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
	
	private void drawEdges(Graphics g) {
		MapNode endNode;
		int i;
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(Main.EDGE_THICKNESS));
		for(MapNode node : list) {
			i = 0;
			for(MapEdge edge : node.edges) {
				endNode = map.get(edge.getNextNode());
				g2.setColor(edge.color);
				g2.drawLine(node.getDrawingX() + i, node.getDrawingY(), endNode.getDrawingX() + i, endNode.getDrawingY());
			}
		}
	}
	
}
