package readAndWrite;

import exeptionHandling.ExceptionAndLogFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class ModifiedBufferedReader extends BufferedReader {
    public boolean isClose = false;
    private int rowsCount = 0;
    private Integer currentIntValue;
    private String currentStringValue;
    private String nameOfFile;
    private String errorStringValue;

    public ModifiedBufferedReader(Reader in, int sz) {
        super(in, sz);
    }

    @Override
    public String readLine() throws IOException {
        this.rowsCount++;
        return currentStringValue = super.readLine();
    }

    public ModifiedBufferedReader(Reader in) {
        super(in);
    }

    public int getRowsCount() {
        return rowsCount;
    }

    public void setRowsCount(int rowsCount) {
        this.rowsCount = rowsCount;
    }

    public String getCurrentStringValue() {
        if(currentStringValue.contains(" ")){
            errorStringValue = currentStringValue;
            return null;
        }
        return currentStringValue;
    }

    public void setCurrentStringValue(String currentStringValue) {
        this.currentStringValue = currentStringValue;
    }

    public String getNameOfFile() {
        return nameOfFile;
    }

    public void setNameOfFile(String nameOfFile) {
        this.nameOfFile = nameOfFile;
    }

    public Integer getCurrentIntValue() {
        try {
            this.currentIntValue = Integer.parseInt(currentStringValue);
        }catch (NumberFormatException e) {
            new ExceptionAndLogFile("Something wrong in file '" + this.nameOfFile +//это ошибка если значение НЕ int. Запишется в лог файл (запросили путь к файлу через мапу)
                    "' error message: " + e.getMessage() + " in row: " + this.rowsCount);
            currentIntValue = null;
        }

        return currentIntValue;
    }

    public boolean isClose() {
        return isClose;
    }

    @Override
    public void close() throws IOException {
        isClose = true;
        super.close();
    }

    public String getErrorStringValue() {
        return errorStringValue;
    }
}
