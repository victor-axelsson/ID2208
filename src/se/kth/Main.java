package se.kth;

import se.kth.factories.CompanyFactory;
import se.kth.factories.EmploymentFactory;
import se.kth.factories.TranscriptFactory;
import se.kth.ns.jobservicecompany.*;
import se.kth.factories.CvFactory;
import se.kth.parsers.*;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.nio.file.Paths;

/**
 * Created by victoraxelsson on 2017-01-25.
 */
public class Main {

    private static void generateCompanies() throws Exception{
        File schemas = Paths.get(".", "schemas").normalize().toFile();
        File instances = Paths.get(".", "instances").normalize().toFile();

        CompanyFactory companyFactory = new CompanyFactory();
        ObjectFactory objFactory = new ObjectFactory();
        Company company = objFactory.createCompany();
        company = companyFactory.fillCompanyWithCrapData(company);

        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(new StreamSource(new File(schemas.getAbsolutePath() + "/companyInfo.xsd")));

        JAXBContext jc = JAXBContext.newInstance(Company.class);
        Marshaller m = jc.createMarshaller();
        m.setSchema(schema);
        m.marshal(company, new File(instances.getAbsolutePath()+"/company.xml"));
    }

    private static void generateEmployments() throws Exception{
        File schemas = Paths.get(".", "schemas").normalize().toFile();
        File instances = Paths.get(".", "instances").normalize().toFile();

        EmploymentFactory employmentFactory = new EmploymentFactory();
        ObjectFactory objFactory = new ObjectFactory();
        EmploymentRecord record = objFactory.createEmploymentRecord();
        record = employmentFactory.fillWithCrapData(record);


        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(new StreamSource(new File(schemas.getAbsolutePath() + "/employmentRecord.xsd")));

        JAXBContext jc = JAXBContext.newInstance(EmploymentRecord.class);
        Marshaller m = jc.createMarshaller();
        m.setSchema(schema);
        m.marshal(record, new File(instances.getAbsolutePath()+"/employmentRecord.xml"));
    }

    private static void generateCvs() throws Exception{
        File schemas = Paths.get(".", "schemas").normalize().toFile();
        File instances = Paths.get(".", "instances").normalize().toFile();

        CvFactory cvFactory = new CvFactory();
        ObjectFactory objFactory = new ObjectFactory();
        Cv cv = objFactory.createCv();
        cv = cvFactory.fillCvWithCrapData(cv);

        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(new StreamSource(new File(schemas.getAbsolutePath() + "/cv.xsd")));

        JAXBContext jc = JAXBContext.newInstance(Cv.class);
        Marshaller m = jc.createMarshaller();
        m.setSchema(schema);
        m.marshal(cv, new File(instances.getAbsolutePath()+"/cv.xml"));
    }

    private static void generateTranscripts() throws Exception{
        File schemas = Paths.get(".", "schemas").normalize().toFile();
        File instances = Paths.get(".", "instances").normalize().toFile();

        TranscriptFactory transcriptFactory = new TranscriptFactory();
        ObjectFactory objFactory = new ObjectFactory();
        Transcript transcript = objFactory.createTranscript();
        transcript = transcriptFactory.fillWithCrapData(transcript);

        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(new StreamSource(new File(schemas.getAbsolutePath() + "/transcript.xsd")));

        JAXBContext jc = JAXBContext.newInstance(Transcript.class);
        Marshaller m = jc.createMarshaller();
        m.setSchema(schema);
        m.marshal(transcript, new File(instances.getAbsolutePath()+"/transcript.xml"));
    }

    public static void main(String[] args) throws Exception {
        //generateCompanies();
        //generateEmployments();
        //generateCvs();
        //generateTranscripts();

        File schemas = Paths.get(".", "schemas").normalize().toFile();
        File instances = Paths.get(".", "instances").normalize().toFile();

        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(new StreamSource(new File(schemas.getAbsolutePath() + "/profile.xsd")));

        JAXBContext jc = JAXBContext.newInstance(Profile.class);
        Marshaller m = jc.createMarshaller();
        m.setSchema(schema);


        /*
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(company, System.out);
        m.setSchema(schema);
        */

        IParsable p = new DomProfileParsable();
        //IParsable p = new SaxProfileIParsable();
        //IParsable p = new JAXBparser();
        //IParsable p = new XSLTParser();

        Profile profile = p.parse();


        m.marshal(profile, new File(instances.getAbsolutePath()+"/profile.xml"));
    }

}
