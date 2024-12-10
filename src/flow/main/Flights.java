package flow.main;

import java.util.LinkedList;
import java.util.List;

import flow.maxFlow.FlowEdge;
import flow.maxFlow.FlowNetwork;

public class Flights {

	List<List<String>> flights;

	public Flights(FlowNetwork network) {
		flights = new LinkedList<List<String>>();

		Iterable<FlowEdge> adjS = network.adjList("S");

		for (FlowEdge edge : adjS) {
			String other = edge.other("S");
			if (!other.equals("S*") && edge.residualCapacityTo(other) == 0) {
				List<String> temp = new LinkedList<String>();
				temp.add(other);
				flights.add(temp);
			}
		}
		for (List<String> list : flights) {
			expandFlightList(list, network);
		}
	}

	private void expandFlightList(List<String> list, FlowNetwork network) {
		String ID = list.get(0);
		while (!ID.equals("T")) {
			for (FlowEdge edge : network.adjList(ID)) {
				String other = edge.other(ID);
				if (edge.from().equals(ID)
						&& edge.residualCapacityTo(other) == 0) {
					ID = other;
					if (!ID.equals("T"))
						list.add(ID);
					break;
				}
			}
		}

	}

	public void printFlights() {
		System.out.println("Number of planes used: " + flights.size());

		for (List<String> path : flights) {
			System.out.println("Flight path:");
			for (String id : path)
				System.out.print(id + " ");
			System.out.println();
		}
	}
}
