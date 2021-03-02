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

import java.io.*;
import java.nio.charset.StandardCharsets;

public class CsvParser {

    /**
     * Creates a reader for a resource in the relative path.
     * Fetched from https://github.com/uniVocity/univocity-parsers/blob/master/src/test/java/com/univocity/parsers/examples/Example.java#L39
     *
     * @param inputStream input stream of the file to be read
     *
     * @return a reader of the resource, or null if the file couldn't be read
     */

    public Reader getReader(InputStream inputStream) {
        if (inputStream != null) {
            return new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        }
        return null;
    }

    /**
     * Creates an input stream for the file
     * @param filename Path of the file
     * @return input stream for the @link #getReader() function above.
     * @throws FileNotFoundException
     */
    public InputStream getInputStream(String filename) throws FileNotFoundException {
        File file = new File(filename);
        return new FileInputStream(file);
    }
}
