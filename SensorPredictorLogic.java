/*
 * Author: Killian O Connell
 * Date: 25/04/2025
 * Programme Description: Defines methods and logic behind trainClassifier, makePrediction and addNewData
 */

import java.util.Map;

public class SensorPredictorLogic 
{
    private fileProcessor processor;
    private Map<String, Map<String, Integer>> frequencyTable;

    public SensorPredictorLogic() 
    {
        processor = new fileProcessor();
        processor.connectToFile();
    }
    
    public String trainClassifier() 
    {
        frequencyTable = processor.getFrequencyTable();
        return "Classifier trained successfully!\n\nFrequency Table:\n" + 
               processor.formatFrequencyTable(frequencyTable);
    }
    
    // I make a prediction based on a given prediction input
    public String makePrediction(String predictionInput) 
    {
        // Error checking
        if (frequencyTable == null || !frequencyTable.containsKey(predictionInput)) 
        {
            return "No data available for this combination. Please train the classifier or try different features.";
        }
        
        // I retrieve the counts of "Yes" and "No" labels for the given input
        Map<String, Integer> labelCounts = frequencyTable.get(predictionInput);

        // I get the count of "Yes" and "No" labels, defaulting to 0 if not found
        int yesCount = labelCounts.getOrDefault("Yes", 0);
        int noCount = labelCounts.getOrDefault("No", 0);
        
        // I predict "Yes" if yesCount is greater than or equal to noCount, otherwise "No"
        String prediction;
        if (yesCount >= noCount) 
        {
            // If yesCount is greater than or equal to noCount, predict "Yes"
            prediction = "Yes";
        } 
        else 
        {
            // If noCount is higher, predict "No"
            prediction = "No";
        }


        // Defining probability and totalCount
        double probability;
        int totalCount = yesCount + noCount;

        // I check if there are no counts
        if (totalCount == 0) 
        {
            probability = 0.0;
        } 
        else 
        {
            // Calculate the probability as a percentage based on the more frequent outcome
            int higherCount;
            if (yesCount >= noCount) 
            {
                higherCount = yesCount;
            } 
            else 
            {
                higherCount = noCount;
            }
            // Convert to percentage by dividing by totalCount and multiplying by 100
            probability = ((double) higherCount / totalCount) * 100;
        }
        
        // I format the probability to two decimal places
        String probabilityString = String.format("%.2f", probability);

        // I return a formatted string with the prediction, probability, and counts
        return "Prediction for " + predictionInput + ":\n" +
            "Sensor Triggered: " + prediction + "\n" +
            "Probability: " + probabilityString + "%\n" +
            "Yes Count: " + yesCount + "\n" +
            "No Count: " + noCount;
    }
    
    public String addNewData(String newRow) 
    {
        try 
        {
            processor.openFile();
            processor.getFileWriter();
            processor.writeLineToFile(newRow);
            processor.closeWriteFile();
            
            // Retrain classifier with new data
            String trainingResult = trainClassifier();
            return trainingResult + "\n\nNew data added successfully: " + newRow;
        } 
        catch (Exception e) 
        {
            return "Error adding data: " + e.getMessage();
        }
    }
}