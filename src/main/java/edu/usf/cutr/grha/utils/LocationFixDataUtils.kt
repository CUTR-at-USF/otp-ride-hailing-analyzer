package edu.usf.cutr.grha.utils

import edu.usf.cutr.grha.model.LocationFixData

/**
 * Utils class to interact with the LocationFixData model class
 */
object LocationFixDataUtils {

    @JvmStatic
     fun saveLocationData(rows: List<Array<String>>): List<LocationFixData> {
         val locationDataList: MutableList<LocationFixData> = mutableListOf()
         rows.forEach {
             if (it.size == 8) {
                 val locationFixData = LocationFixData(
                     it[1], it[2].toDouble(), it[3].toDouble(), it[4].toDouble(),
                     it[5].toDouble(), it[6].toDouble(), it[7].toLong()
                 )
                 locationDataList.add(locationFixData)
             }
         }
        return locationDataList
    }

    @JvmStatic
    fun printLocationData(data: List<LocationFixData>) {
        for (d in data) {
            println(
                d.provider + " " + d.latitude + " " +
                        d.longitude + " " + d.accuracy + " " + d.speed + " " +
                        d.altitude + " " + d.timeInMs
            )
        }
    }
}