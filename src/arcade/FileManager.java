package arcade;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * FileManager.java
 */
public class FileManager
{
    public static final String DATA_FOLDER = "data/";

    public static ArrayList<String> readLines(String fileName)
    {
        ArrayList<String> lines = new ArrayList<String>();
        File file = new File(DATA_FOLDER + fileName);

        try
        {
            if(!file.exists())
            {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            Scanner input = new Scanner(file);

            while(input.hasNextLine())
            {
                String line = input.nextLine();

                if(!line.trim().isEmpty())
                {
                    lines.add(line);
                }
            }

            input.close();
        }
        catch(Exception e)
        {
            System.out.println("File read error: " + fileName);
        }

        return lines;
    }

    public static void writeLines(String fileName, ArrayList<String> lines)
    {
        try
        {
            File file = new File(DATA_FOLDER + fileName);
            file.getParentFile().mkdirs();

            PrintWriter output = new PrintWriter(file);

            for(String line : lines)
            {
                output.println(line);
            }

            output.close();
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File write error: " + fileName);
        }
    }
}
