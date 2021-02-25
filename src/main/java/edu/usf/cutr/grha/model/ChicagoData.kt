package edu.usf.cutr.grha.model

import com.univocity.parsers.annotations.Parsed

data class ChicagoData
(
    @Parsed val tripId: String = "",
    @Parsed val tripStartTimeStamp: String = "",
    @Parsed val tripEndTimeStamp: String = "",
    @Parsed val tripSeconds:  Int = 0,
    @Parsed val tripMiles: Double = 0.0,
    @Parsed val pickupCensusTract: String = "",
    @Parsed val dropoffCensusTract: String = "",
    @Parsed val pickupCommunityArea: Int = 0,
    @Parsed val dropoffCommunityArea: Int = 0,
    @Parsed val fare: Double = 0.0,
    @Parsed val tip: Double = 0.0,
    @Parsed val additionalCharges: Double = 0.0,
    @Parsed val tripTotal: Double = 0.0,
    @Parsed val sharedTripAuthorized: String = "",
    @Parsed val tripsPooled: Int = 0,
    @Parsed val pickupCentroidLatitude: Double = 0.0,
    @Parsed val pickupCentroidLongitude: Double = 0.0,
    @Parsed val dropoffCentroidLatitude: Double = 0.0,
    @Parsed val dropoffCentroidLongitude: Double = 0.0,
)
