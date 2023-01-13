package exeptionHandling;

import readAndWrite.WritePartOfStringFiles;

import java.util.List;

public class ExceptionWrongParam{
    public ExceptionWrongParam(String message) {
        System.out.println(message);
        //добавить логирование в файл
        WritePartOfStringFiles writeString = new WritePartOfStringFiles();
        writeString.write("log.txt", List.of(message));
    }
}
