package DAO;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class WritePartOfIntegerFilesImpl implements  WritePartOfFiles{

@Override
    private static void write(String[] args, List<Integer> bufferList) {
        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter("resource\\" + args[args.length - 1], true))) {//true - дописывание в конец файла
            for (Integer val : bufferList) {
                bw.write(val);
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
