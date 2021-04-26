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
package edu.usf.cutr.grha.model

import com.univocity.parsers.annotations.Parsed

data class ChicagoTncData
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

    @Parsed var totalTravelTime1: Int? = 0,
    @Parsed var walkTime1: Int? = 0,
    @Parsed var transitTime1: Int? = 0,
    @Parsed var totalDistance1: Double? = 0.0,
    @Parsed var altitudeChange1: Double? = 0.0,
    @Parsed var transfers1: Int? = 0,
    @Parsed var Modes1: String? = null,

    @Parsed var totalTravelTime2: Int? = 0,
    @Parsed var walkTime2: Int? = 0,
    @Parsed var transitTime2: Int? = 0,
    @Parsed var totalDistance2: Double? = 0.0,
    @Parsed var altitudeChange2: Double? = 0.0,
    @Parsed var transfers2: Int? = 0,
    @Parsed var Modes2: String? = null,

    @Parsed var totalTravelTime3: Int? = 0,
    @Parsed var walkTime3: Int? = 0,
    @Parsed var transitTime3: Int? = 0,
    @Parsed var totalDistance3: Double? = 0.0,
    @Parsed var altitudeChange3: Double? = 0.0,
    @Parsed var transfers3: Int? = 0,
    @Parsed var Modes3: String? = null
)
