package DAO;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ReadPartOfIntegerFilesImpl implements ReadPartOfFiles{

    public read(List<Integer> bufferList, String args) {
        try (BufferedReader br = new BufferedReader(
                new FileReader("resource\\" + args), maxPartSizeFileKb * 1024)) {
            String line;
            while ((line = br.readLine()) != null) {
                bufferList.add(Integer.parseInt(line));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
