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

import com.univocity.parsers.common.processor.BeanListProcessor;
import edu.usf.cutr.grha.model.Location;

import java.io.InputStream;
import java.util.List;

/**
 * Parses location data from GPSTest that have been altered to remove the extended CSV header with
 * comments. As a result these files can be parsed as simple beans because the first line is the
 * column names.
 */
public class GPSTestParser extends CsvParser {
    public InputStream inputStream;


    public GPSTestParser(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public List<Location> parseFile() {
        BeanListProcessor<Location> beanListProcessor = new BeanListProcessor<>(Location.class);
        return parseFile(beanListProcessor, inputStream);
    }
}
