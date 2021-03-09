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

import edu.usf.cutr.grha.gtfs.GtfsWriterMain;
import edu.usf.cutr.grha.io.ChicagoTncParser;
import edu.usf.cutr.grha.io.GPSTestExtendedHeaderParser;
import edu.usf.cutr.grha.io.GPSTestParser;
import edu.usf.cutr.grha.utils.IOUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ProcessorMain {

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("The first command-line parameter should be the GPSTest data filename with simple headers, and the 2nd parameter should be the filename of the Chicago dataset");
            System.exit(1);
        }

        try {
            GPSTestParser gpsTestParser = new GPSTestParser(new FileInputStream(args[0]));
            System.out.println("*** GPSTest data ***");
            IOUtils.printLocations(gpsTestParser.parseFile());
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("GPS Test file not found. Please check the path");
        }

        try {
            System.out.println("*** Chicago open TNC data ***");
            ChicagoTncParser chicagoTncParser = new ChicagoTncParser(new FileInputStream(args[1]));
            IOUtils.printChicagoTncData(chicagoTncParser.parseFile());
            new GtfsWriterMain(args[2]).writeData();
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("Chicago Data file not found. Please check the path");
        }
    }

    /**
     * Currently unused - parses the GPSTest file with the additional comments in the CSV header
     */
    public static void demoParsingWithExtendedHeader() throws FileNotFoundException {
        GPSTestExtendedHeaderParser GPSTestExtendedHeaderParser = new GPSTestExtendedHeaderParser(
                new FileInputStream("src/test/resources/gnss_log_2021_02_05_13_20_58.txt"));
        IOUtils.printLocations(GPSTestExtendedHeaderParser.parseFile());
    }
}
