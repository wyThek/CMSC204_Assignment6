import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Yei Thek Wang 
 * JUnit Test of TownGraphManager
 *
 */
public class TownGraphManagerTest_Student {

	private TownGraphManagerInterface graph;
	private String[] town;

	@Before
	public void setUp() throws Exception {
		graph = new TownGraphManager();
		town = new String[7];

		for (int i = 1; i < 7; i++) {
			town[i] = "Town_" + i;
			graph.addTown(town[i]);
		}

		graph.addRoad(town[1], town[2], 3, "Road_1");
		graph.addRoad(town[1], town[4], 5, "Road_2");
		graph.addRoad(town[2], town[5], 9, "Road_3");
		graph.addRoad(town[4], town[3], 2, "Road_4");
		graph.addRoad(town[3], town[5], 1, "Road_5");
		graph.addRoad(town[5], town[6], 2, "Road_6");

	}

	@After
	public void tearDown() throws Exception {
		graph = null;
	}

	@Test
	public void testAddRoad() {
		ArrayList<String> roads = graph.allRoads();
		assertEquals("Road_1", roads.get(0));
		assertEquals("Road_2", roads.get(1));
		assertEquals("Road_3", roads.get(2));
		assertEquals("Road_4", roads.get(3));
		graph.addRoad(town[2], town[4], 3, "Road_7");
		roads = graph.allRoads();
		assertEquals("Road_7", roads.get(6));
	}

	@Test
	public void testContainsRoadConnection() {
		assertEquals(true, graph.containsRoadConnection(town[1], town[2]));
		assertEquals(false, graph.containsRoadConnection(town[1], town[6]));
	}

	@Test
	public void testDeleteRoadConnection() {
		assertEquals(true, graph.containsRoadConnection(town[2], town[5]));
		graph.deleteRoadConnection(town[2], town[5], "Road_3");
		assertEquals(false, graph.containsRoadConnection(town[2], town[5]));
	}

	@Test
	public void testGetPath() {
		ArrayList<String> path = graph.getPath(town[1], town[5]);
		assertNotNull(path);
		assertTrue(path.size() > 0);
		assertEquals("Town_1 via Road_2 to Town_4 5 mi", path.get(0).trim());
		assertEquals("Town_4 via Road_4 to Town_3 2 mi", path.get(1).trim());
		assertEquals("Town_3 via Road_5 to Town_5 1 mi", path.get(2).trim());

	}

}