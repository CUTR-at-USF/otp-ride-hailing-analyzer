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
import edu.usf.cutr.grha.io.GPSTestExtendedHeaderParser;
import edu.usf.cutr.grha.io.GPSTestParser;
import edu.usf.cutr.grha.utils.IOUtils;

public class ProcessorMain {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("The first command-line parameter should be the GPSTest data filename with simple headers, and the 2nd parameter should be the filename of the Chicago datset");
            System.exit(1);
        }

        GPSTestParser gpsTestParser = new GPSTestParser(args[0]);
        System.out.println("*** GPSTest data ***");
        IOUtils.printLocations(gpsTestParser.parseFile());

        System.out.println("*** Chicago open TNC data ***");
        ChicagoTncParser chicagoTncParser = new ChicagoTncParser(args[1]);
        IOUtils.printChicagoTncData(chicagoTncParser.parseFile());
    }

    /**
     * Currently unused - parses the GPSTest file with the additional comments in the CSV header
     */
    public static void demoParsingWithExtendedHeader() {
        GPSTestExtendedHeaderParser GPSTestExtendedHeaderParser = new GPSTestExtendedHeaderParser("gnss_log_2021_02_05_13_20_58.txt");
        IOUtils.printLocations(GPSTestExtendedHeaderParser.parseFile());
    }
}
