public class Main {

    public static void main(String[] args){

        // create flow network with V vertices and E edges
        int V = 4;
        int C = 5;
        int s = 0, t = V-1;
        FlowNetwork G = new FlowNetwork(V);

        G.addEdge(new FlowEdge(0,1,C));
        G.addEdge(new FlowEdge(0,2,C));
        G.addEdge(new FlowEdge(1,2,C));
        G.addEdge(new FlowEdge(1,3,C));
        G.addEdge(new FlowEdge(2,3,C));
        //print the graph
        StdOut.println(G);

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
