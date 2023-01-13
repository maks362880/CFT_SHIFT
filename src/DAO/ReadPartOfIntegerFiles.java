package DAO;

import exeptionHandling.ExceptionWrongParam;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ReadPartOfIntegerFiles {


    public void read(List<Integer> bufferList, String inputFileName, int maxPartSizeFileKb) {
        try (BufferedReader br = new BufferedReader(
                new FileReader("resource\\" + inputFileName), maxPartSizeFileKb * 1024)) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    bufferList.add(Integer.parseInt(line));
                }
                catch (NumberFormatException e){
                    new ExceptionWrongParam("Something wrong in file '" + inputFileName +
                            "' error message:\n" + e.getMessage());
                }
            }
        } catch (IOException e) {
            new ExceptionWrongParam("Something wrong in file '" + inputFileName +
                    "' error message:\n" + e.getMessage());

        }
    }
}
