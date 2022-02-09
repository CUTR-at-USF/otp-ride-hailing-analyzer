package edu.usf.cutr.grha.test

import edu.usf.cutr.grha.io.ChicagoTncParser
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ChicagoTncDataTest {

    private val chicagoDataFile = "Transportation_Network_Providers_reduced_records.csv"

    @Test
    fun testChicagoData() {
        val classLoader = Thread.currentThread().contextClassLoader
        val chicagoTncParser = ChicagoTncParser(classLoader.getResourceAsStream(chicagoDataFile))
        val chicagoTncData = chicagoTncParser.parseFile()

        val c0 = chicagoTncData[0]
        assertEquals("2cd3abd631b34b065656de022ff11d3bbfdf7566", c0.tripId)
        assertEquals("9/30/2020 0:00", c0.tripStartTimeStamp)
        assertEquals("9/30/2020 0:30", c0.tripEndTimeStamp)
        assertEquals(1564, c0.tripSeconds)
        assertEquals(15.1, c0.tripMiles, .01)
        assertNull(c0.pickupCensusTract)
        assertNull(c0.dropoffCensusTract)
        assertEquals(0, c0.pickupCommunityArea)
        assertEquals(45, c0.dropoffCommunityArea)
        assertEquals(20.0, c0.fare, .01)
        assertEquals(0.0,c0.tip, .01)
        assertEquals(3.08, c0.additionalCharges, .01)
        assertEquals(23.08, c0.tripTotal, .01)
        assertEquals("FALSE", c0.sharedTripAuthorized)
        assertEquals(1, c0.tripsPooled)
        assertEquals(0.0, c0.pickupCentroidLatitude, .01)
        assertEquals(0.0, c0.pickupCentroidLongitude, .01)
        assertEquals(41.74419953, c0.dropoffCentroidLatitude, .01)
        assertEquals(-87.58634832, c0.dropoffCentroidLongitude, .01)

        val c3 = chicagoTncData[3]
        assertEquals("026ea1923ae9ca2e4037d49895ca3ebf3bfedd6d", c3.tripId)
        assertEquals("9/30/2020 0:00", c3.tripStartTimeStamp)
        assertEquals("9/30/2020 0:15", c3.tripEndTimeStamp)
        assertEquals(438, c3.tripSeconds)
        assertEquals(2.2, c3.tripMiles, .01)
        assertEquals("17031062100", c3.pickupCensusTract)
        assertEquals("17031071500", c3.dropoffCensusTract)
        assertEquals(6, c3.pickupCommunityArea)
        assertEquals(7, c3.dropoffCommunityArea)
        assertEquals(5.0, c3.fare, .01)
        assertEquals(1.0, c3.tip, .01)
        assertEquals(3.1, c3.additionalCharges, .01)
        assertEquals(9.1, c3.tripTotal, .01)
        assertEquals("FALSE", c3.sharedTripAuthorized)
        assertEquals(1, c3.tripsPooled)
        assertEquals(41.94269184, c3.pickupCentroidLatitude, .01)
        assertEquals(-87.65177051, c3.pickupCentroidLongitude, .01)
        assertEquals(41.91461629, c3.dropoffCentroidLatitude, .01)
        assertEquals(-87.63171737, c3.dropoffCentroidLongitude, .01)
    }
}