package edu.usf.cutr.grha;

import edu.usf.cutr.grha.parsing.CsvBeanFileParser;
import edu.usf.cutr.grha.parsing.CsvFileParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class ProcessorMain {
    public static void main(String[] args) {
        parseFileByBeans();
    }

    public static void parseFileByString() {
        CsvFileParser csvFileParser = new CsvFileParser("gnss_log_2021_02_05_13_20_58.txt");
        csvFileParser.parseFile();
    }

    public static void parseFileByBeans(){
        CsvBeanFileParser csvBeanFileParser = new CsvBeanFileParser("gnss_log_2021_02_05_13_20_58_beans.txt");
        csvBeanFileParser.parseFile();
    }


    /**
     * Creates a reader for a resource in the relative path.
     * Fetched from https://github.com/uniVocity/univocity-parsers/blob/master/src/test/java/com/univocity/parsers/examples/Example.java#L39
     *
     * @param filePath relative path of the resource to be read
     *
     * @return a reader of the resource
     */
    public static Reader getReader(String filePath) {
        System.out.println(filePath);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(filePath);
        if (inputStream != null) {
            return new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        }
        return null;
    }
}
