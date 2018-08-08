package format.xml;

import util.Configure;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class XmlFormat {

    public void toXml(XDepObject xDepObject)  {
        Configure configure = Configure.getConfigureInstance();

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(XDepObject.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(xDepObject, new FileOutputStream(configure.getOutputXmlFile()));
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
