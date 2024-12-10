package flow.maxFlow;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import flow.demandGraph.Edge;
import flow.demandGraph.Vertex;

/**
 * This class represents a flow network.
 */
public class FlowNetwork {

	/**
	 * A mapping from vertex IDs to Lists of edges connected to them.
	 */
	private HashMap<String, List<FlowEdge>> adjList;

	/**
	 * Constructs a FlowNetwork
	 * 
	 * @param graph A {@link flow.demandGraph.Vertex Vertex} and
	 *              {@link analysis.Edge Edge} representation of the graph.
	 */
	public FlowNetwork(HashMap<Vertex, List<Edge>> graph) {
		adjList = new HashMap<String, List<FlowEdge>>();
		for (Vertex v : graph.keySet()) {
			LinkedList<FlowEdge> list = new LinkedList<FlowEdge>();
			adjList.put(v.getId(), list);
		}

		for (Vertex v : graph.keySet()) {
			List<Edge> list = graph.get(v);
			for (Edge e : list) {
				addEdge(new FlowEdge(v.getId(), e.getNext(), e.getCapacity()));
			}
		}
	}

	/**
	 * Adds the given FlowEdge the the adjacency lists of both its connected
	 * vertices.
	 */
	public void addEdge(FlowEdge e) {
		String from = e.from();
		String to = e.to();
		adjList.get(from).add(e);
		adjList.get(to).add(e);
	}

	/**
	 * Returns the adjacency list of the given vertex.
	 */
	public Iterable<FlowEdge> adjList(String v) {
		return adjList.get(v);
	}
}
