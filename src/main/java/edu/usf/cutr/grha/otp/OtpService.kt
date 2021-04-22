package edu.usf.cutr.grha.otp

import edu.usf.cutr.grha.model.ChicagoTncData
import edu.usf.cutr.grha.utils.GtfsUtils
import edu.usf.cutr.otp.plan.api.PlanApi
import edu.usf.cutr.otp.plan.model.Planner
import edu.usf.cutr.otp.plan.model.RequestParameters
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
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
        var i = 0
        var start = 0L
        runBlocking {
            var planFlow: Flow<Planner> = flow {}
            while (i < 41) {
                GlobalScope.launch {
                    if (!isValidLocation(
                            chicagoTncData[i].pickupCentroidLatitude,
                            chicagoTncData[i].pickupCentroidLongitude,
                            chicagoTncData[i].dropoffCentroidLatitude,
                            chicagoTncData[i].dropoffCentroidLongitude
                        )
                    ) {
                        i++
                    } else {
                        val origin = GtfsUtils.latLong(
                            chicagoTncData[i].pickupCentroidLatitude,
                            chicagoTncData[i].pickupCentroidLongitude
                        )
                        val destination = GtfsUtils.latLong(
                            chicagoTncData[i].dropoffCentroidLatitude,
                            chicagoTncData[i].dropoffCentroidLongitude
                        )
                        val requestParameters = RequestParameters(fromPlace = origin, toPlace = destination)
                        start = System.currentTimeMillis()
                        val planApi = PlanApi(url, requestParameters)
                        planFlow = flow {
                            emit(makePlanRequest(planApi))
                            println("Emitted Record: $i")
                            i++
                        }
                    }
                }
                planFlow.collect {
                    val time = System.currentTimeMillis() - start
                    println("Consumed Record: $i")
                    println(it)
                    println("Time: $time ms")
                }
            }
        }


    }

    private fun makePlanFlow() {
        val dataFlow = chicagoTncData.asFlow()
        runBlocking {
            dataFlow
                .filter {
                    isValidLocation(
                        it.pickupCentroidLatitude,
                        it.pickupCentroidLongitude,
                        it.dropoffCentroidLatitude,
                        it.dropoffCentroidLongitude
                    )
                }
                .collect {
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
                    println(makePlanRequest(planApi))
                    println(chicagoTncData.indexOf(it))
                }
        }
    }

    private suspend fun makePlanRequest(planApi: PlanApi): Planner = suspendCancellableCoroutine { cont ->
        planApi.getPlan({
            cont.resume(it, null)
        }, {
            if (it != null)
                cont.resumeWithException(it)
        })
    }

    private fun isValidLocation(originLat: Double, originLong: Double, destLat: Double, destLong: Double): Boolean {
        return originLat != 0.0 || originLong != 0.0 ||
                destLat != 0.0 || destLong != 0.0
    }
}