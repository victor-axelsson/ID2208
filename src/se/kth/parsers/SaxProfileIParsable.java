package se.kth.parsers;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import se.kth.ns.jobservicecompany.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Paths;

/**
 * Created by victoraxelsson on 2017-01-26.
 */
public class SaxProfileIParsable {

    Profile profile;
    Company company;
    EmploymentRecord employmentRecord;
    Transcript transcript;
    Cv cv;

    @Override
    public Profile parse(String username) {

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = null;
        try {
            saxParser = factory.newSAXParser();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        File instances = Paths.get(".", "instances").normalize().toFile();
        try {
            saxParser.parse(new File(instances.getAbsolutePath() + "/company.xml"), new CompanyHandler());
            saxParser.parse(new File(instances.getAbsolutePath() + "/cv.xml"), new CvHandler());
            saxParser.parse(new File(instances.getAbsolutePath() + "/employmentRecord.xml"), new EmploymentHandler());
            saxParser.parse(new File(instances.getAbsolutePath() + "/transcript.xml"), new TranscriptHandler());
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        profile = new Profile();
        profile.setFirstName(cv.getFirstName());
        profile.setLastName(cv.getLastName());
        mergeTranscript(profile, transcript);
        mergeCv(profile, cv);
        mergeEmployment(profile, employmentRecord, company);
        //System.out.println(company);
        return profile;
    }

    private void mergeTranscript(Profile profile, Transcript transcript) {
        Transcript.University university = transcript.getUniversity();
        Profile.University profUni = new Profile.University();
        profUni.setDegree(university.getDegree());
        profUni.setStartDate(university.getStartDate());
        profUni.setFinishDate(university.getFinishDate());
        for (Transcript.University.Course course : university.getCourse()) {
            Profile.University.Course profCourse = new Profile.University.Course();
            profCourse.setGrade(course.getGrade());
            profCourse.setName(course.getName());
            profUni.getCourse().add(profCourse);
        }
        profile.setUniversity(profUni);
    }

    private void mergeCv(Profile profile, Cv cv) {
        for (Cv.Project cvProj : cv.getProject()) {
            Profile.Project project = new Profile.Project();
            project.setName(cvProj.getName());
            project.setStartDate(cvProj.getStartDate());
            project.setFinishDate(cvProj.getFinishDate());
            profile.getProject().add(project);
        }
    }

    private void mergeEmployment(Profile profile, EmploymentRecord employmentRecord, Company company) {
        for (EmploymentRecord.Position empPos : employmentRecord.getPosition()) {
            Profile.Position position = new Profile.Position();
            position.setStartDate(empPos.getStartDate());
            position.setFinishDate(empPos.getFinishDate());
            position.setCompanyName(empPos.getCompany());
            position.setResponsibilities(empPos.getResponsibilities());
            position.setRole(empPos.getRole());
            Profile.Position.Office office = new Profile.Position.Office();
            office.setOfficeName(company.getOffice().getOfficeName());
            office.setLat(company.getOffice().getLat());
            office.setLng(company.getOffice().getLng());
            position.setOffice(office);
            profile.getPosition().add(position);
        }
    }

    class CompanyHandler extends DefaultHandler {
        private boolean companyName;
        private boolean website;
        private boolean numberOfEmployees;
        private boolean office;
        private boolean officeLat;
        private boolean officeLng;

        @Override
        public void startDocument() throws SAXException {
            company = new Company();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            switch (qName) {
                case "companyName": companyName = true; break;
                case "website": website = true; break;
                case "numberOfEmployees": numberOfEmployees = true; break;
                case "office":
                    Company.Office officeObj = new Company.Office();
                    officeObj.setOfficeName(attributes.getValue("officeName"));
                    company.setOffice(officeObj);
                    break;
                case "lat": officeLat = true; break;
                case "lng": officeLng = true; break;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            String content = new String(ch, start, length);
            if (companyName) {
                company.setCompanyName(content);
                companyName = false;
            }
            if (website) {
                company.setWebsite(content);
                website = false;
            }
            if (numberOfEmployees) {
                company.setNumberOfEmployees(new BigInteger(content));
                numberOfEmployees = false;
            }
            if (officeLat) {
                company.getOffice().setLat(new BigDecimal(content));
                officeLat = false;
            }
            if (officeLng) {
                company.getOffice().setLng(new BigDecimal(content));
                officeLng = false;
            }
        }
    }

    class CvHandler extends DefaultHandler {
        private boolean firstName;
        private boolean lastName;
        private boolean project;
        private boolean startDate;
        private boolean finishDate;
        private boolean name;
        private boolean description;

        Cv.Project projectObj;

        @Override
        public void startDocument() throws SAXException {
            cv = new Cv();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            switch (qName) {
                case "firstName": firstName = true; break;
                case "lastName": lastName = true; break;
                case "project": project = true; break;
                case "startDate": startDate = true; break;
                case "finishDate": finishDate = true; break;
                case "name": name = true; break;
                case "description": description = true; break;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (project && (projectObj != null) && (projectObj.getStartDate() != null) &&
                    (projectObj.getFinishDate() != null) && (projectObj.getName() != null) && (projectObj.getDescription() != null)) {
                cv.getProject().add(projectObj);
                project = false;
                projectObj = null;
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            String content = new String(ch, start, length);
            if (firstName) {
                cv.setFirstName(content);
                firstName = false;
            }
            if (lastName) {
                cv.setLastName(content);
                lastName = false;
            }
            if (project) {
                if (projectObj == null) projectObj = new Cv.Project();
            }

            if (startDate) {
                projectObj.setStartDate(XMLGregorianCalendarImpl.parse(content));
                startDate = false;
            }
            if (finishDate) {
                projectObj.setFinishDate(XMLGregorianCalendarImpl.parse(content));
                finishDate = false;
            }
            if (name) {
                projectObj.setName(content);
                name = false;
            }
            if (description) {
                projectObj.setDescription(content);
                description = false;
            }
        }
    }

    class EmploymentHandler extends DefaultHandler {
        private boolean firstName;
        private boolean lastName;
        private boolean position;
        private boolean startDate;
        private boolean finishDate;
        private boolean company;
        private boolean role;
        private boolean responsibilities;

        EmploymentRecord.Position positionObj;

        @Override
        public void startDocument() throws SAXException {
            employmentRecord = new EmploymentRecord();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            switch (qName) {
                case "firstName": firstName = true; break;
                case "lastName": lastName = true; break;
                case "position": position = true; break;
                case "startDate": startDate = true; break;
                case "finishDate": finishDate = true; break;
                case "company": company = true; break;
                case "role": role = true; break;
                case "responsibilities": responsibilities = true; break;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (position && (positionObj != null) && (positionObj.getStartDate() != null) &&
                    (positionObj.getFinishDate() != null) && (positionObj.getCompany() != null) &&
                    (positionObj.getResponsibilities() != null) && (positionObj.getRole() != null)) {
                employmentRecord.getPosition().add(positionObj);
                position = false;
                positionObj = null;
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            String content = new String(ch, start, length);
            if (firstName) {
                employmentRecord.setFirstName(content);
                firstName = false;
            }
            if (lastName) {
                employmentRecord.setLastName(content);
                lastName = false;
            }
            if (position) {
                if (positionObj == null) positionObj = new EmploymentRecord.Position();
            }

            if (startDate) {
                positionObj.setStartDate(XMLGregorianCalendarImpl.parse(content));
                startDate = false;
            }
            if (finishDate) {
                positionObj.setFinishDate(XMLGregorianCalendarImpl.parse(content));
                finishDate = false;
            }
            if (company) {
                positionObj.setCompany(content);
                company = false;
            }
            if (role) {
                positionObj.setRole(content);
                role = false;
            }
            if (responsibilities) {
                positionObj.setResponsibilities(content);
                responsibilities = false;
            }
        }
    }

    class TranscriptHandler extends DefaultHandler {
        private boolean firstName;
        private boolean lastName;
        private boolean university;
        private boolean startDate;
        private boolean finishDate;
        private boolean degree;
        private boolean course;
        private boolean name;
        private boolean grade;

        Transcript.University universityObj;
        Transcript.University.Course courseObj;

        @Override
        public void startDocument() throws SAXException {
            transcript = new Transcript();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            switch (qName) {
                case "firstName": firstName = true; break;
                case "lastName": lastName = true; break;
                case "university": university = true; break;
                case "startDate": startDate = true; break;
                case "finishDate": finishDate = true; break;
                case "degree": degree = true; break;
                case "course": course = true; break;
                case "name": name = true; break;
                case "grade": grade = true; break;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (university && (universityObj != null) && (universityObj.getStartDate() != null) &&
                    (universityObj.getFinishDate() != null) && (universityObj.getDegree() != null) &&
                    (universityObj.getCourse() != null)) {
                transcript.setUniversity(universityObj);
                university = false;
                universityObj = null;
            }

            if (course && (courseObj != null) && (courseObj.getGrade() != null) &&
                    (courseObj.getName() != null)) {
                universityObj.getCourse().add(courseObj);
                course = false;
                courseObj = null;
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            String content = new String(ch, start, length);
            if (firstName) {
                transcript.setFirstName(content);
                firstName = false;
            }
            if (lastName) {
                transcript.setLastName(content);
                lastName = false;
            }
            if (university) {
                if (universityObj == null) universityObj = new Transcript.University();
            }

            if (startDate) {
                universityObj.setStartDate(XMLGregorianCalendarImpl.parse(content));
                startDate = false;
            }
            if (finishDate) {
                universityObj.setFinishDate(XMLGregorianCalendarImpl.parse(content));
                finishDate = false;
            }
            if (degree) {
                universityObj.setDegree(content);
                degree = false;
            }
            if (course) {
                if (courseObj == null) courseObj = new Transcript.University.Course();
            }
            if (name) {
                courseObj.setName(content);
                name = false;
            }
            if (grade) {
                courseObj.setGrade(new BigDecimal(content));
                grade = false;
            }
        }
    }
}
