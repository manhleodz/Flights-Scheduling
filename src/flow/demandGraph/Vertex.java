package flow.demandGraph;

public class Vertex {

	private int demand;
	private final String id;

	public Vertex(String id, int demand) {
		this.id = id;
		this.demand = demand;
	}

	public String getId() {
		return id;
	}

	public int getDemand() {
		return demand;
	}

	public void changeDemand(int offset) {
		demand += offset;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public String toString() {
		return "ID: " + id + " D: " + demand;
	}

}
