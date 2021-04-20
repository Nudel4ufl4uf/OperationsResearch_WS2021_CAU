import java.awt.*;
import java.util.ArrayList;

import acm.graphics.GLabel;
import acm.graphics.GLine;
import acm.graphics.GOval;
import acm.program.GraphicsProgram;

import javax.swing.*;

/**
 * The class {@link SVMVisualizer2D} can visualize the result ov the SVM in 2D Graphics for d=2.
 * !!Note: it only works with 2D, otherwise the results will be wrong!!
 */

public class SVMVisualizer2D extends GraphicsProgram {
    //two Arrays for dataA and dataB
    private ArrayList<ArrayList<Double>> listA;
    private ArrayList<ArrayList<Double>> listB;

    //the scale for every object on the JFrame
    private final double scale = 175;
    //move the origin position to get a centered (0,0)
    private final double pos = 400;
    //the size of the points of the data
    private final double psize = 3;

    //Constructor to take the data
    public SVMVisualizer2D(ArrayList<ArrayList<Double>> listA, ArrayList<ArrayList<Double>> listB) {
        this.listA = listA;
        this.listB = listB;
    }

    @Override
    public void run() {
        buildGraphics();
    }

    /**
     * The method "buildGraphics" builds the coordinates
     * from the given data on the 2D canvas including the Hyperplane.
     */
    public void buildGraphics() {
        //create a new canvas
        JFrame background = new JFrame();
        //set the size of the background
        background.setSize(2500, 2500);

        //draw the hyperplane based on the data from the txt file provided
        buildHyperplane();

        //draw coordinate system
        GLine xAchsys = new GLine(0 + pos, -20 * scale + pos, 0 + pos, 20 * scale + pos);
        add(xAchsys);
        GLine yAchsys = new GLine(-20 * scale + pos, 0 + pos, 20 * scale + pos, 0 + pos);
        add(yAchsys);

        //make labels for the x-Achsys
        for (int i = -10; i <= 10; i++) {
            GLine line = new GLine(i * scale + pos, -0.05 * scale + pos, i * scale + pos, 0.05 * scale + pos);
            GLabel number = new GLabel(Integer.toString(i), i * scale + pos, 0.15 * scale + pos);
            add(line);
            add(number);
        }
        //make labels for the y-Achsys
        for (int i = 10; i >= -10; i--) {
            GLine line = new GLine(-0.05 * scale + pos, i * scale + pos, 0.05 * scale + pos, i * scale + pos);
            GLabel number = new GLabel(Integer.toString(i), -0.15 * scale + pos, i * (-1) * scale + pos);
            add(line);
            add(number);
        }

        //draw the red points for dataA
        for (int i = 0; i < listA.size(); i++) {
            GOval point = new GOval(listA.get(i).get(0) * scale + pos, listA.get(i).get(1) * scale + pos, psize, psize);
            point.setColor(Color.RED);
            point.setFilled(true);
            point.setFillColor(Color.RED);
            add(point);
        }
        //draw the blue points for dataB
        for (int i = 0; i < listB.size(); i++) {
            GOval point = new GOval(listB.get(i).get(0) * scale + pos, listB.get(i).get(1) * scale + pos, psize, psize);
            point.setColor(Color.BLUE);
            point.setFilled(true);
            point.setFillColor(Color.BLUE);
            add(point);
        }
    }


    /**
     * The method "buildCoordinates" builds the hyperplane out of the given variables provided by the .txt file.
     */
    public void buildHyperplane() {
        //create  a new SVM object
        SVM hyperplane = new SVM();
        //read and convert the data for the hyperplane
        ArrayList<ArrayList<Double>> hyperplaneFile = hyperplane.convertData(hyperplane.loadData());

        //save the data needed for the coordinates in variables
        double w1 = hyperplaneFile.get(0).get(0);
        double w2 = hyperplaneFile.get(0).get(1);
        double beta = hyperplaneFile.get(0).get(2);
        double x1 = -10;
        double x2 = 10;
        double y1 = (-1 / w2) * (w1 * x1 + beta);
        double y2 = (-1 / w2) * (w1 * x2 + beta);

        //draw the hyperplane
        GLine hyperPlaneLine = new GLine(x1 * scale + pos, y1 * scale + pos, x2 * scale + pos, y2 * scale + pos);
        hyperPlaneLine.setColor(Color.GREEN);
        add(hyperPlaneLine);
    }
}