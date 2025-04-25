/*
 * Author: Killian O Connell
 * Date: 20/3/2025
 * Description: Reading and printing to a csv file with sensorTriggered_data, including frequency table generation
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class fileProcessor 
{
    private String fileName;
    private File fileExample;
    private Scanner myScanner;
    private PrintWriter pwInput;
    
    // Constructor
    fileProcessor()
    {
        this.fileName = "sensorTriggered_data.csv";
    }
    
    // Method to open the file
    public void openFile() 
    {
        try 
        {
            fileExample = new File(fileName);
            if (!fileExample.exists()) 
            {
                fileExample.createNewFile();
            }
        } 
        catch (IOException e) 
        {
            System.out.println("Error opening file: " + e.getMessage());
        }
    }

    // Method to connect to file
    void connectToFile()
    {
        fileExample = new File(fileName);
    }
    
    // Get File Writer
    void getFileWriter()
    {
        try
        {
            // Using FileWriter with append
            pwInput = new PrintWriter(new FileWriter(fileExample, true));
        }
        catch (IOException e)
        {
            System.out.println("run time error " + e.getMessage());
        }
    }

    // Write Line to File
    void writeLineToFile(String line)
    {
        System.out.println(line);
        pwInput.println(line);    	
    }

    // Close Write File
    void closeWriteFile()
    {
        pwInput.close();
    }

    // Method to generate frequency table
    // Reads a file and counts how often each unique combo of four features leads to "Yes" or "No".
    Map<String, Map<String, Integer>> getFrequencyTable()
    {
        // Initialize the outer HashMap to store feature combinations and their label counts
        Map<String, Map<String, Integer>> frequencyTable = new HashMap<>();
        
        String[] headers = {"Location", "TimeOfDay", "Weather", "MotionDetected", "SensorIsTriggered"};
        
        try 
        {
            // Initialize Scanner
            myScanner = new Scanner(fileExample);
            
            // Skip the header line in the file if it exists
            if (myScanner.hasNextLine()) 
            {
                myScanner.nextLine();
            }

            while (myScanner.hasNextLine()) 
            {
                // Read the current line
                String line = myScanner.nextLine();
                // Split the line into values based on comma delimiter
                String[] values = line.split(",");

                // Verify the row has the correct number of columns
                if (values.length == headers.length) 
                {
                    // Create a feature key by concatenating first 4 columns with commas
                    String featureKey = values[0].trim() + "," + 
                                    values[1].trim() + "," + 
                                    values[2].trim() + "," + 
                                    values[3].trim();
                    // Get the label (Yes/No) from the last column
                    String label = values[4].trim();

                    // If this feature combination is new, create a new inner HashMap
                    if (!frequencyTable.containsKey(featureKey))
                    {
                        frequencyTable.put(featureKey, new HashMap<>());
                    }

                    // Get the inner Map containing label counts for this feature combination
                    Map<String, Integer> labelCounts = frequencyTable.get(featureKey);
                    
                    // Increment the count for the current label
                    if (labelCounts.containsKey(label))
                    {
                        // If label exists, increment its count
                        int currentCount = labelCounts.get(label);
                        labelCounts.put(label, currentCount + 1);
                    }
                    else
                    {
                        // If label is new, initialize its count to 1
                        labelCounts.put(label, 1);
                    }
                }
            }

            // Close the Scanner
            myScanner.close();
        } 
        catch (FileNotFoundException e) 
        {
            // Handle file not found exception and print error message
            System.out.println("run time error " + e.getMessage());
        }

        // Return the completed frequency table
        return frequencyTable;
    }

    // Method to format the frequency table as a string for display
    public String formatFrequencyTable(Map<String, Map<String, Integer>> table)
    {
        // Initialize result string to store formatted output
        String result = "";
        
        // Add header lines to the output
        result = result + "         Features                 Labels\n";
        result = result + "F1      F2      F3      F4      Yes     No\n";

        // Iterate through each feature combination in the table
        for (String featureKey : table.keySet())
        {
            // Split the feature key into individual features
            String[] features = featureKey.split(",");
            
            // Get the label counts for this feature combination
            Map<String, Integer> labelCounts = table.get(featureKey);

            // Initialize counts for Yes and No labels
            int yesCount = 0;
            if (labelCounts.containsKey("Yes"))
            {
                // Get Yes count if it exists
                yesCount = labelCounts.get("Yes");
            }
            int noCount = 0;
            if (labelCounts.containsKey("No"))
            {
                // Get No count if it exists
                noCount = labelCounts.get("No");
            }

            // Build formatted row with aligned columns
            result = result + features[0] + "      " + 
                            features[1] + "      " + 
                            features[2] + "      " + 
                            features[3] + "      " + 
                            yesCount + "      " + 
                            noCount + "\n";
        }

        // Return the complete formatted string
        return result;
    }
}