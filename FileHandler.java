import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    
    public static List<String> readContent(String fileName, int maxLines){

        List<String> result = new ArrayList<String>();

        try(BufferedReader reader = new BufferedReader
        (new InputStreamReader(new FileInputStream(fileName), "ISO-8859-1"))){
            
            reader.lines()
            .skip(1)
            .limit(maxLines)
            .forEach(l -> result.add(l.replace("\"", "")));

        }catch(Exception e){
            System.err.println(e.getMessage());
        }   

        System.out.println("File " + fileName + " -> Lines read " + result.size());

        return result;

    }

}
