/**
 * abstract class for local optimizer methods
 *
 */
public abstract class LocalOptimizer {

	protected ObjectiveFunction f;
	protected double accuracy;

	/**
	 * Constructor
	 * @param f objective function
	 * @param accuracy used for stopping criterion
	 */
	public LocalOptimizer(ObjectiveFunction f, double accuracy) {
		this.f = f;
		this.accuracy = accuracy;
	}

	/**
	 * solution method of the local optimizer
	 * @param x initial guess
	 * @return  last iterate, approximate solution
	 */
	public abstract double[] solve(double[] x);

}
