package se.kth.parsers;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import se.kth.ns.jobservicecompany.*;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.math.BigDecimal;

/**
 * Created by victoraxelsson on 2017-01-28.
 */
public class JAXBparser extends Parser implements IParsable {

    private SchemaFactory sf;

    public JAXBparser(){
        sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    }

    private Schema getSchema(String path) throws SAXException {
       return sf.newSchema(new StreamSource(new File(schemas.getAbsolutePath() + path)));
    }


    private Object getUnmarshalledObject(Class cl, String instanceName, String schemaName) throws JAXBException, SAXException {
        JAXBContext jc = null;
        jc = JAXBContext.newInstance(cl);
        Unmarshaller um = jc.createUnmarshaller();
        um.setSchema(getSchema(schemaName));
        return um.unmarshal(new File(instances.getAbsolutePath() + instanceName));
    }

    private Profile fillCV(Profile profile, Cv cv){

        //Add project
        profile.setFirstName(cv.getFirstName());
        profile.setLastName(cv.getLastName());

        for(int i = 0; i < cv.getProject().size(); i++){
            Cv.Project p =  cv.getProject().get(i);
            Profile.Project project = new Profile.Project();

            project.setName(p.getName());
            project.setFinishDate(p.getFinishDate());
            project.setStartDate(p.getStartDate());

            profile.getProject().add(project);
        }

        return profile;
    }


    private Profile fillEmploymentRecord(Profile profile, EmploymentRecord employmentRecord){

        for(int i = 0; i < employmentRecord.getPosition().size(); i++){

            EmploymentRecord.Position p = employmentRecord.getPosition().get(i);

            Profile.Position position = new Profile.Position();
            position.setCompanyName(p.getCompany());
            position.setResponsibilities(p.getResponsibilities());
            position.setStartDate(p.getStartDate());
            position.setFinishDate(p.getFinishDate());
            position.setRole(p.getRole());

            profile.getPosition().add(position);
        }

        return profile;
    }

    private Profile fillTranscript(Profile profile, Transcript transcript){

        Profile.University profUni = new Profile.University();
        BigDecimal gpa = BigDecimal.ZERO;
        int courseCount = 0;
        for(int i = 0; i < transcript.getUniversity().getCourse().size(); i++){
            Transcript.University.Course c = transcript.getUniversity().getCourse().get(i);
            courseCount++;
            Profile.University.Course profCourse = new Profile.University.Course();
            profCourse.setGrade(c.getGrade());
            gpa = gpa.add(c.getGrade()).divide(BigDecimal.valueOf(courseCount * 1.0));
            profCourse.setName(c.getName());
            profUni.getCourse().add(profCourse);
        }
        profUni.setGPA(gpa);
        profUni.setStartDate(transcript.getUniversity().getStartDate());
        profUni.setFinishDate(transcript.getUniversity().getFinishDate());
        profUni.setDegree(transcript.getUniversity().getDegree());
        profile.setUniversity(profUni);

        return profile;

    }

    private Profile fillCompany(Profile profile, Company company) {
        for(int i = 0; i < profile.getPosition().size(); i++) {
            Profile.Position.Office office = new Profile.Position.Office();
            office.setOfficeName(company.getOffice().getOfficeName());
            office.setLat(company.getOffice().getLat());
            office.setLng(company.getOffice().getLng());
            profile.getPosition().get(i).setOffice(office);
        }
        return profile;
    }

    @Override
    public Profile parse(String username) {

        Profile profile = null;

        try {
            Company company = (Company)getUnmarshalledObject(Company.class, "/company.xml", "/companyInfo.xsd");
            Cv cv = (Cv) getUnmarshalledObject(Cv.class, "/cv.xml", "/cv.xsd");
            EmploymentRecord employmentRecord = (EmploymentRecord) getUnmarshalledObject(EmploymentRecord.class, "/employmentRecord.xml", "/employmentRecord.xsd");
            Transcript transcript = (Transcript) getUnmarshalledObject(Transcript.class, "/transcript.xml", "/transcript.xsd");

            profile = new Profile();
            profile = fillCV(profile, cv);
            profile = fillEmploymentRecord(profile, employmentRecord);
            profile = fillTranscript(profile, transcript);
            profile = fillCompany(profile, company);

        } catch (JAXBException e) {
            e.printStackTrace();
            profile = null;
        } catch (SAXException e) {
            e.printStackTrace();
            profile = null;
        }

        return profile;
    }
}
