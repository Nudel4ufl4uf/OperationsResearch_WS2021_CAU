import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        //create an Array to store the result
        double[] result;
        //create a new dataReader Object
        DataReader dataReader = new DataReader();
        //load the data
        File file = dataReader.loadData();
        //create a ArrayList for the data
        ArrayList<Double> [] data = dataReader.convertData(file);
        //create an object of the Interpreter
        Interpreter object = new Interpreter();
        //store the result of SVD
        result = object.SVD(data,3);
        //create an graphics object
        Visualizer2D graphics = new Visualizer2D(data,result);
        //start the graphics program (ACM)
        graphics.start();
    }
}
