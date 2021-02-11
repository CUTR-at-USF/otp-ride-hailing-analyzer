package usf.edu.cutr.grha.model

data class LocationFixData
(
val fix: String,
val provider:String,
val latitude: Double,
val longitude: Double,
val altitude: Double,
val speed: Double,
val accuracy: Double,
val timeInMs: Long
)
