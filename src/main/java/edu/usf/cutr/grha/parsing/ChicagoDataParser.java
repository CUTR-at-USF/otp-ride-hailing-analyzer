package edu.usf.cutr.grha.parsing;

import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.common.processor.ConcurrentRowProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import edu.usf.cutr.grha.ProcessorMain;
import edu.usf.cutr.grha.model.ChicagoData;
import edu.usf.cutr.grha.utils.ChicagoDataUtils;

import java.io.Reader;
import java.util.List;

public class ChicagoDataParser extends ProcessorMain {

    public final String filePath;

    public ChicagoDataParser(String filePath) {
        this.filePath = filePath;
    }

    public void parseFile() {
        BeanListProcessor<ChicagoData> beanListProcessor = new BeanListProcessor<>(ChicagoData.class);
        CsvParserSettings csvParserSettings = new CsvParserSettings();
        csvParserSettings.getFormat().setLineSeparator("\n");
        csvParserSettings.setHeaderExtractionEnabled(true);
        csvParserSettings.setProcessor(new ConcurrentRowProcessor(beanListProcessor));
        CsvParser csvParser = new CsvParser(csvParserSettings);
        Reader reader = getReader(filePath);
        if (reader != null) {
            csvParser.parse(reader);
            List<ChicagoData> chicagoDataList = beanListProcessor.getBeans();
            ChicagoDataUtils.printChicagoData(chicagoDataList);
        }
        else {
            System.out.println("File not found");
        }
    }
}
