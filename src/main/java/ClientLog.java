

import au.com.bytecode.opencsv.CSVWriter;


import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientLog {
    List<String[]> log = new ArrayList<>();

    public void log(int productNum, int amount) {
        log.add(new String[]{String.valueOf(productNum), String.valueOf(amount)});
    }

    public void exportAsCSV(String txtFile) throws IOException {
        try (CSVWriter cw = new CSVWriter(new FileWriter(txtFile, true), ',', CSVWriter.NO_QUOTE_CHARACTER)) {
            cw.writeAll(log);
        }
    }
}

