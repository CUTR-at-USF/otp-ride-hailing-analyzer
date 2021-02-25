package edu.usf.cutr.grha.utils

import edu.usf.cutr.grha.model.ChicagoData

object ChicagoDataUtils {
    @JvmStatic
    fun printChicagoData(data: List<ChicagoData>) {
        for (d in data) {
            println (d.tripId + " " + d.tripStartTimeStamp + " " + d.tripEndTimeStamp + " " + d.tripSeconds + " " +
                    d.tripMiles + " " + d.pickupCensusTract + " " + d.dropoffCensusTract + " " + d.pickupCommunityArea + " " +
                    d.dropoffCommunityArea + " " + d.fare + " " + d.tip + " " + d.additionalCharges + " " + d.tripTotal + " " +
                    d.sharedTripAuthorized + " " + d.tripsPooled + " " + d.pickupCentroidLatitude + " " +
                    d.pickupCentroidLongitude + " " + d.dropoffCentroidLatitude + " " + d.dropoffCentroidLongitude)
        }
    }
}