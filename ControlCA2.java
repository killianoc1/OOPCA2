/*
 * Author: Killian O Connell
 * Date: 22/4/2025
 * Programme Description: Control Class for programme
 */

import java.util.Map;
import javax.swing.SwingUtilities;

public class ControlCA2 
{
    public static void main(String[] args)
    {
        fileProcessor processor = new fileProcessor();
        processor.connectToFile();

        // Part 2: Read and print the frequency table
        Map<String, Map<String, Integer>> frequencyTable = processor.getFrequencyTable();
        
        // Print the frequency table
        System.out.println("\nFrequency Table for sensorTriggered_data.csv:\n");
        for (String column : frequencyTable.keySet()) 
        {
            System.out.println(column + ":");
            Map<String, Integer> frequencies = frequencyTable.get(column);
            for (Map.Entry<String, Integer> entry : frequencies.entrySet()) 
            {
                System.out.printf("  %-15s : %d%n", entry.getKey(), entry.getValue());
            }
            System.out.println();
        }

        SwingUtilities.invokeLater(() -> new SensorPredictorGUI().setVisible(true));
    }
}
