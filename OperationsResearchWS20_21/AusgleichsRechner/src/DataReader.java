import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class DataReader extends Component {
    /**
     * The method loadData opens a browser to choose your .txt file.
     * @return selectedData | File
     */
    public File loadData() {
        // create a JFileChooser
        JFileChooser fileChooser = new JFileChooser();
        // set the start directory
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        // only one file at a time
        fileChooser.setMultiSelectionEnabled(false);
        // only .txt files are possible
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt file", "txt", "text");
        fileChooser.setFileFilter(filter);

        System.out.println("Choose your text file with the coordinates:");
        fileChooser.showOpenDialog(this);
        File selectedData = fileChooser.getSelectedFile();

        System.out.println("File successful loaded.");

        return selectedData;
    }

    /**
     * The method "convertData" converts the chosen .txt file into an ArrayList of type double.
     * !!NOTE!! the converter only works for a specific layout of the .txt file!
     * @param file Takes a .txt file
     * @return points | ArrayList of points in form [{x1,...,xn},{y1,...yn}]
     */
    public ArrayList<Double>[] convertData(File file) {
        // create an Array of two ArrayLists to store the coordinates from the .txt file
        ArrayList<Double>[] points = new ArrayList[2];
        // the ArrayList for t_k wth k = 1, ..., m
        ArrayList<Double> ts = new ArrayList<Double>();
        // the ArrayList for z_k = y(t_k) = a*t_k + b
        ArrayList<Double> zs = new ArrayList<Double>();

        try {
            // create a reader to go through the given file
            BufferedReader reader = new BufferedReader(new FileReader(file));
            // buffer to the first line
            String line = reader.readLine();
            // go through lines until the last on is checked
            while (line != null) {

                double coordinate;

                // create a StringBuilder to append sequences of chars from file
                StringBuilder currentValue = new StringBuilder();

                // iterate through the current line
                for (int i = 0; i < line.length(); i++) {
                    // if the char is a '(' we don't need it, for ',' and
                    // the second to last char (we don't need to check the last ')') there are specific actions
                    if (line.charAt(i) != '(' && line.charAt(i) != ',' && i != line.length() - 1) {
                        // append the current char to the existing sequence
                        currentValue.append(line.charAt(i));
                    }
                    // check if the first number is finished
                    else if (line.charAt(i) == ',') {
                        // transform the given StringBuilder type to String and then to a double
                        coordinate = Double.parseDouble(currentValue.toString());
                        // and add it to the ArraysList for the ts
                        ts.add(coordinate);
                        // empty the currentValue
                        currentValue.delete(0, currentValue.length());
                        // check if the second number is finished
                    } else if (i == line.length() - 1) {
                        // transform the given StringBuilder type to String and then to a double
                        coordinate = Double.parseDouble(currentValue.toString());
                        // and add it to the ArrayList for the zs
                        zs.add(coordinate);
                        // empty the currentValue
                        currentValue.delete(0, currentValue.length());
                    }
                }
                // jump to the next line
                line = reader.readLine();
            }
            // close the reader if line == null
            reader.close();

        } catch (FileNotFoundException FNF) {
            System.out.println("This file was not found!");
        } catch (IOException IOE) {
            System.out.println("This file is no longer available or not readable!");
        }
        points[0] = ts;
        points[1] = zs;

        return points;
    }
}


