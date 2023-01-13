package readAndWrite;

import exeptionHandling.ExceptionAndLogFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class ReadPartFiles {

    public void read(Map<BufferedReader, String> bufferList, String inputFileName, int maxPartSizeFileKb) {
        try {
            BufferedReader br = new BufferedReader(
                    new FileReader("resource\\" + inputFileName), maxPartSizeFileKb * 1024);
            bufferList.put(br, inputFileName);
        } catch (IOException e) {
            new ExceptionAndLogFile("Something wrong in file '" + inputFileName +
                    "' error message: " + e.getMessage());
        }
    }
}
