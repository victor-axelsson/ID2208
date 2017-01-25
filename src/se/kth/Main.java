package se.kth;

import se.kth.factories.CompanyFactory;
import se.kth.ns.jobservicecompany.Company;
import se.kth.ns.jobservicecompany.ObjectFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.math.BigDecimal;

/**
 * Created by victoraxelsson on 2017-01-25.
 */
public class Main {

    public static void main(String[] args) throws Exception {

        CompanyFactory companyFactory = new CompanyFactory();

        ObjectFactory objFactory = new ObjectFactory();
        Company company = objFactory.createCompany();
        company = companyFactory.fillCompanyWithCrapData(company);


        JAXBContext jc = JAXBContext.newInstance(Company.class);
        Marshaller m = jc.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(company, System.out);

        m.marshal(company, new File("/Users/victoraxelsson/Desktop/web_services/asignment1/instances/"+company.getCompanyName().toLowerCase()+".xml"));
    }

}
