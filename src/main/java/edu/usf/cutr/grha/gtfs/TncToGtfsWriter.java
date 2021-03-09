package edu.usf.cutr.grha.gtfs;

import edu.usf.cutr.grha.model.ChicagoTncData;
import org.onebusaway.gtfs.model.Agency;
import org.onebusaway.gtfs.model.AgencyAndId;
import org.onebusaway.gtfs.model.Route;
import org.onebusaway.gtfs.model.Stop;
import org.onebusaway.gtfs.serialization.GtfsWriter;

import java.io.File;
import java.util.List;

public class TncToGtfsWriter {

    public String filePath;

    public TncToGtfsWriter(String filePath) {
        this.filePath = filePath;
    }

    public void startWritingData(List<ChicagoTncData> chicagoTncDataList) {
        GtfsWriter writer = new GtfsWriter();
        writer.setOutputLocation(new File(filePath));

        //Creates a single fake agency in agency.txt for TNC trips
        Agency agency = setFakeAgency();
        writer.handleEntity(agency);

        //Creates a single fake route in routes.txt that all TNC trips can be assigned to
        writer.handleEntity(setFakeRoute(agency));

        int counter = 0;
        for (ChicagoTncData tncData: chicagoTncDataList) {

            //Creates a bus stop (for stops.txt) for the origin and destination location
            Stop originStop = setStop(tncData, agency, counter, true);
            counter++;

            writer.handleEntity(originStop);

            Stop destinationStop = setStop(tncData, agency, counter, false);
            counter++;

            writer.handleEntity(destinationStop);

        }

        try {
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static Agency setFakeAgency() {
        Agency agency = new Agency();
        agency.setId(GtfsConstants.FAKE_AGENCY_ID);
        agency.setName(GtfsConstants.FAKE_AGENCY_NAME);
        agency.setUrl(GtfsConstants.FAKE_AGENCY_URL);
        agency.setTimezone(GtfsConstants.AGENCY_TIMEZONE);
        return agency;
    }

    public static Route setFakeRoute(Agency agency) {
        Route route = new Route();
        route.setId(AgencyAndId.convertFromString(agency.getId() + "_" + GtfsConstants.FAKE_ROUTE_ID));
        route.setShortName(GtfsConstants.FAKE_SHORT_NAME);
        route.setAgency(agency);
        return route;
    }

    public static Stop setStop(ChicagoTncData tncData, Agency agency, int counter, boolean isOrigin) {
        Stop stop = new Stop();
        stop.setId(AgencyAndId.convertFromString(agency.getId() + "_" + counter));
        stop.setName(tncData.getTripId());
        if (isOrigin) {
            stop.setLat(tncData.getPickupCentroidLatitude());
            stop.setLon(tncData.getPickupCentroidLongitude());
        } else {
            stop.setLat(tncData.getDropoffCentroidLatitude());
            stop.setLon(tncData.getDropoffCentroidLongitude());
        }
        return stop;
    }
}
