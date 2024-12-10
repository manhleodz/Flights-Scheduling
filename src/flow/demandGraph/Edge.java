package flow.demandGraph;

public class Edge {

	private int capacity;
	private final String nextVertex;

	public Edge(String nextVertex, int capacity) {
		this.nextVertex = nextVertex;
		this.capacity = capacity;
	}

	public String getNext() {
		return nextVertex;
	}

	public int getCapacity() {
		return capacity;
	}

	public void decreaseCapacity() {
		capacity--;
	}

	@Override
	public String toString() {
		return nextVertex + " C: " + capacity;
	}
}
