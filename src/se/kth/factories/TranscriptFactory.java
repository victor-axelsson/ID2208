package se.kth.factories;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import se.kth.ns.jobservicecompany.Profile;
import se.kth.ns.jobservicecompany.Transcript;

import java.math.BigDecimal;
import java.util.GregorianCalendar;

/**
 * Created by victoraxelsson on 2017-01-26.
 */
public class TranscriptFactory extends Factory {

    public Transcript fillWithCrapData(Transcript transcript){
        transcript.setFirstName("Dude");
        transcript.setLastName("Dudesson");
        Transcript.University university = new Transcript.University();
        university.setStartDate(new XMLGregorianCalendarImpl(new GregorianCalendar(1920, 12, 26)));
        university.setFinishDate(new XMLGregorianCalendarImpl(new GregorianCalendar(1998, 12, 26)));
        university.setDegree("Ms in SittingOnWalls");
        transcript.setUniversity(university);

        Transcript.University.Course c1 = new Transcript.University.Course();
        c1.setGrade(new BigDecimal("10"));
        c1.setName("Sitting down");

        Transcript.University.Course c2 = new Transcript.University.Course();
        c2.setGrade(new BigDecimal("8"));
        c2.setName("History of walls");


        transcript.getUniversity().getCourse().add(c1);
        transcript.getUniversity().getCourse().add(c2);


        return transcript;
    }
}
