package readAndWrite;

import exeptionHandling.ExceptionAndLogFile;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class BufferedReadFiles {

    public void read(List<ModifiedBufferedReader> mBufferList, String inputFileName, int maxPartSizeFileKb) {
        try {
            ModifiedBufferedReader mbr = new ModifiedBufferedReader(
                    new FileReader("resource\\" + inputFileName), maxPartSizeFileKb * 1024);
            mbr.setNameOfFile(inputFileName);
            mBufferList.add(mbr);
        } catch (IOException e) {
            new ExceptionAndLogFile("Something wrong in file '" + inputFileName +
                    "' error message: " + e.getMessage());
        }
    }
}
