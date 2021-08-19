/*
 * Copyright (C) 2021 University of South Florida
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.usf.cutr.grha.utils

import edu.usf.cutr.grha.model.ChicagoTncData

object IOUtils {
    @JvmStatic
    fun printChicagoTncData(data: List<ChicagoTncData>) {
        for (d in data) {
            println(d.tripId + " " + d.tripStartTimeStamp + " " + d.tripEndTimeStamp + " " + d.tripSeconds + " " +
                    d.tripMiles + " " + d.pickupCensusTract + " " + d.dropoffCensusTract + " " + d.pickupCommunityArea + " " +
                    d.dropoffCommunityArea + " " + d.fare + " " + d.tip + " " + d.additionalCharges + " " + d.tripTotal + " " +
                    d.sharedTripAuthorized + " " + d.tripsPooled + " " + d.pickupCentroidLatitude + " " +
                    d.pickupCentroidLongitude + " " + d.dropoffCentroidLatitude + " " + d.dropoffCentroidLongitude)
        }
    }
}