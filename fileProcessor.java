/*
* Author: Killian O Connell
* Date: 20/3/2025
* Description: Reading and printing to a csv file with sensor_data
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
    
    // Getter and Setter for fileName
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    // Getter and Setter for fileExample
    public File getFileExample() {
        return fileExample;
    }

    public void setFileExample(File fileExample) {
        this.fileExample = fileExample;
    }

    // Getter and Setter for myScanner
    public Scanner getMyScanner() {
        return myScanner;
    }

    public void setMyScanner(Scanner myScanner) {
        this.myScanner = myScanner;
    }

    // Getter and Setter for pwInput
    public PrintWriter getPwInput() {
        return pwInput;
    }

    public void setPwInput(PrintWriter pwInput) {
        this.pwInput = pwInput;
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

    // Part 2: Read File
    ArrayList<String> readFile()
    {
        ArrayList<String> values = new ArrayList<>();
        
        try
        {
            myScanner = new Scanner(fileExample); 

            while (myScanner.hasNextLine())
            {
                values.add(myScanner.nextLine());
            }
            myScanner.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("run time error " + e.getMessage());
        }
        finally
        {
            return values;
        }
    }
    
    // Part 3: Get File Writer
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

    // Close Read File
    void closeReadFile()
    {
        myScanner.close();
    }

    // Close Write File
    void closeWriteFile()
    {
        pwInput.close();
    }
}
