import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Path implements Drawable {
	private HashMap<MapNode, MapEdge> map;
	private HashMap<String, MapNode> nodes;
	private MapNode startNode;
	private boolean firstAid;

	public Path(HashMap<MapNode, MapEdge> map, HashMap<String, MapNode> nodes, MapNode startNode, boolean firstAid) {
		this.map = map;
		this.nodes = nodes;
		this.startNode = startNode;
		this.firstAid = firstAid;
	}
	
	public boolean firstAid() {
		return this.firstAid;
	}
	
	public boolean isEmpty() {
		return map.isEmpty();
	}

	public double getAverageDifficulty() {
		Iterator<MapEdge> edges = map.values().iterator();
		int i = 0;
		while (edges.hasNext()) {
			i += edges.next().difficulty;
		}
		return i / map.size();
	}

	public double getTotalDistance() {
		MapNode start;
		MapNode end;
		int dist = 0;
		for (Map.Entry<MapNode, MapEdge> entry : map.entrySet()) {
			start = entry.getKey();
			end = nodes.get(entry.getValue().getNextNode());
			dist += start.distanceBetween(end);
		}
		return dist;
	}

	public int getHighestDifficulty() {
		Iterator<MapEdge> edges = map.values().iterator();
		double max = -1;
		double temp;
		while (edges.hasNext()) {
			temp = edges.next().difficulty;
			if (temp > max) {
				max = temp;
			}
		}
		return (int) max;
	}

	@Override
	public void draw(Graphics g) {
		MapNode currentNode = startNode;
		MapEdge currentEdge;
		MapNode nextNode;
		while (map.containsKey(currentNode)) {
			currentEdge = map.get(currentNode);
			nextNode = nodes.get(currentEdge.getNextNode());
			drawEdge(g, currentNode, nextNode);
			currentNode.highlight(g);
			currentNode = nextNode;
		}
		currentNode.highlight(g);
	}

	private void drawEdge(Graphics g, MapNode startNode, MapNode endNode) {
		g.setColor(Color.YELLOW);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(Main.EDGE_THICKNESS));
		g2.drawLine(startNode.getDrawingX(), startNode.getDrawingY(), endNode.getDrawingX(), endNode.getDrawingY());
	}
	
	public ArrayList<String> generateDirections() {
		ArrayList<String> directions = new ArrayList<String>();
		MapNode currentNode = startNode;
		MapEdge currentEdge = map.get(startNode);
		while(map.containsKey(currentNode)){
			currentEdge = map.get(currentNode);
			directions.add(currentEdge.getDirections());
			currentNode = nodes.get(currentEdge.getNextNode());
		}
		return directions;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null) {
			return false;
		}

		if ((obj instanceof Path)) {
			if (!this.map.equals(((Path) obj).map))
				return false;
			if (this.startNode != ((Path) obj).startNode)
				return false;
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.map.hashCode() + this.startNode.hashCode();
	}

	public String toString() {
		String str = "{" + this.startNode.name;
		MapEdge edge = this.map.get(this.startNode);
		for (int i = 0; i < this.map.size(); i++) {
			str += "=>" + edge.nextNode;
			edge = this.map.get(this.nodes.get(edge.nextNode));
		}
		str += "}";
		return str;
	}
}
