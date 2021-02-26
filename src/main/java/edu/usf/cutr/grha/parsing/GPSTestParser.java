package edu.usf.cutr.grha.parsing;

import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.common.processor.ConcurrentRowProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import edu.usf.cutr.grha.ProcessorMain;
import edu.usf.cutr.grha.model.LocationFixData;
import edu.usf.cutr.grha.utils.LocationFixDataUtils;

import java.util.List;

public class GPSTestParser extends ProcessorMain {
    public final String filePath;

    public GPSTestParser(String filePath) {
        this.filePath = filePath;
    }

    public void parseFile() {
        BeanListProcessor<LocationFixData> beanListProcessor = new BeanListProcessor<>(LocationFixData.class);
        CsvParserSettings csvParserSettings = new CsvParserSettings();
        csvParserSettings.getFormat().setLineSeparator("\n");
        csvParserSettings.setHeaderExtractionEnabled(true);
        csvParserSettings.setProcessor(new ConcurrentRowProcessor(beanListProcessor));
        CsvParser csvParser = new CsvParser(csvParserSettings);
        csvParser.parse(getReader(filePath));
        List<LocationFixData> locationFixDataList = beanListProcessor.getBeans();
        LocationFixDataUtils.printLocationData(locationFixDataList);
    }
}
