# GNSS Ride Hailing Analyser [![Java CI with Maven](https://github.com/CUTR-at-USF/gnss-ride-hailing-analyzer/actions/workflows/maven.yml/badge.svg)](https://github.com/CUTR-at-USF/gnss-ride-hailing-analyzer/actions/workflows/maven.yml)

Java Application for analysing GPS data points and additional datasets for identifying ride sharing and ride hailing trips. 

## Datasets

The application currently supports two different data inputs:
1. GNSS data traces (potentially from a variety of modes of transportation)
2. Ride-hailing data

The ultimate goal is to use the ride-hailing ground-truth data to identify ride-hailing trips in the raw GNSS traces.

The preliminary GNSS data traces are collected using the [GPSTest](https://github.com/barbeau/gpstest) app. 

The GPSTest datasets are stored as two variations: 
* gnss_log_2021_02_05_13_20_58.txt (Fetched as is from the app)
* gnss_log_2021_02_05_13_20_58_beans.txt (Modified to directly map the headers to the model class)

The preliminary ride-hailing data is from the [Chicago open TNC dataset](https://data.cityofchicago.org/Transportation/Transportation-Network-Providers-Trips/m6dm-c72p/data): 
* Transportation_Network_Providers_reduced_records.csv (small subset of data)

## Build
To build the application use `mvn clean package` command. This command will create a jar file (i.e., gnss-ride-hailing-analyzer-1.0.0-SNAPSHOT.jar) 
under the target folder.

## Run
To run the application use `java -jar` command. 

The first command-line parameter should be the GPSTest data file path with simple headers, and the 2nd parameter should be the file path of the Chicago dataset, and the 3rd argument is where you want the dataset to be exported to. Note that the 3rd argument is optional: 

```
java -jar target/gnss-ride-hailing-analyzer-1.0.0-SNAPSHOT.jar path/to/file/filename1.txt path/to/file/filename2.csv export/to/folder(Optional)
```

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