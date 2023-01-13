package readAndWrite;

import exeptionHandling.ExceptionWrongParam;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class WritePartOfIntegerFiles {

    public static void write(String outputFileName, List<Integer> bufferList) {
        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter("resource\\" + outputFileName, true))) {//true - дописывание в конец файла
            for (Integer val : bufferList) {
                bw.write(String.valueOf(val));
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {
            new ExceptionWrongParam("Something wrong in file '" + outputFileName +
                    "' error message:\n" + e.getMessage());
        }
    }


}
