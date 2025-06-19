package Java7Andbefore.IO;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

public class FileWriteDemo {
    public static void main(String[] args) {
        String fileName2 = "classpath:banner.txt";
        String fileName1 = "C:\\Users\\Manisha\\Documents\\GitHub\\02-Backend-API-Java17\\src\\main\\resources\\banner.txt";
        List<String> lines = Arrays.asList("First line", "Second line");

        //==================
        //  WRITE
        //==================
        // OLD - IO
        try (FileWriter fileWriter = new FileWriter(fileName1))
        {
            for (String line : lines)
                fileWriter.write(line + System.lineSeparator());

        } catch (IOException e) {e.printStackTrace(); }

        //NEW - NIO
        try {
            Files.write( Paths.get(fileName1), lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
