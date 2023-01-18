package readAndWrite;

import exeptionHandling.ExceptionAndLogFile;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class BufferedReadFiles {

    public void read(List<ModifiedBufferedReader> mBufferList, String inputFileName, int maxPartSizeFileKb) {
        try {
          //  File file = new File("resource\\" + inputFileName);
            File file = new File(inputFileName);
            if (file.length() != 0L) {
                ModifiedBufferedReader mbr = new ModifiedBufferedReader(
                    //    new FileReader("resource\\" + inputFileName), maxPartSizeFileKb * 1024);
                new FileReader( inputFileName), maxPartSizeFileKb * 1024);
                mbr.setNameOfFile(inputFileName);
                mBufferList.add(mbr);
            }
        } catch (IOException e) {
            new ExceptionAndLogFile("Something wrong in file '" + inputFileName +
                    "' error message: " + e.getMessage());
        }
    }
}
