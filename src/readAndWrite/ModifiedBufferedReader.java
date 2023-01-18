package readAndWrite;

import exeptionHandling.ExceptionAndLogFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class ModifiedBufferedReader extends BufferedReader {

    private int rowsCount = 0;
    private Long currentLongValue;
    private String currentStringValue;
    private String nameOfFile;
    private String errorStringValue;
    public boolean isClose = false;

    public ModifiedBufferedReader(Reader in, int sz) {
        super(in, sz);
    }

    @Override
    public String readLine() throws IOException {
        this.rowsCount++;
        return currentStringValue = super.readLine();
    }

    @Override
    public void close() throws IOException {
        isClose = true;
        super.close();
    }

    public String getCurrentStringValueOrNullIfSpaceChar() {
        if (currentStringValue.contains(" ")) {
            errorStringValue = currentStringValue;
            return null;
        }
        return currentStringValue;
    }

    public Long getCurrentLongValue() {
        try {
            this.currentLongValue = Long.parseLong(currentStringValue);
        } catch (NumberFormatException e) {
            new ExceptionAndLogFile("Something wrong in file '" + this.nameOfFile +
                    "' error message: " + e.getMessage() + " in row: " + this.rowsCount);
            currentLongValue = null;
        }
        return currentLongValue;
    }

    public ModifiedBufferedReader(Reader in) {
        super(in);
    }


    public void setRowsCount(int rowsCount) {
        this.rowsCount = rowsCount;
    }

    public void setCurrentStringValue(String currentStringValue) {
        this.currentStringValue = currentStringValue;
    }

    public void setNameOfFile(String nameOfFile) {
        this.nameOfFile = nameOfFile;
    }

    public boolean isClose() {
        return isClose;
    }

    public int getRowsCount() {
        return rowsCount;
    }

    public String getNameOfFile() {
        return nameOfFile;
    }

    public String getCurrentValue() {
        return currentStringValue;
    }

    public String getErrorStringValue() {
        return errorStringValue;
    }
}
