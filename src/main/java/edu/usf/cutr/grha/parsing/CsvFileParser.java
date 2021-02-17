package edu.usf.cutr.grha.parsing;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import edu.usf.cutr.grha.ProcessorMain;
import edu.usf.cutr.grha.model.LocationFixData;
import edu.usf.cutr.grha.utils.LocationFixDataUtils;

import java.io.Reader;
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
            List<String[]> allRows = csvParser.parseAll(reader);
            printLocationData(LocationFixDataUtils.saveLocationData(allRows));
        }
    }

    public void printLocationData(List<LocationFixData> data) {
        for (LocationFixData locationFixData: data) {
            System.out.println(locationFixData.getFix() + " " + locationFixData.getProvider() +" " + locationFixData.getLatitude() +
                    locationFixData.getLongitude() + " " + locationFixData.getAccuracy() + " " + locationFixData.getSpeed() +
                    locationFixData.getAltitude() + " " + locationFixData.getTimeInMs());
        }
    }
}
