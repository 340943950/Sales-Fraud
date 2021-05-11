/*
 * Date: May 6, 2021
 * Name: Adarsh Padalia and Iza Kurbanova
 * Teacher: Mr. Ho
 * Description: Checking for sales fraud in a set of sales data using Benford's Law
 * */

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset; 
import org.jfree.data.category.DefaultCategoryDataset; 
import javax.swing.JFrame;

 public class SalesFraud {
     public static void main(String[] args) {
        int[] arr1 = {4, 9, 3, 5, 4, 6, 5, 8, 7, 4, 1, 3, 1, 3, 6, 9, 4, 6};
        int[] arr2 = countNums(arr1, 9);
        reportFraud(arr2);
        CategoryDataset dataset = arrToDataset(arr2, "Counts");
        createChart("First Digits in Sales Data", "First Digit", "Count", dataset);
        printArray(arr2);
     }

     /**
      * This method takes in a category dataset and some labels and displays a bar graph in a popup window
      * 
      * @param chartTitle   The title of the bar graph
      * @param xLabel       The label for the x-axis
      * @param yLabel       The label for the y-axis
      * @param dataset      The category dataset to be used to create the bar graph
      */
     public static void createChart (String chartTitle, String xLabel, String yLabel, CategoryDataset dataset) {
        // Create the bar graph and store it in a variable
        JFreeChart barGraph = ChartFactory.createBarChart(chartTitle, xLabel, yLabel, dataset, PlotOrientation.VERTICAL, false, false, false);

        // Create a frame to display the bar graph
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.add(new ChartPanel(barGraph));
        frame.setVisible(true);
    }
    
    /**
     * This method takes in an array and a series name and converts it to a category dataset
     * 
     * @param arr           The array to be converted to a dataset
     * @param seriesName    The name of the series in the category dataset
     * @return datset       The array in category dataset format
     */
    public static CategoryDataset arrToDataset(int[] arr, String seriesName) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
     
        for (int i = 0; i < arr.length; i++) {
            dataset.addValue(arr[i], seriesName, Integer.toString(i+1));
        }
            
        return dataset;
    }
        
    /**
     * This method takes in an array and the desired size of the output array and returns the count of
     * each value that appears in that array
     * 
     * This method only works if arr doesn't have any negative values and arrSize is equal to the largest
     * possible value in arr
     * 
     * @param arr       The array in which to count values
     * @param arrSize   The desired size of the output array (this is also the maximum value in arr)
     * @return counts   The count of each value that appears in the array
     */
    public static int[] countNums (int[] arr, int arrSize) {
        int[] counts = new int[arrSize];
        
        for (int num : arr) {
            counts[num] += 1;
        }

        return counts;
    }

    /**
     * This method checks if the first digit is a one from 29 to 32 percent of the time
     * 
     * If it does occur from 29 to 32 percent of the time fraud likely didn't occur, otherwise may have occurred
     * 
     * @param arr   The array with the counts of each first digit
     */
    public static void reportFraud (int[] arr) {
        // The first element in the array is the frequency of the first digit occurring
        // According to Benford's Law, if the frequency is between 29% and 32% then fraud didn't occur
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }

        double frequency = arr[0]/sum;
        if (frequency >= 0.29 && frequency <= 0.32) {
            System.out.println("\nIt is likely that fraud didn't occur in this set of sales data.");
        }
        else {
            System.out.println("\nThere is a chance that fraud occurred in this set of sales data.");
        }
    }

    /**
     * This method prints out first digit counts wiht headers in table foramt
     * 
     * @param arr   The array with the results to be printed
     */
    public static void printArray (int[] arr) {
        System.out.println("\nFirst Digit Counts:");
        for (int i = 1; i <= 9; i++) {
            System.out.format("%5s", i);
        }
        System.out.println("\n  =============================================");
        for (int j = 0; j < arr.length; j++) {
            System.out.format("%5s", arr[j]);
        }
        System.out.println("\n");
    }
 }