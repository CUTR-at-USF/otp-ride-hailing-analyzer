package usf.edu.cutr.grha.parsing;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import usf.edu.cutr.grha.ProcessorMain;

import java.io.Reader;
import java.util.Arrays;
import java.util.List;

public class CsvFileParser extends ProcessorMain {
    public final String filePath;

    public CsvFileParser(String filePath) {
        this.filePath = filePath;
    }

    public void parseFile() {
        CsvParserSettings csvParserSettings = new CsvParserSettings();
        csvParserSettings.getFormat().setLineSeparator("\n");
        CsvParser csvParser = new CsvParser(csvParserSettings);
        Reader reader = getReader(filePath);
        if (reader != null) {
            List<String[]> allRows = csvParser.parseAll(getReader(filePath));
            for (String[] rows : allRows) {
                System.out.println(Arrays.toString(rows));
            }
        }
    }
}
