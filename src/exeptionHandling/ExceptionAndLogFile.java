package exeptionHandling;

import readAndWrite.WritePartOfStringFiles;

import java.util.List;

public class ExceptionAndLogFile {
    public ExceptionAndLogFile(String message) {
        System.out.println(message);
        WritePartOfStringFiles writeString = new WritePartOfStringFiles();
        writeString.write("log.txt", List.of(message));
    }
}
