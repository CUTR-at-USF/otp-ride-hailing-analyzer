/*
 * Copyright (C) 2021 University of South Florida
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.usf.cutr.grha

import edu.usf.cutr.grha.io.ChicagoTncParser
import edu.usf.cutr.grha.io.ChicagoTncStreamWriter.Companion.DEFAULT_OUTPUT
import edu.usf.cutr.grha.otp.OtpService
import java.io.FileInputStream
import java.io.FileNotFoundException
import kotlin.system.exitProcess

object ProcessorMain {
    @JvmStatic
    fun main(args: Array<String>) {
        if (args.isEmpty() || args.size > 3) {
            println(
                " The first command-line parameter should be the filename of the Chicago dataset," +
                        " and the 2nd parameter should be the number of request you'd like to process concurrently (Optional)," +
                        " and the 3rd parameter should be the output file name (Optional). "
            )
            exitProcess(1)
        }
        try {
            println("Reading Chicago Open TNC data...")
            val chicagoTncParser = ChicagoTncParser(FileInputStream(args[0]))
            val chicagoTncDataList = chicagoTncParser.parseFile()

            var numConcurrentRequests = 10
            if (args.size > 1) {
                numConcurrentRequests = args[1].toInt()
            }
            println("Processing dataset. Using $numConcurrentRequests concurrent requests to OpenTripPlanner server...")
            var outputFile = DEFAULT_OUTPUT
            if (args.size > 2) {
                outputFile = args[2]
            }
            OtpService(chicagoTncDataList, "http://localhost:8080/otp/routers/default/plan", outputFile).call(
                numConcurrentRequests
            )
            println("Done! Please checking the $outputFile file for the dataset with additional columns describing scheduled transit trips.")
        } catch (fileNotFoundException: FileNotFoundException) {
            println("Chicago Data file not found. Please check the path")
        }
    }
}