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

import com.univocity.parsers.common.processor.BeanWriterProcessor;
import com.univocity.parsers.csv.CsvWriterSettings;
import edu.usf.cutr.grha.model.ChicagoTncData;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Writes a series of Chicago TNC Data objects to a file one-by-one
 */
public class ChicagoTncStreamWriter extends CsvWriter {
    com.univocity.parsers.csv.CsvWriter uniWriter;
    int counter = 0;

    public ChicagoTncStreamWriter(List<String> headers) throws FileNotFoundException {
        BeanWriterProcessor<ChicagoTncData> beanWriterProcessor =
                new BeanWriterProcessor<>(ChicagoTncData.class);

        CsvWriterSettings csvWriterSettings = new CsvWriterSettings();
        csvWriterSettings.setRowWriterProcessor(beanWriterProcessor);
        csvWriterSettings.setHeaders(headers.toArray(new String[headers.size()]));

        uniWriter = new com.univocity.parsers.csv.CsvWriter(new BufferedOutputStream(
                new FileOutputStream("output.csv")), csvWriterSettings);
        uniWriter.writeHeaders();
    }

    synchronized public void add(ChicagoTncData chicagoTncData) {
        uniWriter.processRecord(chicagoTncData);
        counter++;
        if (counter % 1000 == 0) {
            uniWriter.flush();
        }
    }

    synchronized public void close() {
        uniWriter.flush();
        uniWriter.close();
    }
}
