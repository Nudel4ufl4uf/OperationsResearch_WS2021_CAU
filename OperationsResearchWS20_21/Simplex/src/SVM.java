import org.apache.commons.math3.optim.MaxIter;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.*;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.math3.optim.linear.SimplexSolver;

/**
 * The class {@link SVM} can convert and load.txt files.
 * Furthermore it can analyze the given data to get the correct SVM.
 */

public class SVM extends Component {
    //number of iterations for the Simplex Solver
    private final int iterations = 300;

    /**
     * The method ConvertData handls input .txt data with a specific layout and transforms it
     * into a ArrayList of vectors, storaged in an Arraylist.
     *
     * @param file input .txt file (!!specific layout!!)
     * @return an ArrayList of vectors
     */
    public ArrayList<ArrayList<Double>> convertData(File file) {
        //create an ArrayList of ArrayList to store the data from a .txt file
        ArrayList<ArrayList<Double>> data = new ArrayList<>();
        //an ArrayList to store the vector of the current line in the .txt file
        ArrayList<Double> vector;

        try {
            //create a reader to go through the given file
            BufferedReader reader = new BufferedReader(new FileReader(file));
            //buffer to the first line
            String line = reader.readLine();
            //go through lines until the last on is checked
            while (line != null) {
                vector = new ArrayList<>();
                //save a detected coordinate part
                double coordinate = 0;
                //save the char before
                char before = ' ';
                //create a StringBuilder to append sequences of chars from file
                StringBuilder currentValue = new StringBuilder();
                //iterate through the current line
                for (int i = 0; i < line.length(); i++) {
                    //check if a number sequence has started or is still being saved
                    if (line.charAt(i) != ' ' && i != line.length() - 1) {
                        //append the current char to the existing sequence
                        currentValue.append(line.charAt(i));
                    }
                    //check if a sequence of numbers is finished
                    else if (line.charAt(i) == ' ' && before != ' ' || i == line.length() - 1) {
                        /*check if the current char is the last of the line because a line ends with a char != " ",
                        which means it has to be stored ass well. Only then it is possible to end the line
                        without missing the last char*/
                        if (i == line.length() - 1) {
                            //append the current char to the existing sequence
                            currentValue.append(line.charAt(i));
                        }
                        //transform the given StringBuilder type to String and then to a double
                        coordinate = Double.parseDouble(currentValue.toString());
                        //add the coordinate part to the vector ArrayList
                        vector.add(coordinate);
                        //delete the saved vector part
                        currentValue.delete(0, currentValue.length());
                    }
                    //override the element before
                    before = line.charAt(i);
                }
                //add the vector arrayList to the data because the line is done
                data.add(vector);
                //update to the next line
                line = reader.readLine();
            }
            reader.close();
        } catch (FileNotFoundException FNF) {
            System.out.println("This file was not found!");
        } catch (IOException IOE) {
            System.out.println("This file is no longer available or not readable!");
        }

        System.out.println(data.toString());
        return data;
    }

