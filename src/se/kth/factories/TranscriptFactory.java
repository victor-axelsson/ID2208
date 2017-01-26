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

        Transcript.University.Courses courses = new Transcript.University.Courses();
        Profile.University.Courses.Course c1 = new Profile.University.Courses.Course();
        c1.setGrade(new BigDecimal("10"));
        c1.setName("Sitting down");

        Profile.University.Courses.Course c2 = new Profile.University.Courses.Course();
        c2.setGrade(new BigDecimal("8"));
        c2.setName("History of walls");



        transcript.getUniversity().add(university);

        return transcript;
    }
}
