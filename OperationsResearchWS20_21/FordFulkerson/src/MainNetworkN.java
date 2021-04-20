public class MainNetworkN {
    public static void main(String[] args) {
        // create flow network with V vertices
        int V = 100;
        int s = 0, t = V-1;
        //build the FlowNetwork with V knots
        FlowNetwork G = new NetworkN(V).buildGraph();
        //StdOut.println(G);

        // compute maximum flow and minimum cut
        FordFulkerson maxFlow = new FordFulkerson(G, s, t);

        StdOut.println("Max flow from " + s + " to " + t);
        for (int v = 0; v < G.V(); v++) {
            for (FlowEdge e : G.adj(v)) {
                if ((v == e.from()) && e.flow() > 0)
                    StdOut.println("   " + e);
            }
        }

        // print min-cut
        StdOut.print("Min cut: ");
        for (int v = 0; v < G.V(); v++) {
            if (maxFlow.inCut(v)) StdOut.print(v + " ");
        }
        StdOut.println();
        StdOut.println("Max flow value = " +  maxFlow.value());
    }
}
