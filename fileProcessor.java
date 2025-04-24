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

    // Method to generate frequency table using maps
    Map<String, Map<String, Integer>> getFrequencyTable()
    {
        Map<String, Map<String, Integer>> frequencyTable = new HashMap<>();
        String[] headers = {"Location", "TimeOfDay", "Weather", "MotionDetected", "SensorIsTriggered"};
        
        try 
        {
            myScanner = new Scanner(fileExample);
            
            // Skip the header line
            if (myScanner.hasNextLine()) 
            {
                myScanner.nextLine();
            }

            // Read each line
            while (myScanner.hasNextLine()) 
            {
                String line = myScanner.nextLine();
                String[] values = line.split(",");

                // Check if the row has the right number of columns
                if (values.length == headers.length) 
                {
                    // Make a key from the first 4 columns
                    String featureKey = values[0].trim() + "," + 
                                       values[1].trim() + "," + 
                                       values[2].trim() + "," + 
                                       values[3].trim();
                    String label = values[4].trim(); // Yes or No

                    // If we haven't seen this feature combination before, make a new map for it
                    if (!frequencyTable.containsKey(featureKey))
                    {
                        frequencyTable.put(featureKey, new HashMap<>());
                    }

                    // Get the map for this feature combination
                    Map<String, Integer> labelCounts = frequencyTable.get(featureKey);
                    
                    // Add 1 to the count for this label
                    if (labelCounts.containsKey(label))
                    {
                        int currentCount = labelCounts.get(label);
                        labelCounts.put(label, currentCount + 1);
                    }
                    else
                    {
                        labelCounts.put(label, 1);
                    }
                }
            }
            myScanner.close();
        } 
        catch (FileNotFoundException e) 
        {
            System.out.println("run time error " + e.getMessage());
        }

        return frequencyTable;
    }

    // Method to format the frequency table as a string for presenting
    public String formatFrequencyTable(Map<String, Map<String, Integer>> table)
    {
        String result = "";
        
        // Print the header
        result = result + "         Features                 Labels\n";
        result = result + "F1      F2      F3      F4      Yes     No\n";

        // Print each row
        for (String featureKey : table.keySet())
        {
            String[] features = featureKey.split(",");
            Map<String, Integer> labelCounts = table.get(featureKey);

            // Get the Yes and No counts
            int yesCount = 0;
            if (labelCounts.containsKey("Yes"))
            {
                yesCount = labelCounts.get("Yes");
            }
            int noCount = 0;
            if (labelCounts.containsKey("No"))
            {
                noCount = labelCounts.get("No");
            }

            // Build the row with spaces for alignment
            result = result + features[0] + "      " + 
                              features[1] + "      " + 
                              features[2] + "      " + 
                              features[3] + "      " + 
                              yesCount + "      " + 
                              noCount + "\n";
        }

        return result;
    }
}