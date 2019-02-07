import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Path implements Drawable{
	private HashMap<MapNode,MapEdge> map;
	private HashMap<String, MapNode> nodes;
	private MapNode startNode;
	
	public Path(HashMap<MapNode,MapEdge> map, HashMap<String, MapNode> nodes,MapNode startNode) {
		this.map = map;
		this.nodes = nodes;
		this.startNode = startNode;
	}
	
	public double getAverageDifficulty() {
		Iterator<MapEdge> edges = map.values().iterator();
		int i = 0;
		while(edges.hasNext()) {
			i += edges.next().difficulty;
		}
		return i / map.size();
	}
	
	public double getTotalDistance() {
		MapNode start;
		MapNode end;
		int dist = 0;
		for(Map.Entry<MapNode, MapEdge> entry : map.entrySet()) {
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
		while(edges.hasNext()) {
			temp = edges.next().difficulty;
			if(temp > max) {
				max = temp;
			}
		}
		return (int)max;
	}
	
	@Override
	public void draw(Graphics g) {
		startNode.draw(g);
		for(MapEdge edge : map.values()) {
			edge.draw(g);
			nodes.get(edge.getNextNode()).draw(g);
		}
	}

	@Override
	public void highlight(Graphics g) {
		startNode.highlight(g);
		for(MapEdge edge : map.values()) {
			edge.highlight(g);
			nodes.get(edge.getNextNode()).highlight(g);
		}
	}
	
	public ArrayList<String> generateDirections() {
		ArrayList<String> directions = new ArrayList<String>();
		MapNode currentNode = startNode;
		MapEdge currentEdge = map.get(startNode);
		//while() {
		//	currentNode = nodes.get(currentEdge.getNextNode());
		//}
		return null;
	}
}