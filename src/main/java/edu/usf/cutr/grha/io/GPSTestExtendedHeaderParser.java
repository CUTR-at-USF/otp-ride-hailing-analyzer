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
package edu.usf.cutr.grha.io;

import static edu.usf.cutr.grha.utils.IOUtils.toLocations;

import com.univocity.parsers.csv.CsvParserSettings;
import edu.usf.cutr.grha.model.Location;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Parses location data from GPSTest with an extended CSV header with comments. These files
 * can't be parsed as simple beans because the first line isn't the column names.
 */
public class GPSTestExtendedHeaderParser extends CsvParser {

    public final String filePath;

    public GPSTestExtendedHeaderParser(String filePath) {
        this.filePath = filePath;
    }

    public List<Location> parseFile() {
        CsvParserSettings csvParserSettings = new CsvParserSettings();
        csvParserSettings.getFormat().setLineSeparator("\n");
        com.univocity.parsers.csv.CsvParser csvParser = new com.univocity.parsers.csv.CsvParser(
            csvParserSettings);
        Reader reader = getReader(filePath);
        if (reader != null) {
            List<String[]> allRows = csvParser.parseAll(reader);
            return toLocations(allRows);
        } else {
            System.out.println("File not found");
            return new ArrayList<>();
        }
    }
}
