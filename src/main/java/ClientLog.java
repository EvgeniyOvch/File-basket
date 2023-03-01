import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class ClientLog {
    List<String[]> log = new ArrayList<>();
    public void log(int productNum, int amount) {
        String[] temp = {Integer.toString(productNum), Integer.toString(amount)};
        log.add(temp);
       // log.add(new String[]{String.valueOf(productNum), String.valueOf(amount)});
    }
    public void exportAsCSV(File txtFile) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(txtFile, true))) {
            writer.writeAll(log);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}