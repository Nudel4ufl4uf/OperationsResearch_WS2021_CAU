/**
 * abstract class for real-valued objective function
 * with real-valued vector argument
 */
public abstract class ObjectiveFunction {

	/**
	 * Evaluates the function at the given point x
	 *
	 * @param x argument of the function
	 * @return function value
	 */
	protected abstract double eval(double[] x);

}
