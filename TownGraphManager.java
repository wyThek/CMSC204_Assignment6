import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * 
 * @author Yei Thek Wang
 * 
 *         Manages the Graph class. Allows elements and functionalities of the
 *         graph to be accessed by referring to Towns and Roads as their names
 *         (Strings).
 *
 */

public class TownGraphManager implements TownGraphManagerInterface {

	private Graph graph = new Graph();

	/**
	 * Adds a road to the graph
	 * 
	 * @param town1
	 * @param town2
	 * @param weight
	 * @param roadName
	 * @return true or false
	 */
	@Override
	public boolean addRoad(String town1, String town2, int weight, String roadName) {
		try {
			graph.addEdge(new Town(town1), new Town(town2), weight, roadName);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Returns a road name based on given towns
	 * 
	 * @param town1
	 * @param town2
	 * @return roadName
	 */
	@Override
	public String getRoad(String town1, String town2) {
		return graph.getEdge(new Town(town1), new Town(town2)).getName();
	}

	/**
	 * Adds a town to the graph
	 * 
	 * @param v name
	 * @return true or false
	 */
	@Override
	public boolean addTown(String v) {
		return graph.addVertex(new Town(v));
	}

	/**
	 * Returns a Town object based on a given town name
	 * 
	 * @param name
	 * @return town
	 */
	@Override
	public Town getTown(String name) {
		Town town = null;
		for (Town t : graph.vertexSet()) {
			if (t.getName().equals(name)) {
				town = t;
			}
		}
		return town;
	}

	/**
	 * Returns true if the graph contains a town based on a given name, false if not
	 * 
	 * @param v name
	 * @return true or false
	 */
	@Override
	public boolean containsTown(String v) {
		return graph.containsVertex(new Town(v));
	}

	/**
	 * Returns true if there is an existing road connection between two given town
	 * names, false if not
	 * 
	 * @param town1
	 * @param town2
	 * @return true or false
	 */
	@Override
	public boolean containsRoadConnection(String town1, String town2) {
		return graph.containsEdge(new Town(town1), new Town(town2));
	}

	/**
	 * Returns a list of all roads in graph
	 * 
	 * @return allRoads
	 */
	@Override
	public ArrayList<String> allRoads() {
		ArrayList<String> allRoads = new ArrayList<>();

		for (Road roads : graph.edgeSet())
			allRoads.add(roads.getName());

		Collections.sort(allRoads);
		return allRoads;
	}

	/**
	 * Deletes a road
	 * 
	 * @param town1
	 * @param town2
	 * @param road
	 * @return true or false
	 */
	@Override
	public boolean deleteRoadConnection(String town1, String town2, String road) {
		int weight = 0;

		for (Road roads : graph.edgeSet()) {
			if (roads.getName().equals(getRoad(town1, town2)))
				weight = roads.getWeight();
		}

		return graph.removeEdge(new Town(town1), new Town(town2), weight, road) != null;
	}

	/**
	 * Deletes a town
	 * 
	 * @param v name
	 * @return true or false
	 */
	@Override
	public boolean deleteTown(String v) {
		return graph.removeVertex(getTown(v));
	}

	/**
	 * Populates the graph based off information from a text file
	 * 
	 * @param selectedFile
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void populateTownGraph(File file) throws FileNotFoundException, IOException {

		Scanner input = new Scanner(file);
		String text = "";
		while (input.hasNextLine()) {
			text += input.nextLine() + " ";
		}

		input.close();

		String[] roads = text.split(" ");
		String[][] description = new String[roads.length][];

		for (int i = 0; i < description.length; i++) {

			description[i] = new String[4];

			description[i][0] = roads[i].split(";")[0].split(",")[0];
			description[i][1] = roads[i].split(";")[0].split(",")[1];
			description[i][2] = roads[i].split(";")[1];
			description[i][3] = roads[i].split(";")[2];

			addTown(description[i][2]);
			addTown(description[i][3]);
			addRoad(description[i][2], description[i][3],

					Integer.parseInt(description[i][1]), description[i][0]);

		}

	}

	/**
	 * Returns a list of all towns in the graph
	 * 
	 * @return allTowns
	 */
	@Override
	public ArrayList<String> allTowns() {
		ArrayList<String> allTowns = new ArrayList<>();

		for (Town towns : graph.vertexSet())
			allTowns.add(towns.getName());

		Collections.sort(allTowns);
		return allTowns;
	}

	/**
	 * Returns a list of towns representing the shortest path between town1 and
	 * town2
	 * 
	 * @param town1
	 * @param town2
	 * @return shortest path
	 */
	@Override
	public ArrayList<String> getPath(String town1, String town2) {
		return graph.shortestPath(new Town(town1), new Town(town2));
	}

}