package edu.usf.cutr.grha.otp

import edu.usf.cutr.grha.model.ChicagoTncData
import edu.usf.cutr.grha.utils.GtfsUtils
import edu.usf.cutr.otp.plan.api.PlanApi
import edu.usf.cutr.otp.plan.model.Planner
import edu.usf.cutr.otp.plan.model.RequestParameters
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

class OtpService(
    private var chicagoTncData: List<ChicagoTncData>,
    private var url: String
) {

    fun call() {
        getPlanData()
    }

    private fun getPlanData() {
        runBlocking {
            chicagoTncData
                .asFlow()
                .filter {
                    isValidLocation(
                        it.pickupCentroidLatitude,
                        it.pickupCentroidLongitude,
                        it.dropoffCentroidLatitude,
                        it.dropoffCentroidLongitude
                    )
                }
                .flatMapMerge(concurrency = 10) {
                    flow {
                        val origin = GtfsUtils.latLong(
                            it.pickupCentroidLatitude,
                            it.pickupCentroidLongitude
                        )
                        val destination = GtfsUtils.latLong(
                            it.dropoffCentroidLatitude,
                            it.dropoffCentroidLongitude
                        )

                        val requestParameters = RequestParameters(fromPlace = origin, toPlace = destination)
                        val planApi = PlanApi(url, requestParameters)
                        emit(makePlanRequest(planApi, chicagoTncData.indexOf(it)))
                    }
                }
                .collect {
                    var chicagoTnc = chicagoTncData[it.additionalProperties["Index"] as Int]
                    // TODO get total travel time from Itinerary

                    // TODO get total travel time by walk or bus from Itinerary

                    // TODO get total distance by looping through List<Legs>

                    // TODO get Altitude change from Itinerary

                    // TODO get number of transfers from Itinerary

                    // TODO get Traverse Modes from List<legs>
                }
        }
    }

    private suspend fun makePlanRequest(planApi: PlanApi, i: Int): Planner = suspendCancellableCoroutine { cont ->
        planApi.getPlan({
            it.additionalProperties["Index"] = i
            cont.resume(it, null)
        }, {
            if (it != null)
                cont.resumeWithException(it)
        })
    }

    private fun isValidLocation(originLat: Double, originLong: Double, destLat: Double, destLong: Double): Boolean {
        return originLat != 0.0 && originLong != 0.0 &&
                destLat != 0.0 && destLong != 0.0
    }
}