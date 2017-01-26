package se.kth.parsers;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import se.kth.ns.jobservicecompany.ObjectFactory;
import se.kth.ns.jobservicecompany.Profile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Created by victoraxelsson on 2017-01-26.
 */
public class DomProfileIParsable extends Parser implements IParsable {

    private String basePath = "/Users/victoraxelsson/Desktop/web_services/asignment1/";
    private DocumentBuilderFactory factory;


    public DomProfileIParsable(){
        factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        factory.setNamespaceAware(true);
        factory.setIgnoringElementContentWhitespace(true);
        factory.setAttribute( "http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");
        factory.setAttribute( "http://java.sun.com/xml/jaxp/properties/schemaSource", basePath + "/schemas/companyInfo.xsd");
    }

    private Profile fillCompanyInfo(Profile profile) throws ParserConfigurationException, IOException, SAXException {
        Document document = null;
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

        Profile.Position position = new Profile.Position();
        position.setCompanyName(companyName);
        profile.getPosition().add(position);

        return profile;
    }

    private Profile fillCv(Profile profile) throws ParserConfigurationException, IOException, SAXException {
        Document document = null;
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.parse(new File(basePath + "instances/cv.xml"));
        Node root = document.getFirstChild();

        Node child = root.getFirstChild();
        String firstname = child.getTextContent();
        child = child.getNextSibling();

        String lastname = child.getTextContent();
        child = child.getNextSibling().getFirstChild();

        String startDate = child.getTextContent();
        child = child.getNextSibling();

        String finishDate = child.getTextContent();
        child = child.getNextSibling();

        String projectName = child.getTextContent();
        child = child.getNextSibling();

        String projectDescription = child.getTextContent();
        child = child.getNextSibling();

        //Add project
        Profile.Project project = new Profile.Project();
        project.setName(projectName);
        project.setFinishDate(XMLGregorianCalendarImpl.parse(finishDate));
        project.setStartDate(XMLGregorianCalendarImpl.parse(startDate));
        profile.getProject().add(project);


        return profile;
    }

    private Profile fillEmployeeRecord(Profile profile) throws ParserConfigurationException, IOException, SAXException {
        Document document = null;
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.parse(new File(basePath + "instances/employeeRecord.xml"));
        Node root = document.getFirstChild();

        Node child = root.getFirstChild();
        String companyName = child.getTextContent();

        Profile.Position position = new Profile.Position();
        position.setCompanyName(companyName);
        profile.getPosition().add(position);

        return profile;
    }

    private Profile fillTranscript(Profile profile) throws ParserConfigurationException, IOException, SAXException {
        Document document = null;
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.parse(new File(basePath + "instances/transcript.xml"));
        Node root = document.getFirstChild();

        Node child = root.getFirstChild();
        String companyName = child.getTextContent();

        Profile.Position position = new Profile.Position();
        position.setCompanyName(companyName);
        profile.getPosition().add(position);

        return profile;
    }

    @Override
    public Profile parse(String username) {
        ObjectFactory objFactory = new ObjectFactory();
        Profile profile = objFactory.createProfile();

        try {
            profile = fillCompanyInfo(profile);
            profile = fillCv(profile);
            profile = fillEmployeeRecord(profile);
            profile = fillTranscript(profile);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return profile;
    }
}
