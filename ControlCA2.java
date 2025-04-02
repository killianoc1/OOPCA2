// SensorIsTriggered

import java.util.ArrayList;

public class ControlCA2 
{
    public static void main(String[] args)
    {
        fileProcessor processor = new fileProcessor();
        processor.connectToFile();

        // Part 2
        ArrayList<String> rows = processor.readFile();
        for (String row : rows)
        {
            System.out.println(row);
        }

        // Part 3
        // processor.getFileWriter();  
        // processor.writeLineToFile("Test1,Test2,Test3,Test4");
        // processor.writeLineToFile("Demo1,Demo2,Demo3,Demo4");  
        // processor.closeWriteFile(); 
    }
}
