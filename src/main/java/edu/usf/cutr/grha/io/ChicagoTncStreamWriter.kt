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
package edu.usf.cutr.grha.io

import com.univocity.parsers.common.processor.BeanWriterProcessor
import com.univocity.parsers.csv.CsvWriterSettings
import edu.usf.cutr.grha.model.ChicagoTncData
import java.io.BufferedOutputStream
import java.io.FileOutputStream

/**
 * Writes a series of Chicago TNC Data objects to a file one-by-one
 */
class ChicagoTncStreamWriter(headers: List<String>, outputFile: String = DEFAULT_OUTPUT) : CsvWriter() {
    var uniWriter: com.univocity.parsers.csv.CsvWriter
    var counter = 0

    @Synchronized
    fun add(chicagoTncData: ChicagoTncData?) {
        uniWriter.processRecord(chicagoTncData)
        counter++
        if (counter % 1000 == 0) {
            uniWriter.flush()
        }
    }

    @Synchronized
    fun close() {
        uniWriter.flush()
        uniWriter.close()
    }

    init {
        val beanWriterProcessor = BeanWriterProcessor(
            ChicagoTncData::class.java
        )
        val csvWriterSettings = CsvWriterSettings()
        csvWriterSettings.rowWriterProcessor = beanWriterProcessor
        csvWriterSettings.setHeaders(*headers.toTypedArray())
        uniWriter = com.univocity.parsers.csv.CsvWriter(
            BufferedOutputStream(
                FileOutputStream(outputFile)
            ), csvWriterSettings
        )
        uniWriter.writeHeaders()
    }

    companion object {
        const val DEFAULT_OUTPUT = "output.csv"
    }
}