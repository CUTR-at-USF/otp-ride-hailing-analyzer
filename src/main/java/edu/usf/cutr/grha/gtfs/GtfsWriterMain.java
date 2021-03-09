package edu.usf.cutr.grha.gtfs;

import edu.usf.cutr.grha.model.ChicagoTncData;
import org.onebusaway.gtfs.model.Agency;
import org.onebusaway.gtfs.model.AgencyAndId;
import org.onebusaway.gtfs.model.Route;
import org.onebusaway.gtfs.model.Stop;
import org.onebusaway.gtfs.serialization.GtfsWriter;

import java.io.File;
import java.util.List;

public class GtfsWriterMain {

    public String filePath;

    public GtfsWriterMain(String filePath) {
        this.filePath = filePath;
    }

    public void startWritingData(List<ChicagoTncData> chicagoTncDataList) {
        GtfsWriter  writer = new GtfsWriter();
        writer.setOutputLocation(new File(filePath));

        //Creates a single fake agency in agency.txt for TNC trips
        Agency agency = new Agency();
        agency.setId(GtfsConstants.FAKE_AGENCY_ID);
        agency.setName(GtfsConstants.FAKE_AGENCY_NAME);
        agency.setUrl(GtfsConstants.FAKE_AGENCY_URL);
        agency.setTimezone(GtfsConstants.AGENCY_TIMEZONE);

        writer.handleEntity(agency);

        //Creates a single fake route in routes.txt that all TNC trips can be assigned to
        Route route = new Route();
        route.setId(AgencyAndId.convertFromString(agency.getId() + "_" + GtfsConstants.FAKE_ROUTE_ID));
        route.setShortName(GtfsConstants.FAKE_SHORT_NAME);
        route.setAgency(agency);

        writer.handleEntity(route);

        int counter = 0;
        for (ChicagoTncData tncData: chicagoTncDataList) {

            //Creates a bus stop (for stops.txt) for the origin and destination location
            Stop stop = new Stop();
            stop.setId(AgencyAndId.convertFromString(agency.getId() + "_" + counter++));
            stop.setName(tncData.getTripId());
            stop.setLat(tncData.getPickupCentroidLatitude());
            stop.setLon(tncData.getPickupCentroidLongitude());

            writer.handleEntity(stop);

            stop.setId(AgencyAndId.convertFromString(agency.getId() +"_" + counter++));
            stop.setName(tncData.getTripId());
            stop.setLat(tncData.getDropoffCentroidLatitude());
            stop.setLon(tncData.getDropoffCentroidLongitude());

            writer.handleEntity(stop);

        }

        try {
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
