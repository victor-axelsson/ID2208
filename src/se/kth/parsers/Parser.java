package se.kth.parsers;

import se.kth.ns.jobservicecompany.Company;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by victoraxelsson on 2017-01-26.
 */
public abstract class Parser implements IParsable {

    protected File schemas;
    protected File instances;


    public Parser(){
        schemas = Paths.get(".", "schemas").normalize().toFile();
        instances = Paths.get(".", "instances").normalize().toFile();
    }
}
