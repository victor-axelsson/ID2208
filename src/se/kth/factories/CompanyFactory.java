package se.kth.factories;

import se.kth.ns.jobservicecompany.Company;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;

/**
 * Created by victoraxelsson on 2017-01-25.
 */
public class CompanyFactory {
    private String[] companyNames = {
            "Opentech",
            "Solcone",
            "Quoteace",
            "konkelectronics",
            "ranlane",
            "Tamptech",
            "cefax",
            "Job-line",
            "Techlux",
            "Lexinix",
            "Zimsanice",
            "Drill-touch",
            "Roundlane",
            "Jayronis",
            "antam",
            "Univolzim",
            "X-lux",
            "U-lax",
            "redware",
            "Damin",
            "Hatviatax",
            "Tontech",
            "y-taxon",
            "Medtonex",
            "Caneunajob",
            "Gravelane",
            "La-dax",
            "Meddexon"
    };

    private Random rand;
    public CompanyFactory(){
        rand = new Random();
    }

    private int getRandomInt(int min, int max){
        return rand.nextInt(max) + min;
    }

    private String getRandom(String[] input){
        return input[getRandomInt(0, input.length)];
    }

    public Company fillCompanyWithCrapData(Company company){

        String name = getRandom(companyNames);

        company.setCompanyName(name);
        company.setId(new BigInteger(130, rand).toString(32));
        company.setNumberOfEmployees(new BigInteger(getRandomInt(0, 100) + ""));
        company.setOffice(new Company.Office());
        company.getOffice().setLat(new BigDecimal(getRandomInt(5, 60) + "." + getRandomInt(1000, 5000)));
        company.getOffice().setLng(new BigDecimal(getRandomInt(5, 18) + "." + getRandomInt(1000, 5000)));
        company.setWebsite("http://www." + name.toLowerCase() + ".com");


        return company;
    }


}
