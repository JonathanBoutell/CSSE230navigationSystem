import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.util.HashMap;

import org.junit.Test;

public class PathFinderTest {

	@Test
	public void testEmptyMap() {
		try {
			PathFinder test = new PathFinder();
			HashMap<String, MapNode> emptyMap = new HashMap<>();
			test.setMap(emptyMap);
			assertEquals(null, test.runAStar(false, true, "GreenLight", "BaseCamp", 4));
		} catch (FileNotFoundException exception) {
			System.out.println("Failed to load file");
			assertTrue(false);
			exception.printStackTrace();
		}

	}

	@Test
	public void testNonExistingPath() {
		try {
			PathFinder test = new PathFinder();
			assertEquals(null, test.runAStar(false, true, "easyout0", "crystalhut0", 4));
		} catch (FileNotFoundException exception) {
			exception.printStackTrace();
		}

	}

	@Test
	public void testSingleEdgePath() {
		try {
			PathFinder test = new PathFinder();
			HashMap<MapNode, MapEdge> edges = new HashMap<>();
			HashMap<String, MapNode> nodes = test.getNodes();
			MapNode greenLine = nodes.get("greenline5");
			for (MapEdge e : greenLine.getEdges()) {
				if (e.name.equals("stoker")) {
					edges.put(greenLine, e);
					break;
				}
			}
			Path path = new Path(edges, test.getNodes(), greenLine);
			assertEquals(path, test.runAStar(false, true, "greenline5", "greenline3", 4));
		} catch (FileNotFoundException exception) {
			exception.printStackTrace();
		}
	}
	
	@Test
	public void testLongPath() {
		try {
			PathFinder test = new PathFinder();
			HashMap<MapNode, MapEdge> edges = new HashMap<>();
			HashMap<String, MapNode> nodes = test.getNodes();
			String[] node = {"greenline5", "greenline4", "greenline3", "greenline2", "excaliburgondola", "greenline1", "greenline0", "sideline", "yellowbrickroad"};
			String[] edge = {"greenline", "greenline", "greenline", "greenline", "greenline", "greenline", "greenline", "sideline", "yellowbrickroad"};
			for(int i=0;i<node.length;i++){
				MapNode current = nodes.get(node[i]);
				for(MapEdge e : current.getEdges()){
					if(e.getName().equals(edge[i])){
						edges.put(current, e);
						break;
					}
				}
				
			}
			Path path = new Path(edges, test.getNodes(), nodes.get("greenline5"));
			assertEquals(path, test.runAStar(true, false, "greenline5", "basecamp1", 4));
		} catch (FileNotFoundException exception) {
			exception.printStackTrace();
		}
	}
	
	@Test
	public void testFirstAidAtNode(){
		try {
			PathFinder test = new PathFinder();
			HashMap<MapNode, MapEdge> edges = new HashMap<>();
			HashMap<String, MapNode> nodes = test.getNodes();
			MapNode node = nodes.get("christine's");

			Path path = new Path(edges, test.getNodes(), node);
			assertEquals(path, test.findNearestFirstAidStation("christine's"));
		} catch (FileNotFoundException exception) {
			exception.printStackTrace();
		}
	}
	
	@Test 
	public void testFarFirstAidStation(){
		try {
			PathFinder test = new PathFinder();
			HashMap<MapNode, MapEdge> edges = new HashMap<>();
			HashMap<String, MapNode> nodes = test.getNodes();
			String[] node = {"greenline1", "yellowbrickroad"};
			String[] edge = {"lowercruiser","yellowbrickroad"};
			for(int i=0;i<node.length;i++){
				MapNode current = nodes.get(node[i]);
				for(MapEdge e : current.getEdges()){
					if(e.getName().equals(edge[i])){
						edges.put(current, e);
						break;
					}
				}
				
			}

			Path path = new Path(edges, test.getNodes(), nodes.get("greenline1"));
			assertEquals(path, test.findNearestFirstAidStation("greenline1"));
		} catch (FileNotFoundException exception) {
			exception.printStackTrace();
		}
	}
	
	@Test 
	public void testSkiLift(){
		try {
			PathFinder test = new PathFinder();
			HashMap<MapNode, MapEdge> edges = new HashMap<>();
			HashMap<String, MapNode> nodes = test.getNodes();
			MapNode node = nodes.get("basecamp1");
			for (MapEdge e : node.getEdges()) {
				if (e.name.equals("blackcombgondola")) {
					edges.put(node, e);
					break;
				}
			}
			
			Path path = new Path(edges, test.getNodes(), node);
			assertEquals(path, test.runAStar(false, true, "basecamp1", "christine's", 4));
		} catch (FileNotFoundException exception) {
			exception.printStackTrace();
		}
	}
	
	@Test
	public void testLimitedDifficulty(){
		try {
			PathFinder test = new PathFinder();
			HashMap<MapNode, MapEdge> edges = new HashMap<>();
			HashMap<String, MapNode> nodes = test.getNodes();
			String[] node = {"christine's", "easyout3"};
			String[] edge = {"greensomething", "easyout"};
			for(int i=0;i<node.length;i++){
				MapNode current = nodes.get(node[i]);
				for(MapEdge e : current.getEdges()){
					if(e.getName().equals(edge[i])){
						edges.put(current, e);
						break;
					}
				}
				
			}

			
			Path path = new Path(edges, test.getNodes(), nodes.get("christine's"));
			assertEquals(path, test.runAStar(false, true, "christine's", "easyout2", 2));
		} catch (FileNotFoundException exception) {
			exception.printStackTrace();
		}
	}

}
