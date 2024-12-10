package flow.main;

import flow.demandGraph.DemandGraph;
import flow.flightGraph.Graph;
import flow.maxFlow.FlowNetwork;
import flow.maxFlow.FordFulkerson;

/**
 * Holds the main function, where the magic happens.
 */
public class Main {

	public static void main(String... args) {
		try {
			Graph inputGraph = new Graph(Utils.readFile());
			DemandGraph demandGraph = new DemandGraph(inputGraph.getGraph());
			if (!demandGraph.isSolvable()) {
				System.out.println("The problem is not solvable");
				return;
			}
			FlowNetwork flowNetwork = new FlowNetwork(demandGraph.getGraph());

			FordFulkerson ff = new FordFulkerson(flowNetwork, demandGraph.source(),
					demandGraph.sink());
			if (demandGraph.getD() != ff.maxFlow()) {
				System.out.println("More planes are needed");
				return;
			}

			Flights paths = new Flights(flowNetwork);
			paths.printFlights();

		} catch (Exception e) {
			System.err.println("Something went wrong");
			e.printStackTrace();
		}
	}
}
