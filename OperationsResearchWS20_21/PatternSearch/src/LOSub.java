import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunction;

import java.util.Arrays;

public class LOSub extends LocalOptimizer {
    //The input function
    protected ObjectiveFunction f;
    //The accuracy needed for the result
    protected double accuracy;
    //The start "stepSize" to move in
    protected double stepSize;

    /**
     * Constructor
     *  @param f        objective function
     * @param accuracy used for stopping criterion*/
    public LOSub(ObjectiveFunction f, double accuracy, double stepSize) {
        super(f, accuracy);
        this.f = f;
        this.accuracy = accuracy;
        this.stepSize = stepSize;
    }
    


    /**
     * The method "solve" solves the given function f with the math of the Local Optimizer.
     * @param x initial guess
     * @return xd | the result with the given accuracy
     */
    @Override
    public double[] solve (double[] x) {

        //##############################################################################################################
        //##############################################--Step 1--######################################################
        //##############################################################################################################

        //start point x is given from the method call as well as the "stepSize" and "accuracy" from the class variable

        //##############################################################################################################
        //##############################################--Step 2--######################################################
        //##############################################################################################################

        //save the current x as the start xd and do "stepTwo"
        double [] xd = stepTwo(x);

        //##############################################################################################################
        //##############################################--Step 3--######################################################
        //##############################################################################################################
        //case: xDach = x


        if(Arrays.equals(xd, x)){
            //check if the accuracy needed is reached
            if(stepSize <= accuracy){
                //return the final value
                return xd;
            }
            else {
                //lower the "stepSize" for a more precise result
                stepSize = stepSize / 2;
                //restart from step 2
                return solve(xd);
            }
        }

        //##############################################################################################################
        //##############################################--Step 4--######################################################
        //##############################################################################################################
        //case: xDach != x

        //we have to calculate xs = xd + (xd - x)
        double [] xs = addArrays(xd, (subArrays(xd,x)));

        //restart the step two separately with the new xs
        double [] xds = stepTwo(xs);

        //##############################################################################################################
        //##########################################--Step 5 & 6--######################################################
        //##############################################################################################################

        //check if f(xDachSchlange) < f(xDach)
        if(f.eval(xds) < f.eval(xd)){
            //restart from step 2 with xDachSchlange
            return solve(xds);
        }
        else {
            //restart from step 2 with xDach
            return solve(xd);
        }
    }

    /**
     * The method "stepTwo" calculates the step 2 from the Pattern Search Algorithm with a given start vector.
     * @param x the input vector
     * @return xd | the best optimized vector xd
     */
    private double [] stepTwo (double [] x){
        //save xd as the given "startPoint" x
        double [] xd = x.clone();
        //evaluate f on xd
        double fxd = f.eval(xd);

        for(int i = 0; i < x.length; i++){
            //create the "Einheitsvektor" e
            double [] e = new double [x.length];
            //set the needed dimension to one (for d = 2, (i=0: (1,0) ; i=1: (0,1)))
            e [i] = 1;

            //calculate the value if you add the "stepSize" in the direction i
            double valuePlus = f.eval(addArrays(xd, scalar (stepSize, e)));
            //calculate the value if you subtract the "stepSize" in the direction i
            double valueMinus =  f.eval(subArrays(xd, scalar (stepSize, e)));

            //case: adding the "stepSize" optimizes the distance => xd = xd + stepSize
            if(valuePlus < fxd){
                xd = addArrays(xd, scalar (stepSize, e));
            }
            //case: subtracting the "stepSize" optimizes the distance => xd = xd - stepSize
            else if(valueMinus < fxd) {
                xd = subArrays(xd, scalar(stepSize, e));
            }
        }
        return xd;
    }

    /**
     * The method "addArrays" adds two Arrays x and y
     * @param x
     * @param y
     * @return result | the result of x + y
     */
    private double[] addArrays(double[] x, double[] y) {

        if (x.length != y.length) {
            throw new IllegalArgumentException();
        }

        double[] result = new double[x.length];

        for (int i = 0; i < x.length; i++) {
            result[i] = x[i] + y[i];
        }

        return result;
    }

    /**
     * The method "subArrays" subtracts two arrays x and y.
     * @param x
     * @param y
     * @return result | the result of x - y
     */
    private double[] subArrays(double[] x, double[] y) {

        if (x.length != y.length) {
            throw new IllegalArgumentException();
        }

        double[] result = new double[x.length];

        for (int i = 0; i < x.length; i++) {
            result[i] = x[i] - y[i];
        }

        return result;
    }

    /**
     * The method scalar calculates the "Skalarprodukt" of scalar * arrays.
     * @param scalar
     * @param array
     * @return result | the result of scalar * array
     */
    private double[] scalar(double scalar, double[] array) {

        double[] result = new double[array.length];

        for (int i = 0; i < array.length; i++) {
            result[i] = scalar * array[i];
        }

        return result;

    }
}
