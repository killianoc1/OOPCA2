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
        
        // Initial training
        //trainClassifier()??
    }
    
}