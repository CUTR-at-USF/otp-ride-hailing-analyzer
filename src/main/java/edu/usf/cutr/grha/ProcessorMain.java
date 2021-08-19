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
package edu.usf.cutr.grha;

import edu.usf.cutr.grha.io.ChicagoTncParser;
import edu.usf.cutr.grha.model.ChicagoTncData;
import edu.usf.cutr.grha.otp.OtpService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class ProcessorMain {

    public static void main(String[] args) {
        if (args.length < 1 || args.length > 2) {
            System.out.println(" The first command-line parameter should be the filename of the Chicago dataset," +
                    " and the 2nd parameter should be the number of request you'd like to process concurrently (Optional). ");
            System.exit(1);
        }

        try {
            System.out.println("Reading Chicago Open TNC data...");
            ChicagoTncParser chicagoTncParser = new ChicagoTncParser(new FileInputStream(args[0]));
            List<ChicagoTncData> chicagoTncDataList = chicagoTncParser.parseFile();

            // Create output folder if it doesn't exist
            new File("output").mkdirs();

            int numConcurrentRequests = 10;
            if (args.length > 1) {
                numConcurrentRequests = Integer.parseInt(args[1]);
            }
            System.out.println("Processing dataset. Using " + numConcurrentRequests + " concurrent requests to OpenTripPlanner server...");
            new OtpService(chicagoTncDataList, "http://localhost:8080/otp/routers/default/plan").call(
                    numConcurrentRequests);
            System.out.println("Done! Please checking the /output folder for the dataset with additional columns describing scheduled transit trips.");
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("Chicago Data file not found. Please check the path");
        }
    }
}
