package Java7Andbefore.IO;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileReadDemo {
    public static void main(String[] args) throws IOException
    {
        String fileName2 = "classpath:banner.txt";
        String fileName1 = "C:\\Users\\Manisha\\Documents\\GitHub\\02-Backend-API-Java17\\src\\main\\resources\\banner.txt";

        //==============================
        // READ
        //==============================
        // Way-1 NIO : File, Path
        try (Stream<String> lines = Files.lines(Paths.get(fileName1)))
        {
            lines.forEach(System.out::println);
        } catch (IOException  e) {
            e.printStackTrace();
        }

        // Way-2 IO :  streams >> reader
        // [InputStream ] --> [ InputStreamReader > BufferedReader ]
        // SB : resource Loader: to load from resource folder.
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource(fileName2);

        try (InputStream inputStream1 = new FileInputStream(fileName1);
             InputStream inputStream2 = resource.getInputStream())
        {
            // inputStream2
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream2, StandardCharsets.UTF_8);
            BufferedReader bufferReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferReader.readLine()) != null)
                System.out.println(line);

            //inputStream1 : same code
        } catch (IOException  e) {
            e.printStackTrace();
        }
    }
}
