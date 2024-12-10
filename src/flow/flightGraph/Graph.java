package flow.flightGraph;

import java.util.ArrayList;
import java.util.HashMap;

import flow.main.Utils;

public class Graph {

	private HashMap<String, ArrayList<Route>> graph;
	private ArrayList<Flight> flights;
	private ArrayList<Route> routes;

	public Graph(ArrayList<Flight> flights) {
		this.flights = flights;
		System.out.println(flights.size());
		graph = new HashMap<String, ArrayList<Route>>();

		initializeGraph();
		// System.out.println("initializeGraph " + graph);
		// System.out.println("--------------------------------");
		extendGraph();
		// System.out.println("extendGraph " + graph);
	}

	private void initializeGraph() {
		ArrayList<Route> sRoutes = new ArrayList<Route>();

		for (Flight f : flights) {
			String origKey = f.getOrigin() + "_" + f.getDepTime();
			String destKey = f.getDest() + "_" + f.getArrTime();
			routes = new ArrayList<Route>();

			if (graph.containsKey(origKey)) {
				routes.addAll(graph.get(origKey));
			}
			routes.add(new Route(destKey, 1, 1));
			graph.put(origKey, routes);

			Route sRoute = new Route(origKey, 0, 1);
			if (!sRoutes.contains(sRoute)) {
				sRoutes.add(sRoute);
			}

			ArrayList<Route> tRoutes = new ArrayList<Route>();
			tRoutes.add(new Route("T", 0, 1));
			graph.put(destKey, tRoutes);
		}

		sRoutes.add(new Route("T", 0, Utils.PLANES_AVAILABLE));
		graph.put("S", sRoutes);

	}

	private void extendGraph() {
		for (Flight fi : flights) {
			for (Flight fj : flights) {
				if (fi != fj) {
					if (fj.isSameAirportAndReachableBy(fi) || fj.isReachableBy(fi)) {
						String key = fi.getDest() + "_" + fi.getArrTime();
						routes = new ArrayList<Route>();

						// Adds (fi.getDest(),fj.getOrigin()) edge.
						if (graph.containsKey(key)) {
							routes.addAll(graph.get(key));
						}
						routes.add(new Route(fj.getOrigin() + "_" + fj.getDepTime(), 0, 1));
						graph.put(key, routes);
					}
				}
			}
		}
	}

	public HashMap<String, ArrayList<Route>> getGraph() {
		return graph;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (String key : graph.keySet()) {
			sb.append(key + " -> " + graph.get(key) + "\n");
		}
		return sb.toString();
	}
}