    /**
     * A method to analyze the given data in a graph
     *
     * @param dataA dataB
     */
    public void analyzeData(ArrayList<ArrayList<Double>> dataA, ArrayList<ArrayList<Double>> dataB) {
        Collection<LinearConstraint> constraints = new ArrayList<LinearConstraint>();

        //case of dim=1
        if (dataA.get(1).size() == 1) {
            //build the cost function which has to be zero
            LinearObjectiveFunction f = new LinearObjectiveFunction(new double[]{0, 0}, 0);

            //build a constrain for every vector for DataA
            for (int i = 0; i < dataA.size(); i++) {
                constraints.add(new LinearConstraint(new double[]{dataA.get(i).get(0), 1}, Relationship.GEQ, 1));
            }
            //build a constrain for every vector for DataB
            for (int i = 0; i < dataB.size(); i++) {
                constraints.add(new LinearConstraint(new double[]{dataB.get(i).get(0), 1}, Relationship.LEQ, -1));
            }
            //create a SimplexSolver and run it on the created constrains
            SimplexSolver solver = new SimplexSolver();
            PointValuePair solution = solver.optimize(new MaxIter(iterations), f, new LinearConstraintSet(constraints),
                    GoalType.MINIMIZE, new NonNegativeConstraint(false));

            double w1 = solution.getPoint()[0];
            double beta = solution.getPoint()[1];
            double min = solution.getValue();
            System.out.println(w1 + " " + beta + " " + min);
        }
        //case of dim=2
        else if (dataA.get(1).size() == 2) {
            //build the cost function which has to be zero
            LinearObjectiveFunction f = new LinearObjectiveFunction(new double[]{0, 0, 0}, 0);

            //build a constrain for every vector for DataA
            for (int i = 0; i < dataA.size(); i++) {
                constraints.add(new LinearConstraint(new double[]{dataA.get(i).get(0), dataA.get(i).get(1), 1}, Relationship.GEQ, 1));
            }
            //build a constrain for every vector for DataB
            for (int i = 0; i < dataB.size(); i++) {
                constraints.add(new LinearConstraint(new double[]{dataB.get(i).get(0), dataB.get(i).get(1), 1}, Relationship.LEQ, -1));
            }
            //create a SimplexSolver and run it on the created constrains
            SimplexSolver solver = new SimplexSolver();
            PointValuePair solution = solver.optimize(new MaxIter(iterations), f, new LinearConstraintSet(constraints),
                    GoalType.MINIMIZE, new NonNegativeConstraint(false));

            double w1 = solution.getPoint()[0];
            double w2 = solution.getPoint()[1];
            double beta = solution.getPoint()[2];
            double min = solution.getValue();
            System.out.println(w1 + " " + w2 + " " + beta + " " + min);
            writeData(w1, w2, beta);
        }
        //case of dim = 3
        else if (dataA.get(1).size() == 3) {
            //build the cost function which has to be zero
            LinearObjectiveFunction f = new LinearObjectiveFunction(new double[]{0, 0, 0, 0}, 0);

            //build a constrain for every vector for DataA
            for (int i = 0; i < dataA.size(); i++) {
                constraints.add(new LinearConstraint(new double[]{dataA.get(i).get(0), dataA.get(i).get(1), dataA.get(i).get(2), 1}, Relationship.GEQ, 1));
            }
            //build a constrain for every vector for DataB
            for (int i = 0; i < dataB.size(); i++) {
                constraints.add(new LinearConstraint(new double[]{dataB.get(i).get(0), dataB.get(i).get(1), dataB.get(i).get(2), 1}, Relationship.LEQ, -1));
            }
            //create a SimplexSolver and run it on the created constrains
            SimplexSolver solver = new SimplexSolver();
            PointValuePair solution = solver.optimize(new MaxIter(iterations), f, new LinearConstraintSet(constraints),
                    GoalType.MINIMIZE, new NonNegativeConstraint(false));

            double w1 = solution.getPoint()[0];
            double w2 = solution.getPoint()[1];
            double w3 = solution.getPoint()[2];
            double beta = solution.getPoint()[3];
            double min = solution.getValue();
            System.out.println(w1 + " " + w2 + " " + w3 + " " + beta + " " + min);
        }
        //case of invalid dim
        else {
            throw new IllegalArgumentException("This Dimension is invalid!");
        }


    }

    /**
     * This method "loadData" loads a data selected by the user in the pop up window.
     * ->!!only one file at a time!!
     * ->!!only .txt files!!
     *
     * @return filepath of selected data
     */
    public File loadData() {
        //create a JFileChooser
        JFileChooser fileChooser = new JFileChooser();
        //set the start directory
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        //only one file at a time
        fileChooser.setMultiSelectionEnabled(false);
        //only .txt files are possible
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt file", "txt", "text");
        fileChooser.setFileFilter(filter);

        System.out.println("Choose your datasets for the support vector machine:");
        fileChooser.showOpenDialog(this);
        File selectedData = fileChooser.getSelectedFile();

        return selectedData;
    }

    /**
     * This method "writeData" writes the given data in a new .txt file.
     *
     * @param w1
     * @param w2
     * @param B
     */
    public void writeData(double w1, double w2, double B) {

        try {
            //create a new file at the given path
            File hyperplane = new File("C:\\Users\\Admin\\Desktop\\hyperplane.txt");
            if (hyperplane.createNewFile()) {
                System.out.println("This file was created!");
            } else {
                System.out.println("This file already exists!");
            }
            //create a new file writer to write in the new file created above
            FileWriter myWriter = new FileWriter(hyperplane);
            //write the variables given from the simplex Algorithm
            myWriter.write("   " + w1 + "     " + w2 + "     " + B);
            myWriter.close();

        } catch (IOException IOE) {
            System.out.println("This file is no longer available!");
        }
    }
}