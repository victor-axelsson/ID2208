package se.kth.factories;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import se.kth.ns.jobservicecompany.Cv;

import java.util.GregorianCalendar;

/**
 * Created by Nick on 1/26/2017.
 */
public class CvFactory extends Factory {

    String[] offices = {"Sales", "IT", "HumanResource", "Logistics", "MoneyLaundry"};

    public Cv fillCvWithCrapData(Cv cv){

        cv.setFirstName("Larry");
        cv.setLastName("Barry");
        cv.setId("s" + 123123);
        Cv.Project project = new Cv.Project();
        project.setName("Parry " + getRandom(offices));
        project.setDescription("Description yada yada yada");
        project.setStartDate(new XMLGregorianCalendarImpl(new GregorianCalendar(1998, 12, 26)));
        project.setFinishDate(new XMLGregorianCalendarImpl(new GregorianCalendar(2003, 6, 13)));
        cv.getProject().add(project);
        return cv;
    }
}
