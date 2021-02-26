# GNSS Ride Hailing Analyser [![Java CI with Maven](https://github.com/CUTR-at-USF/gnss-ride-hailing-analyzer/actions/workflows/maven.yml/badge.svg)](https://github.com/CUTR-at-USF/gnss-ride-hailing-analyzer/actions/workflows/maven.yml)

Java Application for analysing GPS data points and additional datasets for identifying ride sharing and ride hailing trips. 

## Datasets

The preliminary dataset is collected using the ![GPSTest](https://github.com/barbeau/gpstest) app. The datasets are stored 
as two variations. 
* gnss_log_2021_02_05_13_20_58.txt (Fetched as is from the app)
* gnss_log_2021_02_05_13_20_58_beans.txt (Modified to directly map the headers to the model class)
* Transportation_Network_Providers_reduced_records.csv (Reduced number of [Chicago TNC data](https://data.cityofchicago.org/Transportation/Transportation-Network-Providers-Trips/m6dm-c72p/data). )

## Build
To build the application use `mvn clean package` command. This command will create a jar file (i.e., gnss-ride-hailing-analyzer-1.0.0-SNAPSHOT.jar) 
under the target folder.

## Run
To run the application use `java -jar` command. Make sure to enter the filename as an argument. You can enter multiple 
filenames arguments by separating them with a space. 
```
java -jar target/gnss-ride-hailing-analyzer-1.0.0-SNAPSHOT.jar filename1 filename2
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