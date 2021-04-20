import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.SingularValueDecomposition;
import java.lang.reflect.Array;
import java.util.ArrayList;


public class Interpreter {
    /**
     * The main method to start different methods for calculating the svD for given data in an ArrayList.
     * First transforming the ArrayList to an double[][] Array to be able to use the data type RealMatrix for further operations.
     * Then calculate using the "SVDCalc Method" and return the data.
     * @param data The input data
     * @param dim The dimension of the polynomial wanted
     * @return result | ArrayList
     */
    public double[] SVD (final ArrayList<Double>[] data, final int dim){

        //two ArrayLists "data1" and "data2" to store the two separate ArrayLists from the input.
        ArrayList<Double> data1 = data [0];
        ArrayList<Double> data2 = data [1];
        //two doubleArrays to store the two transformed Arrays element by element
        double[][] matrixA = new double [data [0].size()][1] ;
        double[][] matrixZ = new double [data [1].size()][1] ;

        //for loop to store the separate elements
        for(int i = 0; i < data [0].size(); i++){
            matrixA[i][0] = data1.get(i);
            matrixZ[i][0] = data2.get(i);
        }

        //create the RealArrays needed for the svD calculator based on the transformed Arrays
        RealMatrix A = new Array2DRowRealMatrix(matrixA);
        RealMatrix Z = new Array2DRowRealMatrix(matrixZ);

        //call "buildA" to build the full Matrices A depending on the given dimension
        RealMatrix AT = buildA(A,dim);
        //call "SVDCalc" to calculate the result
        RealMatrix x = SVDCalc(AT, Z, dim);

        //an Array List for the result at the end to be returned
        double[] result = new double[dim+1];

        //go through the whole RealMatrix to transform it into the ArrayList
        for(int i = 0; i <= dim; i++){
            result[i] = x.getEntry(i,0);
        }
        return result;
    }

    /**
     * The methode "SVDCalc" calculates the minimal svD by using the apache.commons library.
     * The different steps are from slide 10 from the Presentation "Lösung mit Singulärwertzerlegung".
     *
     * ##(1)## zD := UT * Z
     * ##(2)## xi-Dach := zi-Dach/svDi
     * ##(3)## x := V * x-Dach
     *
     * @param A The Matrix A
     * @param Z The Matrix Z
     * @param dim The dimension n
     * @return x | the result in type RealMatrix
     */

    private RealMatrix SVDCalc (final RealMatrix A,final RealMatrix Z, final int dim){

        //create an Singular Value Decomposition object for further operations
        SingularValueDecomposition svdH = new SingularValueDecomposition(A);

        //create all the Components needed to perform the steps on slide 10: zD:= UT*Z, xD:= zD/asvD and x:= V*xD
        RealMatrix UT = svdH.getUT();
        RealMatrix V = svdH.getV();
        double[] asvD = svdH.getSingularValues();

        //--------------------------------------------------------------------------------------------------------------
        // ##(1)## calculate the "z-Dach" from the lecture by zD := UT * Z
        RealMatrix zD = UT.multiply(Z);

        System.out.println("##(1)## Matrix zD: " + zD.toString());

        //--------------------------------------------------------------------------------------------------------------
        // ##(2)## create the "x-Dach" from the lecture by xi-Dach := zi-Dach/svDi
        // create a matrix for step (2) to be able to use the data type RealMatrix
        double [][] matrix = new double [A.getColumnDimension()][1];
        RealMatrix xD = new Array2DRowRealMatrix(matrix);

        //create xDi for every i (i,..,dim)
        for(int i = 0; i<dim;i++){
            double x = Array.getDouble(asvD,i);
            xD.setEntry(i,0,zD.getEntry(i,0) / x );
        }

        System.out.println("##(2)## Matrix xD: " + xD.toString());

        //--------------------------------------------------------------------------------------------------------------
        // ##(3)## calculate the solution as x like mentioned in the lecture by x := V * x-Dach
        System.out.println("##(3)## Matrix x: " +V.multiply(xD));
        return V.multiply(xD);
    }

    /**
     * The method "buildA" builds the complete matrix A depending on the given dimension in the Form:
     * t1^n ... t1^1 1
     * ...      ... ... = A
     * tm^n ... tm^n 1
     *
     * @param A The one dimension matrix of the original input data by the user
     * @param dim The dimension
     * @return m The complete Matrix
     */
    private RealMatrix buildA (final RealMatrix A, final int dim){
        //create the matrix needed to build the complete Matrix A
        double [][] matrix = new double [A.getRowDimension()][dim+1];
        RealMatrix m = new Array2DRowRealMatrix(matrix);

        //create a variable d which holds the current dimension to calculate the power for every element
        int d = dim;

        //two for loops to go though every line (i), column by column (k), and set the entry from the input matrix
        //to bring it in the from shown in the description.
        for(int i = 0; i < A.getRowDimension(); i++){
            for (int k = 0; k <= dim; k++){
                //case for the last element that needs to be 1
                if(k == dim){
                 m.setEntry(i,k,1);
                }
                else {
                    m.setEntry(i, k, Math.pow(A.getEntry(i, 0),d));
                    d = d - 1;
                }
            }
            //reset the dimension for the power operation
            d = dim;
        }
        return m;
    }

}


