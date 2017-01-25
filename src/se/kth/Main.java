package se.kth;

import se.kth.ns.jobservicecompany.Company;
import se.kth.ns.jobservicecompany.ObjectFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * Created by victoraxelsson on 2017-01-25.
 */
public class Main {

    public static void main(String[] args) throws Exception {

        ObjectFactory objFactory = new ObjectFactory();
        Company company = objFactory.createCompany();


        JAXBContext jc = JAXBContext.newInstance("company.po");
        Marshaller m = jc.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(company, System.out);

        System.out.println("Hello world");
    }

}
