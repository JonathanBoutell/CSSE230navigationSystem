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
			System.out.println(greenLine);
			for (MapEdge e : greenLine.getEdges()) {
				if (e.name == "stoker") {
					edges.put(greenLine, e);
					System.out.println("found edge");
					System.out.println(e);
					break;
				}
			}
			Path path = new Path(edges, test.getNodes(), greenLine);

			assertEquals(path, test.runAStar(false, true, "greenline5", "greenline3", 4));
		} catch (FileNotFoundException exception) {
			exception.printStackTrace();
		}
	}

}
