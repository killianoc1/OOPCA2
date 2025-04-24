/*
 * Author: Killian O Connell
 * Date: 22/4/2025
 * Programme Description: Initial GUI without predictor
*/


import java.awt.*;
import java.util.Map;
import javax.swing.*;

public class SensorPredictorGUI extends JFrame 
{
    private fileProcessor processor;
    private Map<String, Map<String, Integer>> frequencyTable;
    
    // GUI Components
    private JComboBox<String> locationCombo;
    private JComboBox<String> timeOfDayCombo;
    private JComboBox<String> weatherCombo;
    private JComboBox<String> motionDetectedCombo;
    private JTextArea outputArea;
    private JButton predictButton;
    private JButton trainButton;
    private JButton addDataButton;
    private JComboBox<String> sensorTriggeredCombo;

    public SensorPredictorGUI() 
    {
        processor = new fileProcessor();
        processor.connectToFile();
        
        // Set up the frame
        setTitle("Sensor Trigger Predictor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setSize(600, 600);
        
        // Create input panel
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Initialize combo boxes
        locationCombo = new JComboBox<>(new String[]{"Inside", "Outside"});
        timeOfDayCombo = new JComboBox<>(new String[]{"Morning", "Night"});
        weatherCombo = new JComboBox<>(new String[]{"Sunny", "Rainy"});
        motionDetectedCombo = new JComboBox<>(new String[]{"High", "Low"});
        sensorTriggeredCombo = new JComboBox<>(new String[]{"Yes", "No"});
        
        // Add components to input panel
        inputPanel.add(new JLabel("Location:"));
        inputPanel.add(locationCombo);
        inputPanel.add(new JLabel("Time of Day:"));
        inputPanel.add(timeOfDayCombo);
        inputPanel.add(new JLabel("Weather:"));
        inputPanel.add(weatherCombo);
        inputPanel.add(new JLabel("Motion Detected:"));
        inputPanel.add(motionDetectedCombo);
        inputPanel.add(new JLabel("Sensor Triggered (for adding data):"));
        inputPanel.add(sensorTriggeredCombo);
        
        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        predictButton = new JButton("Predict");
        trainButton = new JButton("Train Classifier");
        addDataButton = new JButton("Add Data");
        buttonPanel.add(predictButton);
        buttonPanel.add(trainButton);
        buttonPanel.add(addDataButton);
        
        // Create output area
        outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        
        // Add components to frame
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // Add action listeners
        trainButton.addActionListener(e -> trainClassifier());
        predictButton.addActionListener(e -> makePrediction());
        addDataButton.addActionListener(e -> addNewData());
        
        // Initial training
        trainClassifier();
    }
    
    private void trainClassifier() 
    {
        // Level 2: Dynamically calculate frequency table
        frequencyTable = processor.getFrequencyTable();
        outputArea.setText("Classifier trained successfully!\n\nFrequency Table:\n" + 
                          processor.formatFrequencyTable(frequencyTable));
    }
    
    private void makePrediction() 
    {
        // Level 1: Predict based on input features
        // Combine selected features into a single key string
        String featureKey = locationCombo.getSelectedItem() + "," +
                          timeOfDayCombo.getSelectedItem() + "," +
                          weatherCombo.getSelectedItem() + "," +
                          motionDetectedCombo.getSelectedItem();
        
        // Validate frequency table and feature combination
        if (frequencyTable == null || !frequencyTable.containsKey(featureKey)) 
        {
            outputArea.setText("No data available for this combination. Please train the classifier or try different features.");
            return;
        }
        
        // Retrieve counts for prediction outcomes
        Map<String, Integer> labelCounts = frequencyTable.get(featureKey);
        int yesCount = 0;
        int noCount = 0;
        
        // Get count for "Yes" outcome if it exists
        if (labelCounts.containsKey("Yes")) 
        {
            yesCount = labelCounts.get("Yes");
        }
        
        // Get count for "No" outcome if it exists
        if (labelCounts.containsKey("No")) 
        {
            noCount = labelCounts.get("No");
        }
        
        // Determine prediction based on higher count
        String prediction;
        if (yesCount >= noCount) 
        {
            prediction = "Yes";
        } 
        else 
        {
            prediction = "No";
        }
        
        // Calculate probability of the predicted outcome
        double probability;
        int totalCount = yesCount + noCount;
        if (totalCount == 0) 
        {
            probability = 0.0; // Avoid division by zero
        } 
        else if (yesCount >= noCount) 
        {
            probability = ((double) yesCount / totalCount) * 100; // Convert to percentage
        } 
        else 
        {
            probability = ((double) noCount / totalCount) * 100; // Convert to percentage
        }
        
        // Format probability to two decimal places
        String probabilityString = String.format("%.2f", probability);
        
        // Display prediction results
        outputArea.setText("Prediction for " + featureKey + ":\n" +
                          "Sensor Triggered: " + prediction + "\n" +
                          "Probability: " + probabilityString + "%\n" +
                          "Yes Count: " + yesCount + "\n" +
                          "No Count: " + noCount);
    }
    
    private void addNewData() 
    {
        // Level 3: Add new row to dataset and retrain
        String newRow = locationCombo.getSelectedItem() + "," +
                       timeOfDayCombo.getSelectedItem() + "," +
                       weatherCombo.getSelectedItem() + "," +
                       motionDetectedCombo.getSelectedItem() + "," +
                       sensorTriggeredCombo.getSelectedItem();
        
        try 
        {
            processor.openFile();
            processor.getFileWriter();
            processor.writeLineToFile(newRow);
            processor.closeWriteFile();
            
            // Retrain classifier with new data
            trainClassifier();
            outputArea.append("\n\nNew data added successfully: " + newRow);
        } 
        catch (Exception e) 
        {
            outputArea.setText("Error adding data: " + e.getMessage());
        }
    }
}