
/**
 * 
 * @author Yei Thek Wang
 * This class represents the graph structure. It holds a list of towns and roads.
 * It handles the Dijkstra's algorithm implementation, as well as general interactions
 * between towns and roads.
 *
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph implements GraphInterface<Town, Road> {

	Set<Town> town = new HashSet<Town>();
	Set<Road> road = new HashSet<Road>();
	Map<Town, Town> townMap = new HashMap<Town, Town>();
	Map<Town, Integer> pathWeight = new HashMap<Town, Integer>();

	/**
	 * Return a reference to the road that is connected to the given sourceVertex
	 * and destinationVertex. Will return null if no such road exists.
	 * 
	 * @param sourceVertex, destinationVertex
	 * @return road
	 */
	@Override
	public Road getEdge(Town sourceVertex, Town destinationVertex) {
		for (Road roads : road) {
			if (roads.contains(sourceVertex) && roads.contains(destinationVertex))
				return roads;
		}

		return null;
	}

	/**
	 * Add an edge to the graph
	 * 
	 * @param sourceVertex
	 * @param destinationVertex
	 * @param weight
	 * @param description
	 * @return road a Road object
	 */
	@Override
	public Road addEdge(Town sourceVertex, Town destinationVertex, int weight, String description) {
		if (sourceVertex == null || destinationVertex == null)
			throw new NullPointerException();

		if (!town.contains(sourceVertex) || !town.contains(destinationVertex))
			throw new IllegalArgumentException();

		Road edge = new Road(sourceVertex, destinationVertex, weight, description);

		try {
			road.add(edge);
		} catch (Exception e) {
			return null;
		}

		return edge;
	}

	/**
	 * Adds a new vertex to the graph if there is no existing Town with the given
	 * name.
	 * 
	 * @param v a Town object
	 * @return true or false
	 */
	@Override
	public boolean addVertex(Town v) {
		if (v == null)
			throw new NullPointerException();

		if (!town.contains(v)) {
			town.add(v);
			return true;
		}

		return false;
	}

	/**
	 * Returns true if the graph contains a road attached to these towns, false if
	 * not.
	 * 
	 * @param sourceVertex
	 * @param destinationVertex
	 * @param true              or false
	 */
	@Override
	public boolean containsEdge(Town sourceVertex, Town destinationVertex) {
		for (Road roads : road) {
			if (roads.contains(sourceVertex) && roads.contains(destinationVertex))
				return true;
		}

		return false;
	}

	/**
	 * Returns true if graph contains the given vertex
	 * 
	 * @param v a Town object
	 * @return true or false
	 */
	@Override
	public boolean containsVertex(Town v) {
		for (Town towns : town) {
			if (towns.equals(v))
				return true;
		}

		return false;
	}

	/**
	 * Returns set of roads
	 * 
	 * @return roads
	 */
	@Override
	public Set<Road> edgeSet() {
		return road;
	}

	/**
	 * Returns set of edges attached to a given vertex
	 * 
	 * @param vertex
	 * @return set
	 */
	@Override
	public Set<Road> edgesOf(Town vertex) {
		Set<Road> edges = new HashSet<>();

		for (Road roads : road) {
			if (roads.contains(vertex))
				edges.add(roads);
		}

		return edges;
	}

	/**
	 * Removes an edge from the graph and modifies the vertices accordingly
	 * 
	 * @param sourceVertex
	 * @param destinationVertex
	 * @param weight
	 * @param description
	 * @return road
	 */
	@Override
	public Road removeEdge(Town sourceVertex, Town destinationVertex, int weight, String description) {
		for (Road roads : road)
			if (roads.contains(sourceVertex) && roads.contains(destinationVertex) && roads.getWeight() == weight
					&& roads.getName().equals(description)) {
				road.remove(roads);
				return roads;
			}

		return null;
	}

	/**
	 * Removes a vertex and all of its edges, if the vertex exists
	 * 
	 * @param v Town object
	 * @return true or false
	 */
	@Override
	public boolean removeVertex(Town v) {
		return town.remove(v);
	}

	/**
	 * Returns a set all towns
	 * 
	 * @return set
	 */
	@Override
	public Set<Town> vertexSet() {
		return town;
	}

	/**
	 * Calls dijkstraShortestPath to find a the shortest path from the source vertex
	 * to every other vertex. Then calls backTrackRecurse to start at the
	 * destinationVertex, and trace the path back to the source vertex
	 * 
	 * @param sourceVertex
	 * @param destinationVertex
	 * @return shortestPath a List representing the shortest path from sourceVertex
	 *         to destinationVertex
	 */
	@Override
	public ArrayList<String> shortestPath(Town sourceVertex, Town destinationVertex) {
		ArrayList<String> shortestPath = new ArrayList<String>();
		boolean bool = false;

		for (Road roads : road)
			if (roads.contains(destinationVertex))
				bool = true;

		if (!bool)
			return shortestPath;

		dijkstraShortestPath(sourceVertex);

		while (!sourceVertex.equals(destinationVertex)) {
			for (Road roads : road)
				if (roads.contains(destinationVertex) && roads.contains(townMap.get(destinationVertex)))
					shortestPath.add(0, townMap.get(destinationVertex).getName() + " via " + roads.getName() + " to "
							+ destinationVertex.getName() + " " + roads.getWeight() + " mi");

			destinationVertex = townMap.get(destinationVertex);
		}
		return shortestPath;
	}

	/**
	 * This method allows us to determine all shortest paths to every vertex,
	 * starting from a specified source vertex. It does this by assigning every Town
	 * object a pointer to a previous Town (stepping towards the source along the
	 * shortest path), as well as a distance value to represent the total shortest
	 * distance between the source and destination vertices.
	 */
	@Override
	public void dijkstraShortestPath(Town sourceVertex) {
		HashSet<Town> dijkstra = new HashSet<>();

		for (Town townNode : town)
			dijkstra.add(townNode);

		for (Town towns : town)
			pathWeight.put(towns, Integer.MAX_VALUE);

		while (!dijkstra.isEmpty()) {
			int minWeight = 100;
			for (Town towns : pathWeight.keySet()) {
				if (minWeight > pathWeight.get(towns) && dijkstra.contains(towns)) {
					minWeight = pathWeight.get(towns);
					sourceVertex = towns;
				}
			}

			for (Road roadNode : road) {
				if (roadNode.contains(sourceVertex)) {
					if (!roadNode.getDestination().equals(sourceVertex)
							&& dijkstra.contains(roadNode.getDestination())) {
						if (pathWeight.get(sourceVertex) + roadNode.getWeight() < pathWeight
								.get(roadNode.getDestination())) {
							townMap.put(roadNode.getDestination(), sourceVertex);
							pathWeight.put(roadNode.getDestination(),
									roadNode.getWeight() + pathWeight.get(sourceVertex));
						}
					}
				}
			}
			for (Road roadNode : road) {
				if (roadNode.contains(sourceVertex)) {
					if (!roadNode.getSource().equals(sourceVertex) && dijkstra.contains(roadNode.getSource()))
						if (pathWeight.get(sourceVertex) + roadNode.getWeight() < pathWeight
								.get(roadNode.getSource())) {
							townMap.put(roadNode.getSource(), sourceVertex);
							pathWeight.put(roadNode.getSource(), roadNode.getWeight() + pathWeight.get(sourceVertex));
						}
				}
			}

			dijkstra.remove(sourceVertex);
		}
	}

}