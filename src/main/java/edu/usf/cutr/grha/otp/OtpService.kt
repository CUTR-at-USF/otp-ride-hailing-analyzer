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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resumeWithException

class OtpService(
    private var chicagoTncData: List<ChicagoTncData>,
    private var url: String
) {

    fun call() {
        getPlanData()
    }

    private fun getPlanData() {
        val origin = GtfsUtils.latLong(
            chicagoTncData[2].pickupCentroidLatitude,
            chicagoTncData[2].pickupCentroidLongitude
        )
        val destination = GtfsUtils.latLong(
            chicagoTncData[2].dropoffCentroidLongitude,
            chicagoTncData[2].dropoffCentroidLongitude
        )
        val requestParameters = RequestParameters(fromPlace = origin, toPlace = destination)
        val planApi = PlanApi(url, requestParameters)
        val planFlow = flow {
            emit(makePlanRequest(planApi).toString())
        }
        runBlocking {
            planFlow.collect {
                println(it)
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
}