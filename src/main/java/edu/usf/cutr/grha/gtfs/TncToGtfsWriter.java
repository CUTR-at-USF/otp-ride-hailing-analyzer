package edu.usf.cutr.grha.gtfs;

import edu.usf.cutr.grha.model.ChicagoTncData;
import edu.usf.cutr.grha.utils.GtfsUtils;
import org.onebusaway.gtfs.model.*;
import org.onebusaway.gtfs.model.calendar.ServiceDate;
import org.onebusaway.gtfs.serialization.GtfsWriter;

import java.io.File;
import java.util.Date;
import java.util.List;

public class TncToGtfsWriter {
    private static final String FAKE_AGENCY_ID = "chicago-tnc";
    private static final String FAKE_AGENCY_NAME = "Chicago TNC Trips";
    private static final String FAKE_AGENCY_URL = "https://data.cityofchicago.org/Transportation/Transportation-Network-Providers-Trips/m6dm-c72p/data";
    private static final String AGENCY_TIMEZONE = "America/Chicago";

    private static final String FAKE_ROUTE_ID = "1";
    private static final String FAKE_SHORT_NAME = "TNC Trips";

    private static final String PICKUP_STOP_SUFFIX = "_pickup";
    private static final String DROPOFF_STOP_SUFFIX = "_dropoff";

    public String filePath;

    public TncToGtfsWriter(String filePath) {
        this.filePath = filePath;
    }

    public void write(List<ChicagoTncData> chicagoTncDataList) {
        GtfsWriter writer = new GtfsWriter();
        writer.setOutputLocation(new File(filePath));

        // Creates a single fake agency in agency.txt for TNC trips
        Agency agency = newFakeAgency();
        writer.handleEntity(agency);

        // Creates a single fake route in routes.txt that all TNC trips can be assigned to
        Route route = newFakeRoute(agency);
        writer.handleEntity(route);

        int counter = 1;
        for (ChicagoTncData tncData: chicagoTncDataList) {

            // Creates a bus stop (for stops.txt) for the origin and destination location
            Stop originStop = newStop(tncData, agency, counter, true);
            counter++;

            writer.handleEntity(originStop);

            Stop destinationStop = newStop(tncData, agency, counter, false);
            counter++;

            writer.handleEntity(destinationStop);

            // Creates a record in calendar_dates.txt with a unique service_id
            ServiceCalendarDate calendarDate = newCalenderDate(tncData, agency);
            writer.handleEntity(calendarDate);

            // Creates a record to trips.txt
            Trip trip = newTrip(tncData, route, calendarDate, agency);
            writer.handleEntity(trip);

            // Creates a record in stop_times.txt referencing the trip_id
            StopTime originStopTime = newStopTime(tncData, trip, originStop, true);
            writer.handleEntity(originStopTime);

            StopTime destinationStopTime = newStopTime(tncData, trip, destinationStop, false);
            writer.handleEntity(destinationStopTime);
        }

        try {
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("GTFS data exported to: " + filePath);
    }

    public static Agency newFakeAgency() {
        Agency agency = new Agency();
        agency.setId(FAKE_AGENCY_ID);
        agency.setName(FAKE_AGENCY_NAME);
        agency.setUrl(FAKE_AGENCY_URL);
        agency.setTimezone(AGENCY_TIMEZONE);
        return agency;
    }

    public static Route newFakeRoute(Agency agency) {
        Route route = new Route();
        route.setId(AgencyAndId.convertFromString(agency.getId() + "_" + FAKE_ROUTE_ID));
        route.setShortName(FAKE_SHORT_NAME);
        route.setAgency(agency);
        return route;
    }

    public static Stop newStop(ChicagoTncData tncData, Agency agency, int counter, boolean isOrigin) {
        Stop stop = new Stop();
        stop.setId(AgencyAndId.convertFromString(agency.getId() + "_" + counter));
        if (isOrigin) {
            stop.setName(tncData.getTripId() + PICKUP_STOP_SUFFIX);
            stop.setLat(tncData.getPickupCentroidLatitude());
            stop.setLon(tncData.getPickupCentroidLongitude());
        } else {
            stop.setName(tncData.getTripId() + DROPOFF_STOP_SUFFIX);
            stop.setLat(tncData.getDropoffCentroidLatitude());
            stop.setLon(tncData.getDropoffCentroidLongitude());
        }
        return stop;
    }

    public static ServiceCalendarDate newCalenderDate(ChicagoTncData tncData, Agency agency) {
        ServiceCalendarDate calendarDate = new ServiceCalendarDate();
        calendarDate.setServiceId(AgencyAndId.convertFromString(agency.getId() + "_" + tncData.getTripId()));
        Date date = GtfsUtils.getDateFromTimeStamp(tncData.getTripStartTimeStamp(), "M/dd/yyyy HH:mm");
        calendarDate.setDate(new ServiceDate(date));
        calendarDate.setExceptionType(1);

        return calendarDate;
    }

    public static Trip newTrip(ChicagoTncData tncData, Route route, ServiceCalendarDate date, Agency agency) {
        Trip trip = new Trip();
        trip.setId(AgencyAndId.convertFromString(agency.getId() + "_" + tncData.getTripId()));
        trip.setServiceId(date.getServiceId());
        trip.setRoute(route);
        return trip;
    }

    public static StopTime newStopTime(ChicagoTncData tncData, Trip trip, Stop stop, boolean isOrigin) {
        StopTime stopTime = new StopTime();
        stopTime.setTrip(trip);
        Date date;
        if (isOrigin) {
            date = GtfsUtils.getDateFromTimeStamp(tncData.getTripStartTimeStamp(), "M/dd/yyyy hh:mm");
            stopTime.setStopSequence(1);
        } else {
            date = GtfsUtils.getDateFromTimeStamp(tncData.getTripEndTimeStamp(), "M/dd/yyyy hh:mm");
            stopTime.setStopSequence(2);
        }
        int seconds = GtfsUtils.getTimeInSeconds(date);
        stopTime.setArrivalTime(seconds);
        stopTime.setDepartureTime(seconds);
        stopTime.setStop(stop);
        return stopTime;
    }
}
