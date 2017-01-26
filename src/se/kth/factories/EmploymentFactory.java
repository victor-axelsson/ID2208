package se.kth.factories;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import se.kth.ns.jobservicecompany.EmploymentRecord;

import java.util.GregorianCalendar;

/**
 * Created by victoraxelsson on 2017-01-26.
 */
public class EmploymentFactory extends Factory {


    public EmploymentRecord fillWithCrapData(EmploymentRecord record) {
        record.setFirstName("Larry");
        record.setLastName("Barry");

        EmploymentRecord.Position position = new EmploymentRecord.Position();
        position.setCompany("Co and sons");
        position.setFinishDate(new XMLGregorianCalendarImpl(new GregorianCalendar(1998, 12, 26)));
        position.setStartDate(new XMLGregorianCalendarImpl(new GregorianCalendar(1999, 12, 26)));
        position.setResponsibilities("I had none");
        position.setRole("CEO");
        record.getPosition().add(position);



        return record;
    }
}
