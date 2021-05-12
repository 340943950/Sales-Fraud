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
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.text.DecimalFormat;

 public class SalesFraud {
     public static void main(String[] args) {
        Scanner reader = new Scanner (System.in); // Initializing Scanner
        boolean verifyFile = false;
        String salesFile;
        do{
            // Loops if user input is wrong
            // Prompts user for file
            System.out.println("\nPlease enter the full file path and name of the file with the sales you would like to verify (EX:C:\\Users\\John\\Desktop\\ICS3U\\sales.csv)");
            salesFile = reader.nextLine(); // User file input

            // Verifys user input
            System.out.println("Please confirm that your file input is correct (YES or NO)");
            String fileConfirmation = reader.nextLine();
            if(fileConfirmation.equalsIgnoreCase("yes")){
                verifyFile = true;
            }
            else if(fileConfirmation.equalsIgnoreCase("no")){
                verifyFile = false;
            }
            else{
                System.out.println("Your confirmation input was invalid. We will assume it was NO");
                verifyFile = false;
            }
        } while(!(verifyFile));
        reader.close();
        
        int[] salesData = getSalesData(salesFile);
        int[] counts = countNums(salesData, 9);
        double[] frequencies = getFrequencies(counts); // Array with the percentage/frequency of every digit
        reportFraud(frequencies);
        CategoryDataset dataset = arrToDataset(counts, "Counts");
        createChart("First Digits in Sales Data", "First Digit", "Count", dataset);
        printArray(counts);
        printResults("results.csv", frequencies); // Creating the results file
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
     * This method only works if arr doesn't have any negative or 0 values and arrSize is equal to the largest
     * possible value in arr
     * 
     * @param arr       The array in which to count values
     * @param arrSize   The desired size of the output array (this is also the maximum value in arr)
     * @return counts   The count of each value that appears in the array
     */
    public static int[] countNums (int[] arr, int arrSize) {
        int[] counts = new int[arrSize];
        
        for (int num : arr) {
            counts[num-1] += 1;
        }

        return counts;
    }

    /**
     * This method checks if the first digit is a one from 29 to 32 percent of the time
     * 
     * If it does occur from 29 to 32 percent of the time fraud likely didn't occur, otherwise fraud may have occurred
     * 
     * @param arr   The array with the percentage of times each digit occurred
     */
    public static void reportFraud (double[] arr) {
        // The first element in the array is the frequency of the first digit occurring
        // According to Benford's Law, if the frequency is between 29% and 32% then fraud didn't occur
        // All frequencies are expressed as percentages (decimal times 100)
        if (arr[0] >= 29 && arr[0] <= 32) {
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
            System.out.format("%5s", i);        // Prints out the column headers in table format
        }
        System.out.println("\n  =============================================");
        for (int j = 0; j < arr.length; j++) {
            System.out.format("%5s", arr[j]);   // Prints out the frequecies in table format
        }
        System.out.println("\n");
    }

    /**
     * This method takes in the user inputed file and reads it to find the first digit of every sales number. Then it outputs an array with the first digits
     * 
     * @param fileName       The file name and location
     * @return firstDigits   An array containing the first digit of every sale number
     */
    public static int[] getSalesData(String fileName){
        File salesFile = new File(fileName);  
        ArrayList<Integer> firstNums = new ArrayList<Integer>(); // Create an array list because we do not know the number of sales
        try {
            BufferedReader reader = new BufferedReader(new FileReader(salesFile)); // Get the file to read
            String str = reader.readLine(); // Read the first line to disriard the title (Postal Code,Sales)
            while(str != null){ // Loop to read every line
                str = reader.readLine(); // Reading the line
                
                if(str != null){
                    String[] parts = str.split(","); // Seperates the line using the comma
                    char c = parts[1].charAt(0); // Finds the first character of the second part which is the sales number
                    int num = Integer.parseInt("" + c); // Converts the character into a integer
                    firstNums.add(num); // Adds the integer into the array list
                }
                
            }
            reader.close();
        }
        catch(Exception e){ // Incase an error occures, informs the user of the error and quits the program
            System.out.println("An error has occured please try again");
            System.out.println(e);
            System.exit(-1);
        }

        int[] firstDigits = new int[firstNums.size()]; // Creating an array to covert the array list into it

        for(int i = 0; i<firstNums.size(); i++){
            firstDigits[i] = firstNums.get(i); // Converting the array list into the array firstDigits
        }

        return firstDigits; 
    }

    /**
     * This method takes in the results file name and the int array count. Then it outputs the results file with the digit and its frequency
     * 
     * @param fileName    The file results.csv
     * @param frequencies An array containing the frequency of the digits
     */
    public static void printResults(String fileName, double[] frequencies){
        try{
            FileWriter myWriter = new FileWriter(fileName); 
            BufferedWriter bw = new BufferedWriter(myWriter);
            PrintWriter pw = new PrintWriter(bw);
            pw.println("Digit,Frequency"); // Prints the title into the file
            DecimalFormat df = new DecimalFormat("#.#"); // Rounds the frequencies to the tenth decimal place for better display
            for(int i=1; i<frequencies.length+1; i++){ 
                pw.println(i +","+ df.format(frequencies[i-1]) + "%"); // Prints every digit and its frequency
            }
            pw.flush();
            pw.close();
            System.out.println ("Successfully wrote in the file"); // Informs the user of successful completion
        }
        catch (Exception e) { // If an error occurs
            System.out.println("An error has occured please try again");
            System.out.println(e);
        }
    }
    
    /**
     * This method takes in the counts array and converts it into frequencies for each digit
     * 
     * @param counts       The array containing the count of each first digit
     * @return frequencies An array containing the frequency of the digits
     */
    public static double[] getFrequencies(int[] counts){
        // Declairing variables
        double sum = 0.0;
        double[] frequencies = new double[counts.length];
        double freqTemp = 0.0;

        for (int num: counts){
            sum += num; // Finding the sum of all the counts
        }

        for(int i = 0; i<counts.length; i++){
            freqTemp = (counts[i]*100)/sum; // Finding the percentage of each count
            frequencies[i] = freqTemp; // Placing the frequency into the frequencies array
        }

        return frequencies;

    }
}