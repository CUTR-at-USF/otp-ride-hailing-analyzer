package edu.usf.cutr.grha.otp

import edu.usf.cutr.grha.model.ChicagoTncData
import edu.usf.cutr.grha.utils.GtfsUtils
import edu.usf.cutr.otp.plan.api.PlanApi
import edu.usf.cutr.otp.plan.model.Planner
import edu.usf.cutr.otp.plan.model.RequestParameters
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resumeWithException

class OtpService(
    private var chicagoTncData: List<ChicagoTncData>,
    private var url: String
) {

    fun call() {
        makePlanFlow()
    }

    private fun getPlanData() {
        var i = 0
        var start = 0L
        var planFlow: Flow<Planner>
            while (i < 42) {
                if (isValidLocation(
                        chicagoTncData[i].pickupCentroidLatitude,
                        chicagoTncData[i].pickupCentroidLongitude,
                        chicagoTncData[i].dropoffCentroidLatitude,
                        chicagoTncData[i].dropoffCentroidLongitude
                    )
                ) {
                    i++
                    continue
                }
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
                    emit(makePlanRequest(planApi, i))
                }
                i++
                runBlocking {
                    planFlow.collect {
                        val time = System.currentTimeMillis() - start
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
                    println(makePlanRequest(planApi, chicagoTncData.indexOf(it)))
                    println(chicagoTncData.indexOf(it))
                }
        }
    }

    private suspend fun makePlanRequest(planApi: PlanApi, i: Int): Planner = suspendCancellableCoroutine { cont ->
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