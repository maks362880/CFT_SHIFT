package readAndWrite;

import exeptionHandling.ExceptionAndLogFile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class WritePartOfIntegerFiles {

    public void write(String outputFileName, List<Integer> bufferList) {
        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter("resource\\" + outputFileName, true))) {//true - дописывание в конец файла
            for (Integer val : bufferList) {
                bw.write(String.valueOf(val));
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {
            new ExceptionAndLogFile("Something wrong in file '" + outputFileName +
                    "' error message: " + e.getMessage());
        }
    }


}