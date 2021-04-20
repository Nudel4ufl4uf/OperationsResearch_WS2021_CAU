import java.util.Arrays;

class zMain {

    public static void main(String[] args){
        //create the start vector x
        double[] x = {5,3};
        //create the "Rosenbrockfunction"
        ObjectiveFunction f = new Rosenbrock();
        //call the LocalOptimizer with the given values
        LocalOptimizer LO = new LOSub(f, 1E-8, 1 );
        //print the result
        System.out.println(Arrays.toString(LO.solve(x)));
    }
}
