package readAndWrite;

import java.io.BufferedWriter;
import java.io.Writer;

public class WriteFiles extends BufferedWriter{

    public WriteFiles(Writer out) {
        super(out);
    }

    public WriteFiles(Writer out, int sz) {
        super(out, sz);
    }

//    public void write(String outputFileName, String value) {
//        try (BufferedWriter bw = new BufferedWriter(
//                new FileWriter("resource\\" + outputFileName, true))) {//true - дописывание в конец файла
//                bw.write(String.valueOf(value));
//                bw.newLine();
//
//        } catch (IOException e) {
//            new ExceptionAndLogFile("Something wrong in file '" + outputFileName +
//                    "' error message: " + e.getMessage());
//        }
//    }


}
