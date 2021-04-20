import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        //create a new SVM
        SVM mySVM = new SVM();
        //load dataA and convert it to an Array
        ArrayList fileA = mySVM.convertData(mySVM.loadData());
        //load dataB and convert it to an Array
        ArrayList fileB = mySVM.convertData(mySVM.loadData());
        //create new visuals
        SVMVisualizer2D visuals = new SVMVisualizer2D(fileA, fileB);
        //analyze the the data A and B
        mySVM.analyzeData(fileA, fileB);
        //start the visuals
        visuals.start();
    }
}
