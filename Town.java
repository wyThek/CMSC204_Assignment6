
/**
 * 
 * @author Yei Thek Wang 
 * This class is a vertex in a graph, and represents a
 * town that is attached to roads.
 *
 */
public class Town implements Comparable<Town> {

	protected String name = "";
	protected java.util.Set<Town> towns = new java.util.HashSet<Town>();
	protected int weight = Integer.MAX_VALUE;
	protected Town previous = null;

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public Town(String name) {

		this.name = name;
	}

	/**
	 * Copy constructor
	 * 
	 * @param templateTown
	 */
	public Town(Town templateTown) {

		this.name = templateTown.name;
		this.weight = templateTown.weight;
		this.towns = templateTown.towns;
		this.previous = templateTown.previous;
	}

	/**
	 * Reset
	 */
	public void reset() {
		this.weight = Integer.MAX_VALUE;
		this.previous = null;
	}

	/**
	 * Returns name of town
	 * 
	 * @return name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Return hashcode
	 * 
	 * @return hashCode
	 */
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}

	/**
	 * Returns toString return toString
	 */
	@Override
	public String toString() {
		return this.getName();
	}

	/**
	 * Returns true if town names are equal by comparison of names, false if not
	 * 
	 * @param t a Town object
	 * @return true or false
	 */
	@Override
	public boolean equals(Object obj) {
		return obj == this || this.name.toLowerCase().equals(((Town) obj).name.toLowerCase());
	}

	/**
	 * Compare 2 Town objects by comparing their names
	 * 
	 * @param o a Town object
	 * @return integer
	 */
	@Override
	public int compareTo(Town o) {
		return this.name.compareTo(o.name);
	}
}