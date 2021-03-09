package edu.usf.cutr.grha.gtfs;

import org.onebusaway.gtfs.model.Agency;
import org.onebusaway.gtfs.model.AgencyAndId;
import org.onebusaway.gtfs.model.Route;
import org.onebusaway.gtfs.serialization.GtfsWriter;

import java.io.File;

public class GtfsWriterMain {

    public String filePath;

    public GtfsWriterMain(String filePath) {
        this.filePath = filePath;
    }

    public void writeData() {
        GtfsWriter  writer = new GtfsWriter();
        writer.setOutputLocation(new File(filePath));

        Agency agency = new Agency();
        agency.setId("S");
        agency.setName("Sudarshan's Transit");
        agency.setUrl("www.sudarshan.com");
        agency.setTimezone("EST");

        writer.handleEntity(agency);

        Route route = new Route();
        route.setId(AgencyAndId.convertFromString(agency.getId()+"_"+"1"));
        route.setShortName("S");
        route.setAgency(agency);

        writer.handleEntity(route);
        try {
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
