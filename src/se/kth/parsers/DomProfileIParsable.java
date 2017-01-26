package se.kth.parsers;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import se.kth.ns.jobservicecompany.Company;
import se.kth.ns.jobservicecompany.ObjectFactory;
import se.kth.ns.jobservicecompany.Profile;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by victoraxelsson on 2017-01-26.
 */
public class DomProfileIParsable extends Parser implements IParsable {

    private String basePath = "/Users/victoraxelsson/Desktop/web_services/asignment1/";

    @Override
    public Profile parse(String username) {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        factory.setNamespaceAware(true);
        factory.setIgnoringElementContentWhitespace(true);
        factory.setAttribute( "http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");
        factory.setAttribute( "http://java.sun.com/xml/jaxp/properties/schemaSource", basePath + "/schemas/companyInfo.xsd");


        ObjectFactory objFactory = new ObjectFactory();
        Profile profile = objFactory.createProfile();

        Document document = null;
        try {

            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(new File(basePath + "instances/jayronis.xml"));
            Node root = document.getFirstChild();
            /*
            String companyName = node.getFirstChild().getTextContent();
            node = node.getNextSibling();
            */
            Node child = root.getFirstChild();
            String companyName = child.getTextContent();
            child = child.getNextSibling();
            String website = child.getTextContent();
            child = child.getNextSibling();
            String numberOfempployees = child.getTextContent();
            child = child.getNextSibling();

            /*
            String lat = child.getTextContent();
            child = child.getNextSibling();
            String lng = child.getTextContent();
            child = child.getNextSibling();
            */


            /*
            for (Node child = root.getFirstChild(); child != null; child = child.getNextSibling()) {


            }
                */

             // fill the profiler object form the document reader

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Do shiet

        return profile;

        /*
        List<Company> companies = getAllCompanies();


        ObjectFactory objFactory = new ObjectFactory();
        Company company = objFactory.createCompany();

        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        //Schema schema = sf.newSchema(new StreamSource(new File("/Users/victoraxelsson/Desktop/web_services/asignment1/schemas/profile.xsd")));
        */
    }
}
