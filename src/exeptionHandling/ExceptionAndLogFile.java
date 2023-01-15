package exeptionHandling;

import readAndWrite.WritePartOfStringFiles;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExceptionAndLogFile {
    public ExceptionAndLogFile(String message) {
        message = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss ")) + message;
        System.out.println(message);
        WritePartOfStringFiles writeString = new WritePartOfStringFiles();
        writeString.write("log.txt", List.of(message));
    }
}
