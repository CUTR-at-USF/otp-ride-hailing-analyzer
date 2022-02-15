# OpenTripPlanner (OTP) Ride Hailing Analyser [![Java CI with Maven](https://github.com/CUTR-at-USF/gnss-ride-hailing-analyzer/actions/workflows/maven.yml/badge.svg)](https://github.com/CUTR-at-USF/gnss-ride-hailing-analyzer/actions/workflows/maven.yml)

Java Application for calculating scheduled transit trips that have the same departure time, origin, and destination as a given dataset of TNC trips.

## Datasets

The input ride-hailing data is from
the [Chicago open TNC dataset](https://data.cityofchicago.org/Transportation/Transportation-Network-Providers-Trips/m6dm-c72p/data).

## Prerequisites

Before building the program, make sure you have the OpenTripPlanner installed and running. If you have trouble
installing the software, follow the steps to run the OTP server:

* Download the OTP Jar file from
  the [OTP-Maven-Repository](https://repo1.maven.org/maven2/org/opentripplanner/otp/2.0.0/otp-2.0.0-shaded.jar)
* Make sure you have the GTFS dataset you want to use (it ***must*** be from the same time period as the TNC input dataset) and the OSM data of the location in the same folder
* Run the OTP server with the following in your command-line:

    ```
    java -Xmx3G -jar otp-2.0.0-shaded.jar --build --serve path/to/folder
    ```

## Build

To build the application use `mvn clean package` command. This command will create a jar file (i.e., 
`gnss-ride-hailing-analyzer-1.0.0-SNAPSHOT.jar`) under the target folder.

## Run

Command line parameters:
1. Filename of the input Chicago TNC dataset
2. Number of requests to the OTP server you'd like to process concurrently (Optional) (Default = `10`)
3. File output name (Optional) (Default = `output.csv`)

For example:

```
java -jar target/gnss-ride-hailing-analyzer-1.0.0-SNAPSHOT.jar path/to/file/filename2.csv 10 "output1.csv"
```

An `output1.csv` file will be generated in the project root, which will be the input file with additional columns added that describe the scheduled transit trips that could have been taken at the same departure time using the same origin and destination as the TNC trips.

## License
```
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
```
