package DAO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ReadPartOfIntegerFilesImpl {


    public void read(List<Integer> bufferList, String arg, int maxPartSizeFileKb) {
        try (BufferedReader br = new BufferedReader(
                new FileReader("resource\\" + arg), maxPartSizeFileKb * 1024)) {
            String line;
            while ((line = br.readLine()) != null) {
                bufferList.add(Integer.parseInt(line));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
