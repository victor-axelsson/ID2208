package se.kth;

import se.kth.factories.CompanyFactory;
import se.kth.ns.jobservicecompany.Company;
import se.kth.ns.jobservicecompany.ObjectFactory;
import se.kth.ns.jobservicecompany.Profile;
import se.kth.parsers.DomProfileIParsable;
import se.kth.parsers.IParsable;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;

/**
 * Created by victoraxelsson on 2017-01-25.
 */
public class Main {

    public static void main(String[] args) throws Exception {

        CompanyFactory companyFactory = new CompanyFactory();

        ObjectFactory objFactory = new ObjectFactory();
        Company company = objFactory.createCompany();
        company = companyFactory.fillCompanyWithCrapData(company);

        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(new StreamSource(new File("/Users/victoraxelsson/Desktop/web_services/asignment1/schemas/profile.xsd")));

        JAXBContext jc = JAXBContext.newInstance(Profile.class);
        Marshaller m = jc.createMarshaller();
        m.setSchema(schema);


        /*
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(company, System.out);
        m.setSchema(schema);
        */

        IParsable p = new DomProfileIParsable();
        //IParsable p = new SaxProfileIParsable();

        Profile profile = p.parse("dude");
        m.marshal(profile, new File("/Users/victoraxelsson/Desktop/web_services/asignment1/instances/profile-"+company.getCompanyName().toLowerCase()+".xml"));
    }

}
