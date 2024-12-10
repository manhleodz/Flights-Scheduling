package flow.maxFlow;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import flow.demandGraph.Edge;
import flow.demandGraph.Vertex;

public class FlowNetwork {

	private HashMap<String, List<FlowEdge>> adjList;

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

	public void addEdge(FlowEdge e) {
		String from = e.from();
		String to = e.to();
		adjList.get(from).add(e);
		adjList.get(to).add(e);
	}

	public Iterable<FlowEdge> adjList(String v) {
		return adjList.get(v);
	}
}
