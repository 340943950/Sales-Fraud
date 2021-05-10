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
    public static CategoryDataset arrToDataset(int[] arr, String seriesName) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
     
        for (int i = 0; i < arr.length; i++) {
            dataset.addValue(arr[i], seriesName, Integer.toString(i+1));
        }
            
        return dataset;
    }
    public static int[] countNums (int[] arr, int arrSize) {
        int[] counts = new int[arrSize];
        
        for (int num : arr) {
            counts[num] += 1;
        }

        return counts;
    }
 }