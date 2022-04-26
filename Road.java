/**
 * 
 * @author Yei Thek Wang
 * This class is an edge in a graph, and represents a road connecting two towns
 */
public class Road implements Comparable<Road> {

	protected Town source = null;
	protected Town destination = null;
	protected String name = "";
	protected int weight = 0;

	/**
	 * Constructor
	 * 
	 * @param source
	 * @param destination
	 * @param degrees
	 * @param name
	 */
	public Road(Town source, Town destination, int degrees, String name) {

		this.source = source;
		this.destination = destination;
		this.weight = degrees;
		this.name = name;
	}

	/**
	 * Constructor with distance preset to 1
	 * 
	 * @param source
	 * @param destination
	 * @param name
	 */
	public Road(Town source, Town destination, String name) {

		this.source = source;
		this.destination = destination;
		this.weight = 1;
		this.name = name;
	}

	/**
	 * Returns name of road
	 * 
	 * @return name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Return source town
	 * 
	 * @return
	 */
	public Town getSource() {
		return this.source;
	}

	/**
	 * Return source town
	 * 
	 * @return source
	 */
	public void setSource(Town source) {
		this.source = source;
	}

	/**
	 * Returns destination town
	 * 
	 * @return destination
	 */
	public Town getDestination() {
		return this.destination;
	}

	/**
	 * Set the destination
	 * 
	 * @param destination
	 */
	public void setDestination(Town destination) {
		this.destination = destination;
	}

	/**
	 * Returns distance of road
	 * 
	 * @return distance
	 */
	public int getWeight() {
		return this.weight;
	}

	/**
	 * Set the degree weight
	 * 
	 * @param degrees
	 */
	public void setDegrees(int degrees) {
		this.weight = degrees;
	}

	/**
	 * Return the Road's hashcode
	 */
	public int hashCode() {
		return this.name.hashCode();
	}

	/**
	 * toString
	 * 
	 * @return string representation of the road
	 */
	@Override
	public String toString() {
		return source + " to " + destination + " via " + getName();
	}

	/**
	 * Set the name of Road
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns true if the road contains a given town, false if it doesn't
	 * 
	 * @param town
	 * @return true or false
	 */
	public boolean contains(Town town) {
		return this.source.equals(town) || this.destination.equals(town);
	}

	/**
	 * Compares 2 Road objects by checking if they are both attached to the same
	 * towns
	 * 
	 * @param r a road object
	 * @return true or false
	 */
	@Override
	public boolean equals(Object r) {
		boolean smatch = this.source.equals(((Road) r).source) || this.source.equals(((Road) r).destination);
		boolean dmatch = this.destination.equals(((Road) r).source) || this.destination.equals(((Road) r).destination);
		boolean result = r == this || (smatch && dmatch);

		return result;
	}

	/**
	 * Compare 2 Road objects by comparing their names
	 * 
	 * @param o a Road object
	 * @return integer
	 */
	@Override
	public int compareTo(Road o) {
		return this.getWeight() - o.getWeight();
	}
}