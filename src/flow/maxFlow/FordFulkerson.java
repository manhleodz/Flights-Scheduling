package flow.maxFlow;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class FordFulkerson {
	private int maxFlow;
	private FlowNetwork network;
	private String source;
	private String sink;
	private HashMap<String, FlowEdge> edgeTo;

	public FordFulkerson(FlowNetwork G, String source, String sink) {
		network = G;
		this.source = source;
		this.sink = sink;
		maxFlow = 0;

		while (hasAugmentingPath()) {
			int bottleneck = Integer.MAX_VALUE;
			for (String v = sink; !v.equals(source); v = edgeTo.get(v).other(v)) {
				bottleneck = Math.min(bottleneck, edgeTo.get(v)
						.residualCapacityTo(v));
			}
			for (String v = sink; !v.equals(source); v = edgeTo.get(v).other(v)) {
				edgeTo.get(v).addResidualFlowTo(v, bottleneck);
			}

			maxFlow += bottleneck;
		}

	}

	public boolean hasAugmentingPath() {
		edgeTo = new HashMap<String, FlowEdge>();
		HashSet<String> marked = new HashSet<String>();
		Queue<String> q = new LinkedList<String>();

		q.add(source);
		marked.add(source);
		while (!q.isEmpty()) {
			String v = q.remove();
			/* For each edge adjacent to v */
			for (FlowEdge e : network.adjList(v)) {
				String other = e.other(v);
				if (e.residualCapacityTo(other) > 0) {
					if (!marked.contains(other)) {
						edgeTo.put(other, e);
						marked.add(other);
						q.add(other);
					}
				}
			}
		}
		return marked.contains(sink);
	}

	public FlowNetwork network() {
		return network;
	}

	public int maxFlow() {
		return maxFlow;
	}

}
