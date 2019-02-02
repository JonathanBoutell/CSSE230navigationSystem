import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;

public class PathFinder {
	private HashMap<String,MapNode> map;
	
	public PathFinder() throws FileNotFoundException{
		map = read();
	}
	
	public static HashMap<String, MapNode> read() throws FileNotFoundException {
		XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("graph.xml")));
		HashMap<String, MapNode> nodes = (HashMap<String, MapNode>) decoder.readObject();
		decoder.close();
		return nodes;
	}
	
	public Path runAStar(boolean findMaxDistance, String start, String end, double maxDifficulty) {
		PriorityQueue<MapNode> queue = new PriorityQueue<MapNode>();
		if(findMaxDistance) queue = new PriorityQueue<MapNode>(Collections.reverseOrder());
		if(map.containsKey(start) && map.containsKey(end)) { 
			queue.offer(map.get(start));
			ArrayList<MapNode> visited = new ArrayList<>();
			HashMap<MapNode,MapEdge> previousEdge = new HashMap<>();
			HashMap<MapNode,MapNode> previousNode = new HashMap<>();
			HashMap<MapNode,Double> openNodes = new HashMap<>();
			ArrayList<MapNode> closedNodes = new ArrayList<>();
			while(!queue.isEmpty()) {
				MapNode current = queue.poll();
				if(current.findHeuristicDistance() == 0) return reconstructPath(map.get(start),current,previousEdge,previousNode);
				visited.add(current);
				for(MapEdge e : current.getEdges()) {
					if(e.getDifficulty() <= maxDifficulty) {
						MapNode next = map.get(e.getNextNode());
						if(!closedNodes.contains(next)){
							next.updateDestination(map.get(end));
							double dist = openNodes.get(current) + current.distanceBetween(next) + next.findHeuristicDistance();
							if(!openNodes.containsKey(next)) {
								openNodes.put(next,dist);
							}else {
								double otherDistance = openNodes.get(next);
								if(findMaxDistance) {
									if(dist > otherDistance) {
										previousEdge.put(next, e);
										previousNode.put(next, current);
										openNodes.put(next,dist);
									}
								}else {
									if(dist < otherDistance) {
										previousEdge.put(next, e);
										previousNode.put(next,current);
										openNodes.put(current,dist);
									}
								}
							}
						}
					}
				}
				closedNodes.add(current);
				openNodes.remove(current);
			}
			return null;
		}else return null;
	}
	
	private Path reconstructPath(MapNode start, MapNode current, HashMap<MapNode,MapEdge> previousEdges, HashMap<MapNode,MapNode> previousNodes) {
		HashMap<MapNode,MapEdge> path = new HashMap<MapNode,MapEdge>();
		while(current.distanceBetween(start) != 0) {
			MapNode previousNode = previousNodes.get(current);
			MapEdge edge = previousEdges.get(current);
			path.put(previousNode, edge);
			current = previousNode;
		}
		return new Path(path);
	}
}
