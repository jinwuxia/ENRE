package writer;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import util.Configure;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JsonWriter {

    public void toJson(Object depObject, String fileName) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonText = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(depObject);
            //
            jsonText = jsonText.replace("schemaVersion", "@schemaVersion");
            //mapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileName), jsonText);
            FileWriter outputfile = new FileWriter(fileName);
            outputfile.write(jsonText);
            outputfile.close();


            //String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(depObject);
            //System.out.println(jsonString);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
