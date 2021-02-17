package edu.usf.cutr.grha.model

import com.univocity.parsers.annotations.Parsed

data class LocationFixData
(
    @Parsed val fix: String = "",
    @Parsed val provider:String = "",
    @Parsed val latitude: Double = 0.0,
    @Parsed val longitude: Double = 0.0,
    @Parsed val altitude: Double = 0.0,
    @Parsed val speed: Double = 0.0,
    @Parsed val accuracy: Double = 0.0,
    @Parsed val timeInMs: Long = 0
)
