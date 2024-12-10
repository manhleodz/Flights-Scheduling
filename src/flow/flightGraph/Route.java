package flow.flightGraph;

public class Route {

	private String dest;
	private int lowerBound, capacity;

	/**
	 * Route constructor.
	 * 
	 * @param dest
	 * @param lowerBound
	 * @param capacity
	 */
	public Route(String dest, int lowerBound, int capacity) {
		this.dest = dest;
		this.lowerBound = lowerBound;
		this.capacity = capacity;
	}

	public String getDest() {
		return dest;
	}

	public int getLowerBound() {
		return lowerBound;
	}

	public int getCapacity() {
		return capacity;
	}

	@Override
	public boolean equals(Object o) {
		if (this.dest.equals(((Route) o).dest)) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return dest + "|L:" + lowerBound + " " + "C:" + capacity;
	}
}