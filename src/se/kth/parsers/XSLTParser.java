package se.kth.parsers;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import se.kth.ns.jobservicecompany.Profile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import javax.xml.transform.Transformer;

/**
 * Created by victoraxelsson on 2017-01-28.
 */
public class XSLTParser extends Parser implements IParsable {
    @Override
    public Profile parse(String username) {

        TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(new File(instances.getAbsolutePath() + "/transformation.xsl"));
        Transformer transformer = null;
        try {
            transformer = factory.newTransformer(xslt);
            Source text = new StreamSource();
            transformer.transform(text, new StreamResult(new File(instances.getAbsolutePath() + "/output.xml")));

        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        return null;
    }
}
