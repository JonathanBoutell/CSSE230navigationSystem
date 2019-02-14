import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class MapComponent extends JComponent {
	
	Image trailImage;
	Path drawPath;
	Boolean mapMode;
	Collection<MapNode> list;
	HashMap<String, MapNode> map;
	
	public MapComponent(){
		this.drawPath = null;
		this.mapMode = true;
		
		//load the image and scale it appropriately
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
		// draw all appropriate pictures, nodes, and edges
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		if(mapMode) g2.drawImage(trailImage, 0, 0, trailImage.getWidth(null),trailImage.getHeight(null),null);
		if(list != null) drawNodes(g2);
		if(list != null && map != null && !mapMode) drawEdges(g2);
		if (drawPath != null) drawPath.draw(g2);
	}

	public void setMapMode(Boolean mode) {
		this.mapMode = mode;
	}
	
	public Boolean getMapMode() {
		return this.mapMode;
	}
	
	public void addMap(HashMap<String, MapNode> map) {
		this.map = map;
		this.list = map.values();
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
		double offsetX;
		double offsetY;
		double slope;
		int offsetMult;
		ArrayList<MapNode> visited;
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(Main.EDGE_THICKNESS));
		// draw edges, adding offsets to edges that share start and end nodes
		for(MapNode node : list) {
			visited = new ArrayList<MapNode>();
			for(MapEdge edge : node.edges) {
				endNode = map.get(edge.getNextNode());
				offsetMult = Main.EDGE_THICKNESS * countOccurances(visited, endNode);
				visited.add(endNode);
				slope = ((double)(endNode.getDrawingX() - node.getDrawingX()))/((double)(endNode.getDrawingY() - node.getDrawingY()));
				offsetX = offsetMult * Math.cos(Math.atan(slope));
				offsetY = offsetMult * -Math.sin(Math.atan(slope));
				g2.setColor(edge.color);
				g2.drawLine(node.getDrawingX() + (int)offsetX, node.getDrawingY() + (int)offsetY, 
						endNode.getDrawingX() + (int)offsetX, endNode.getDrawingY() + (int)offsetY);
			}
		}
	}
	
	private int countOccurances(Collection<MapNode> list, MapNode specified) {
		int count = 0;
		for(MapNode node : list) {
			if(node.equals(specified))
				count++;
		}
		return count;
	}
	
}
