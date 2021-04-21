package edu.usf.cutr.grha.otp

import edu.usf.cutr.grha.model.ChicagoTncData
import edu.usf.cutr.grha.utils.GtfsUtils
import edu.usf.cutr.otp.plan.api.PlanApi
import edu.usf.cutr.otp.plan.model.Planner
import edu.usf.cutr.otp.plan.model.RequestParameters
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.SingleOnSubscribe
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
        val planFlow: Flow<Planner> = flow {
            while (i < 42) {
                if (chicagoTncData[i].pickupCentroidLatitude == 0.0 ||
                    chicagoTncData[i].pickupCentroidLongitude == 0.0 ||
                    chicagoTncData[i].dropoffCentroidLongitude == 0.0 ||
                    chicagoTncData[i].dropoffCentroidLongitude == 0.0
                ) {
                    i++
                    continue
                }
                val origin = GtfsUtils.latLong(
                    chicagoTncData[i].pickupCentroidLatitude,
                    chicagoTncData[i].pickupCentroidLongitude
                )
                val destination = GtfsUtils.latLong(
                    chicagoTncData[i].dropoffCentroidLongitude,
                    chicagoTncData[i].dropoffCentroidLongitude
                )
                val requestParameters = RequestParameters(fromPlace = origin, toPlace = destination)
                start = System.currentTimeMillis()
                val planApi = PlanApi(url, requestParameters)
                emit(makePlanRequest(planApi, i))
                i++
            }
        }
        runBlocking {
            planFlow.collect {
                val time = System.currentTimeMillis() - start
                println(it)
                println("Time: $time ms")
            }
        }
    }

    private fun makePlanFlow() {
        val dataFlow = chicagoTncData.asFlow()
        runBlocking {
            dataFlow
                .filter {
                    it.pickupCentroidLatitude != 0.0
                    it.pickupCentroidLongitude != 0.0
                    it.dropoffCentroidLatitude != 0.0
                    it.dropoffCentroidLongitude != 0.0
                }
                .onEach {
                    val origin = GtfsUtils.latLong(
                        it.pickupCentroidLatitude,
                        it.pickupCentroidLongitude
                    )
                    val destination = GtfsUtils.latLong(
                        it.dropoffCentroidLongitude,
                        it.dropoffCentroidLongitude
                    )

                    val requestParameters = RequestParameters(fromPlace = origin, toPlace = destination)
                    val planApi = PlanApi(url, requestParameters)
                    makePlanRequest(planApi, chicagoTncData.indexOf(it))

                }.flowOn(Dispatchers.IO)
                .collect {
                    //val time = System.currentTimeMillis()
                    //println("Index" + chicagoTncData.indexOf(it))
                }
        }
    }

    private suspend fun makePlanRequest(planApi: PlanApi, i: Int): Planner = suspendCancellableCoroutine { cont ->
        planApi.getPlan({
            println(it)
            println("record $i")
            cont.resume(it, null)
        }, {
            if (it != null)
                cont.resumeWithException(it)
        })
    }
}