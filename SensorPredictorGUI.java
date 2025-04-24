/*
 * Author: Killian O Connell
 * Date: 25/04/2025
 * Programme Description: GUI class that defines GUI and calls methods based on request
 */

import java.awt.*;
import javax.swing.*;

public class SensorPredictorGUI extends JFrame 
{
    private SensorPredictorLogic predictor;
    
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
        // Initialize the predictor object
        predictor = new SensorPredictorLogic();
        
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
        // If certain button is clicked, a corresponding method is called
        trainButton.addActionListener(e -> trainClassifier());
        predictButton.addActionListener(e -> makePrediction());
        addDataButton.addActionListener(e -> addNewData());
        
        // Initial training for user to see frequency table
        trainClassifier();
    }
    
    private void trainClassifier() 
    {
        // Call the trainClassifier method from the predictor object to train the model
        String result = predictor.trainClassifier();

        // Update the outputArea with the training result
        outputArea.setText(result);
    }
    
    // Method to generate a prediction based on user-selected features and display the result
    private void makePrediction() 
    {
        // Create a string that combines selected values from combo boxes into a single input
        String predictionInput = locationCombo.getSelectedItem() + "," +
                                timeOfDayCombo.getSelectedItem() + "," +
                                weatherCombo.getSelectedItem() + "," +
                                motionDetectedCombo.getSelectedItem();
        
        // Pass the combined input to the predictor object's makePrediction method
        String result = predictor.makePrediction(predictionInput);
        
        // Display the prediction result in the outputArea
        outputArea.setText(result);
    }
    
    // Method to add new data to the system based on user-selected features
    private void addNewData() 
    {
        // Create a string representing a new data row by combining selected values from combo boxes
        String newRow = locationCombo.getSelectedItem() + "," +
                        timeOfDayCombo.getSelectedItem() + "," +
                        weatherCombo.getSelectedItem() + "," +
                        motionDetectedCombo.getSelectedItem() + "," +
                        sensorTriggeredCombo.getSelectedItem();
        
        // Pass the new data row to the predictor object's addNewData method
        String result = predictor.addNewData(newRow);
        
        // Display the result in the outputArea
        outputArea.setText(result);
    }
}