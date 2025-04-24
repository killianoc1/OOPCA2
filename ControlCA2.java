/*
 * Author: Killian O Connell
 * Date: 25/04/2025
 * Programme Description: Control Class for this file, launches GUI
 */

public class ControlCA2 
{
    public static void main(String[] args) 
    {
        // Print a message to show the program is starting
        System.out.println("Starting Sensor Predictor GUI Application...");

        // Create a new instance of the SensorPredictorGUI
        SensorPredictorGUI gui = new SensorPredictorGUI();
        
        // Make the GUI visible to the user
        gui.setVisible(true);
    }
}