package readAndWrite;

import exeptionHandling.ExceptionWrongParam;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class WritePartOfStringFiles {

    public static void write(String outputFileName, List<String> bufferList) {
        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter("resource\\" + outputFileName, true))) {//true - дописывание в конец файла
            for (String val : bufferList) {
                bw.write(val);
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {
            new ExceptionWrongParam("Something wrong in file '" + outputFileName +
                    "' error message: " + e.getMessage());
        }
    }


}
