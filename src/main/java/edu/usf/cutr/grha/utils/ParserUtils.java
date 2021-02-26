package edu.usf.cutr.grha.utils;

import edu.usf.cutr.grha.constants.FileNameConstants;
import edu.usf.cutr.grha.parsing.ChicagoDataParser;
import edu.usf.cutr.grha.parsing.GPSTestParser;

public class ParserUtils {

    public static void parseFileByBeans(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (i == 0) {
                if (args[i].equals(FileNameConstants.CHICAGO_DATA_FILE_NAME)) {
                    new ChicagoDataParser(args[i]).parseFile();
                }
                else {
                    System.out.println("Invalid order. Please enter chicago data filename first");
                    System.exit(1);
                }
            }
            else {
                if (args[i].equals(FileNameConstants.GPS_TEST_DATA_FILE_NAME)) {
                    new GPSTestParser(args[i]).parseFile();
                }
                else {
                    System.out.println("File not supported yet");
                    System.exit(1);
                }
            }
        }
    }
}
