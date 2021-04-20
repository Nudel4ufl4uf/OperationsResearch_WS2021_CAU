import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JFrame;

import acm.graphics.GLabel;
import acm.graphics.GLine;
import acm.graphics.GOval;
import acm.program.GraphicsProgram;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;

public class Visualizer2D extends GraphicsProgram {

    // Array of ArrayLists for coordinates
    private final ArrayList<Double>[] points;
    // ArrayList for the parameters in the vector x we get from the SVD
    private final double[] xVector;
    // the x-Scale for every object on the JFrame
    private final int xScale = 120;
    // the y-Scale for every object on the JFrame
    private final double yScale = 2.5;
    // move the origin position to get a centered (0,0)
    private final int yPos = 500;
    // move the origin position to get a centered (0,0)
    private final int xPos = 800;
    // the size of the points of the data
    private final int psize = 3;


    /**
     * Constructor to take the data
     * @param points Array of the lists with coordinates
     */
    public Visualizer2D (ArrayList<Double>[] points, double[] xVector) {
        this.points = points;
        this.xVector = xVector;
    }

    @Override
    public void run() {
        buildGraphics();
    }

    /**
     * constructs a coordinate system and builds the coordinates from the given data and connects the points
     */
    public void buildGraphics() {

        // single ArrayLists for easier use of the coordinates
        ArrayList<Double> ts = points[0];
        ArrayList<Double> zs = points[1];

        // create a new canvas
        JFrame background = new JFrame();
        // set the size of the background
        background.setSize(2500, 2500);

        // draw coordinate system
        GLine xAchsys = new GLine(xPos, -20 * xScale + yPos, xPos, 20 * xScale + yPos);
        add(xAchsys);
        GLine yAchsys = new GLine(-20 * xScale + yPos, yPos, 20 * xScale + yPos, yPos);
        add(yAchsys);

        // make labels for the x-axis
        for (int i = -6; i <= 6; i++) {
            GLine line = new GLine(i * xScale + xPos, -0.05 * xScale + yPos, i * xScale + xPos, 0.05 * xScale + yPos);
            GLabel number = new GLabel(Integer.toString(i), i * xScale + xPos, 0.15 * xScale + yPos);
            add(line);
            add(number);
        }

        // make labels for the y-axis
        for (int i = 160; i >= -160; i -= 20) {
            GLine line = new GLine(-0.05 * xScale + xPos, i * yScale + yPos, 0.05 * xScale + xPos, i * yScale + yPos);
            GLabel number = new GLabel(Integer.toString(i), -0.15 * xScale + xPos, i * (-1) * yScale + yPos);
            add(line);
            add(number);
        }

        // to save the previous points for connecting with the next one
        double savedPointX = ts.get(0) * xScale + xPos;
        double savedPointY = (-1) * zs.get(0) * yScale + yPos;

        // draw the first point
        GOval firstPoint = new GOval(savedPointX, savedPointY, psize, psize);
        firstPoint.setColor(Color.RED);
        firstPoint.setFilled(true);
        firstPoint.setFillColor(Color.RED);
        add(firstPoint);

        // draw points and lines between them from file's coordinates
        for (int i = 1; i < ts.size(); i++) {

            // variables to easier save the coordinates of the current point
            double newDatapointX = ts.get(i) * xScale + xPos;
            double newDatapointY = (-1) * zs.get(i) * yScale + yPos;

            // draws current point
            GOval newDatapoint = new GOval(newDatapointX, newDatapointY, psize, psize);
            newDatapoint.setColor(Color.RED);
            newDatapoint.setFilled(true);
            newDatapoint.setFillColor(Color.RED);
            add(newDatapoint);

            // draws a connecting line between the current and the saved previous point
            GLine connector = new GLine(savedPointX, savedPointY, newDatapointX, newDatapointY);
            connector.setColor(Color.RED);
            add(connector);

            // overwrite saved coordinates for next point
            savedPointX = newDatapointX;
            savedPointY = newDatapointY;

        }

        // reverse the Array of vector x for usable coefficients
        for(int i = 0; i < xVector.length / 2; i++) {
            double temp = xVector[i];
            xVector[i] = xVector[xVector.length - i - 1];
            xVector[xVector.length - i - 1] = temp;
        }

        // create the function as instance with the coefficients taken from xVector
        PolynomialFunction function = new PolynomialFunction(xVector);

        // have the first point for first line of the function
        double savedFunctionPointX = -6.0 * xScale + xPos;
        double savedFunctionPointY = (-1) * function.value(-6.0) * yScale + yPos;

        // draw a lot of small lines from point to point to get a detailed function graph
        for (double i = -5.9; i <= 7.0; i += 0.1) {

            // to safe one calculation later
            double XofI = i * xScale + xPos;
            double YofI = (-1) * function.value(i) * yScale + yPos;

            // draws current line
            GLine functionLine = new GLine(savedFunctionPointX, savedFunctionPointY, XofI, YofI);
            functionLine.setColor(Color.BLUE);
            add(functionLine);

            // overwrite saved coordinates for next point
            savedFunctionPointX = XofI;
            savedFunctionPointY = YofI;
        }
    }
}
