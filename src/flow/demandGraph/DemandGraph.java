package flow.demandGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import flow.flightGraph.Route;
import flow.main.Utils;

public class DemandGraph {

	private HashMap<Vertex, List<Edge>> graph;
	private int D;
	private boolean solvable;

	public DemandGraph(HashMap<String, ArrayList<Route>> flightMap) {
		graph = new HashMap<Vertex, List<Edge>>();
		initializeAndRemoveLowerBounds(flightMap);
		// System.out.println("initializeAndRemoveLowerBounds: " + graph);
		solvable = true;
		addMasterVertexes();
		// System.out.println("addMasterVertexes: " + graph);
	}

	private void initializeAndRemoveLowerBounds(HashMap<String, ArrayList<Route>> flightMap) {
		HashMap<String, Integer> demandChange = new HashMap<String, Integer>();

		for (String key : flightMap.keySet()) {
			ArrayList<Route> routes = flightMap.get(key);
			Vertex v;
			LinkedList<Edge> list = new LinkedList<Edge>();

			if (key.equals("S")) {
				v = new Vertex(key, -Utils.PLANES_AVAILABLE);
			} else {
				v = new Vertex(key, 0);
			}

			for (Route route : routes) {
				String dest = route.getDest();
				Edge edge = new Edge(dest, route.getCapacity());

				list.add(edge);

				if (route.getLowerBound() == 1) {

					edge.decreaseCapacity();

					/* Track changes of vertexes' demand */
					if (demandChange.containsKey(key)) {
						int offset = demandChange.get(key);
						demandChange.put(key, offset + 1);
					} else {
						demandChange.put(key, 1);
					}

					if (demandChange.containsKey(dest)) {
						int offset = demandChange.get(dest);
						demandChange.put(dest, offset - 1);
					} else {
						demandChange.put(dest, -1);
					}
				}

			}
			graph.put(v, list);
		}

		graph.put(new Vertex("T", Utils.PLANES_AVAILABLE), new LinkedList<Edge>());

		for (Vertex v : graph.keySet()) {
			String s = v.getId();

			if (demandChange.containsKey(s)) {
				int offset = demandChange.get(s);
				v.changeDemand(offset);
			}
		}
	}

	private void addMasterVertexes() {
		int D_plus = 0;
		int D_minus = 0;
		Vertex S_master = new Vertex("S*", -1);
		Vertex T_master = new Vertex("T*", -1);

		List<Edge> s_list = new LinkedList<Edge>();

		for (Vertex v : graph.keySet()) {
			int d = v.getDemand();
			if (d > 0) {
				D_plus += d;
				Edge e = new Edge("T*", d);
				List<Edge> list = graph.get(v);
				list.add(e);
			}
			if (d < 0) {
				D_minus -= d;
				Edge e = new Edge(v.getId(), -d);
				s_list.add(e);
			}
		}

		graph.put(S_master, s_list);
		graph.put(T_master, new LinkedList<Edge>());

		D = D_plus;

		if (D_minus != D_plus)
			solvable = false;
	}

	public int getD() {
		return D;
	}

	public boolean isSolvable() {
		return solvable;
	}

	public String source() {
		return "S*";
	}

	public String sink() {
		return "T*";
	}

	public HashMap<Vertex, List<Edge>> getGraph() {
		return graph;
	}
}
