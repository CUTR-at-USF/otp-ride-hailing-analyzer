package edu.usf.cutr.grha.utils

import edu.usf.cutr.grha.model.LocationFixData

/**
 * Utils class to interact with the LocationFixData model class
 */
object LocationFixDataUtils {
    private val locationDataList: MutableList<LocationFixData> = mutableListOf()
    @JvmStatic
     fun saveLocationData(rows: List<Array<String>>) {
         rows.forEach {
             if (it.size == 8) {
                 val locationFixData = LocationFixData(
                     it[0], it[1], it[2].toDouble(), it[3].toDouble(), it[4].toDouble(),
                     it[5].toDouble(), it[6].toDouble(), it[7].toLong()
                 )
                 locationDataList.add(locationFixData)
             }
         }
    }

    @JvmStatic
    fun getLocationData(): MutableList<LocationFixData> {
        return locationDataList
    }
}