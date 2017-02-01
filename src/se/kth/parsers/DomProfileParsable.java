package se.kth.parsers;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import se.kth.ns.jobservicecompany.ObjectFactory;
import se.kth.ns.jobservicecompany.Profile;
import se.kth.ns.jobservicecompany.Transcript;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;

/**
 * Created by victoraxelsson on 2017-01-26.
 */
public class DomProfileParsable extends Parser {

    private DocumentBuilderFactory factory;


    public DomProfileParsable(){
        factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        factory.setNamespaceAware(true);
        factory.setIgnoringElementContentWhitespace(true);
        factory.setAttribute( "http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");
        factory.setAttribute( "http://java.sun.com/xml/jaxp/properties/schemaSource", schemas.getAbsolutePath() + "/companyInfo.xsd");
    }

    private Profile fillCompanyInfo(Profile profile) throws ParserConfigurationException, IOException, SAXException {
        Document document = null;
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.parse(new File(instances.getAbsolutePath() + "/jayronis.xml"));
        builder.setErrorHandler(new ErrorHandler() {
            @Override
            public void warning(SAXParseException exception) throws SAXException {

            }

            @Override
            public void error(SAXParseException exception) throws SAXException {

            }

            @Override
            public void fatalError(SAXParseException exception) throws SAXException {

            }
        });
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

        Element e = (Element)child;
        String officeName = e.getAttribute("officeName");

        child = child.getFirstChild();

        //Parse courses
        String lat = child.getTextContent();

        child = child.getNextSibling();
        String lng = child.getTextContent();

        Profile.Position.Office office = new Profile.Position.Office();
        office.setLat(new BigDecimal(lat));
        office.setLng(new BigDecimal(lng));
        office.setOfficeName(officeName);

        for(int i = 0; i < profile.getPosition().size(); i++){
            profile.getPosition().get(i).setOffice(office);
        }


        return profile;
    }

    private Profile fillCv(Profile profile) throws ParserConfigurationException, IOException, SAXException {
        Document document = null;
        DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setErrorHandler(new ErrorHandler() {
            @Override
            public void warning(SAXParseException exception) throws SAXException {

            }

            @Override
            public void error(SAXParseException exception) throws SAXException {

            }

            @Override
            public void fatalError(SAXParseException exception) throws SAXException {

            }
        });
        document = builder.parse(new File(instances.getAbsolutePath() + "/cv.xml"));
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
        profile.setFirstName(firstname);
        profile.setLastName(lastname);
        project.setName(projectName);
        project.setFinishDate(XMLGregorianCalendarImpl.parse(finishDate));
        project.setStartDate(XMLGregorianCalendarImpl.parse(startDate));
        profile.getProject().add(project);


        return profile;
    }

    private Profile fillEmployeeRecord(Profile profile) throws ParserConfigurationException, IOException, SAXException {
        Document document = null;
        DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setErrorHandler(new ErrorHandler() {
            @Override
            public void warning(SAXParseException exception) throws SAXException {

            }

            @Override
            public void error(SAXParseException exception) throws SAXException {

            }

            @Override
            public void fatalError(SAXParseException exception) throws SAXException {

            }
        });
        document = builder.parse(new File(instances.getAbsolutePath() + "/employmentRecord.xml"));
        Node root = document.getFirstChild();

        Node child = root.getFirstChild();
        String firstname = child.getTextContent();
        child = child.getNextSibling();

        String lastname = child.getTextContent();
        child = child.getNextSibling().getFirstChild();

        String companyName = child.getTextContent();
        child = child.getNextSibling();

        String role = child.getTextContent();
        child = child.getNextSibling();

        String responsibilities = child.getTextContent();
        child = child.getNextSibling();

        String startDate = child.getTextContent();
        child = child.getNextSibling();

        String finishDate = child.getTextContent();

        Profile.Position position = new Profile.Position();
        position.setCompanyName(companyName);
        position.setResponsibilities(responsibilities);
        position.setStartDate(XMLGregorianCalendarImpl.parse(startDate));
        position.setFinishDate(XMLGregorianCalendarImpl.parse(finishDate));
        position.setRole(role);


        profile.getPosition().add(position);

        return profile;
    }

    private Profile fillTranscript(Profile profile) throws ParserConfigurationException, IOException, SAXException {
        Document document = null;
        DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setErrorHandler(new ErrorHandler() {
            @Override
            public void warning(SAXParseException exception) throws SAXException {

            }

            @Override
            public void error(SAXParseException exception) throws SAXException {

            }

            @Override
            public void fatalError(SAXParseException exception) throws SAXException {

            }
        });
        document = builder.parse(new File(instances.getAbsolutePath() + "/transcript.xml"));
        Node root = document.getFirstChild();


        Node child = root.getFirstChild();
        String firstname = child.getTextContent();
        child = child.getNextSibling();

        String lastname = child.getTextContent();
        child = child.getNextSibling().getFirstChild();

        String degree = child.getTextContent();
        child = child.getNextSibling();

        Node courseIterator = child;

        //Parse courses
        Profile.University profUni = new Profile.University();
        BigDecimal gpa = BigDecimal.ZERO;
        int courseCount = 0;
        while (child.getLocalName().equals("course")){
            Profile.University.Course profCourse = new Profile.University.Course();
            Node courseChild = child.getFirstChild();
            String name = courseChild.getTextContent();

            courseChild = courseChild.getNextSibling();
            String grade = courseChild.getTextContent();

            profCourse.setGrade(new BigDecimal(grade));
            profCourse.setName(name);
            profUni.getCourse().add(profCourse);
            courseCount++;
            gpa = gpa.add(new BigDecimal(grade)).divide(BigDecimal.valueOf(courseCount * 1.0));

            courseIterator = courseIterator.getNextSibling();
            child = child.getNextSibling();
        }

        String startTime = child.getTextContent();
        child = child.getNextSibling();

        String stopTime = child.getTextContent();

        profUni.setStartDate(XMLGregorianCalendarImpl.parse(startTime));
        profUni.setFinishDate(XMLGregorianCalendarImpl.parse(stopTime));
        profUni.setDegree(degree);
        profUni.setGPA(gpa);
        profile.setUniversity(profUni);

        return profile;
    }

    @Override
    public Profile parse() {
        ObjectFactory objFactory = new ObjectFactory();
        Profile profile = objFactory.createProfile();

        try {
            profile = fillEmployeeRecord(profile);
            profile = fillCompanyInfo(profile);
            profile = fillCv(profile);
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
