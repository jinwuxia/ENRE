package multiparser.format.json;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import multiparser.util.Configure;

import java.io.File;
import java.io.IOException;

public class JsonFormat {

    public void toJson(JDepObject depObject) {
        ObjectMapper mapper = new ObjectMapper();
        Configure configure = Configure.getConfigureInstance();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(configure.getOutputJsonFile()), depObject);

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
