import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class PathFinder {
	private HashMap<String, MapNode> map;

	public PathFinder() throws FileNotFoundException {
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
		if (findMaxDistance)
			queue = new PriorityQueue<MapNode>(Collections.reverseOrder());
		if (map.containsKey(start) && map.containsKey(end)) {
			queue.offer(map.get(start));
			// A HashMap mapping the destination node to the edge that it came
			// from
			HashMap<MapNode, MapEdge> previousEdge = new HashMap<>();
			// A HashMap mapping the destination node to the node that it came
			// from
			HashMap<MapNode, MapNode> previousNode = new HashMap<>();
			// A HashMap mapping each open node to its (distance from start) +
			// (heuristic distance from end)
			HashMap<MapNode, Double> openNodes = new HashMap<>();
			// An ArrayList of closed nodes
			ArrayList<MapNode> closedNodes = new ArrayList<>();
			while (!queue.isEmpty()) {
				// process the next node from the queue
				MapNode current = queue.poll();
				// if the current node is the destination, reconstruct the path
				// and return
				if (current.findHeuristicDistance() == 0)
					return reconstructPath(map.get(start), current, previousEdge, previousNode);
				for (MapEdge e : current.getEdges()) {
					if (e.getDifficulty() <= maxDifficulty) {
						MapNode next = map.get(e.getNextNode());
						if (!closedNodes.contains(next)) {
							next.updateDestination(map.get(end));
							double dist = openNodes.get(current) + current.distanceBetween(next)
									+ next.findHeuristicDistance();
							if (!openNodes.containsKey(next)) {
								openNodes.put(next, dist);
							} else {
								double otherDistance = openNodes.get(next);
								if (findMaxDistance) {
									if (dist > otherDistance) {
										previousEdge.put(next, e);
										previousNode.put(next, current);
										openNodes.put(next, dist);
									}
								} else {
									if (dist < otherDistance) {
										previousEdge.put(next, e);
										previousNode.put(next, current);
										openNodes.put(current, dist);
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
		} else
			return null;
	}

	private Path reconstructPath(MapNode start, MapNode current, HashMap<MapNode, MapEdge> previousEdges,
			HashMap<MapNode, MapNode> previousNodes) {
		HashMap<MapNode, MapEdge> path = new HashMap<MapNode, MapEdge>();
		while (current.distanceBetween(start) != 0) {
			MapNode previousNode = previousNodes.get(current);
			MapEdge edge = previousEdges.get(current);
			path.put(previousNode, edge);
			current = previousNode;
		}
		return new Path(path, map, start);
	}

	public Path findNearestFirstAidStation(String start) {
		Queue<MapNode> queue = new LinkedList<>();
		queue.offer(this.map.get(start));
		HashMap<MapNode, MapEdge> traveledEdge = new HashMap<>();
		HashMap<MapNode, MapNode> previousNode = new HashMap<>();
		ArrayList<MapNode> openNodes = new ArrayList<>();
		ArrayList<MapNode> closedNodes = new ArrayList<>();
		MapNode current = null;
		while (!queue.isEmpty()) {
			current = queue.poll();
			for (MapEdge edge : current.getEdges()) {
				MapNode next = this.map.get(edge.nextNode);
				if (!openNodes.contains(next) && !closedNodes.contains(next)) {
					openNodes.add(next);
					traveledEdge.put(next, edge);
					previousNode.put(next, current);
					if (next.hasFirstAid) {
						return this.reconstructPath(this.map.get(start), next, traveledEdge, previousNode);
					}
				}
			}
			closedNodes.add(current);
			openNodes.remove(current); // Probably unecessar
		}
		return null;
	}

	public ArrayList<MapNode> getAllNodes() {
		// since each node returns all the edges going away from it, we should
		// just need to pass back
		// the nodes and be able to draw all the edges as well
		ArrayList<MapNode> temp = new ArrayList<>();
		for (String k : map.keySet()) {
			temp.add(map.get(k));
		}
		return temp;
	}
}
