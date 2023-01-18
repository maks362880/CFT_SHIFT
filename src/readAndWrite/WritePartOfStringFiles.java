package readAndWrite;

import exeptionHandling.ExceptionAndLogFile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class WritePartOfStringFiles {

    public void write(String outputFileName, List<String> bufferList) {
        try (BufferedWriter bw = new BufferedWriter(
              //  new FileWriter("resource\\" + outputFileName, true))) {
                new FileWriter(outputFileName, true))) {
            for (String val : bufferList) {
                bw.write(val);
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {
            new ExceptionAndLogFile("Something wrong in file '" + outputFileName +
                    "' error message: " + e.getMessage());
        }
    }
}
