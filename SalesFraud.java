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

 public class SalesFraudCopy {
     public static void main(String[] args) {
        
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
 }