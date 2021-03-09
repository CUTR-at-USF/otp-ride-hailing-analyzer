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

    public void startWritingData() {
        GtfsWriter  writer = new GtfsWriter();
        writer.setOutputLocation(new File(filePath));

        Agency agency = new Agency();
        agency.setId(GtfsConstants.FAKE_AGENCY_ID);
        agency.setName(GtfsConstants.FAKE_AGENCY_NAME);
        agency.setUrl(GtfsConstants.FAKE_AGENCY_URL);
        agency.setTimezone(GtfsConstants.AGENCY_TIMEZONE);

        writer.handleEntity(agency);

        Route route = new Route();
        route.setId(AgencyAndId.convertFromString(agency.getId() + "_" + GtfsConstants.FAKE_ROUTE_ID));
        route.setShortName(GtfsConstants.FAKE_SHORT_NAME);
        route.setAgency(agency);

        writer.handleEntity(route);

        try {
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
