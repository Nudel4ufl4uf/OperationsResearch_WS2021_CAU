public class Rosenbrock extends ObjectiveFunction {


    @Override
    protected double eval(double[] x) {
        double x1 = x[0];
        double x2 = x[1];

        return 100 * Math.pow (x2 - (Math.pow(x1,2)),2) + Math.pow(1 - x1,2);
    }
}
