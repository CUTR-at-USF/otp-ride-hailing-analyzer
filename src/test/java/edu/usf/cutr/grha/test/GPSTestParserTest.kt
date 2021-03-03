package edu.usf.cutr.grha.test

import edu.usf.cutr.grha.io.GPSTestParser
import org.junit.Test
import kotlin.test.assertEquals

class GPSTestParserTest {

    private val gpsTestFile = "gnss_log_2021_02_05_13_20_58_beans.txt"

    @Test
    fun testGpsTestParser() {
        val classLoader = Thread.currentThread().contextClassLoader
        val inputTestStream = classLoader.getResourceAsStream(gpsTestFile)
        val gpsTestParser = GPSTestParser(inputTestStream)
        val locations = gpsTestParser.parseFile()

        val l0 = locations[0]
        assertEquals("gps", l0.provider)
        assertEquals(28.065749, l0.latitude)
        assertEquals(-82.413471, l0.longitude)
        assertEquals(16.961182, l0.altitude)
        assertEquals(8.330000, l0.speed)
        assertEquals(7.151000, l0.accuracy)
        assertEquals(1612549260209, l0.timeInMs)

        val l1 = locations[1]
        assertEquals("gps", l1.provider)
        assertEquals(28.065719, l1.latitude)
        assertEquals(-82.413394, l1.longitude)
        assertEquals(3.703033, l1.altitude)
        assertEquals(7.920000, l1.speed)
        assertEquals(4.416990, l1.accuracy)
        assertEquals(1612549262000, l1.timeInMs)
    }
}