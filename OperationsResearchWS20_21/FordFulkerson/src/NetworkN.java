public class NetworkN extends FlowNetwork {
    private static int n;

    /**
     * The constructor for the class Network n which gets a n and saves it via the Super Constructor variable V (amount of knots) and in the class variable n.
     * @param n
     */
    public NetworkN(int n){
        super(n+1);
        this.n = n;
    }

    /**
     * The method "buildGraph" builds a FlowNetwork with n knots.
     * @return a FlowNetwork
     */
    public FlowNetwork buildGraph (){
        FlowNetwork result = new FlowNetwork(n);

        //iterate from every knot to every knot to build edges
        for(int i = 0 ; i < n; i++){
            for( int j = i+1; j < n; j++){
                //add the edge to the FlowNetwork
                result.addEdge(new FlowEdge(i,j,capacity(i,j)));
            }
        }
        return result;
    }

    /**
     * The method "capacity" calculates the needed capacity for an edge from i -> j
     * @param i start knot
     * @param j ending knot
     * @return the capacity for the given edge
     */
    private int capacity(int i, int j){
        if(j == i + 1){
            return 2 * n;
        }else if (j == i -1){
            return n;
        }else {
            return 0;
        }
    }
}
