package readAndWrite;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class ModifiedBufferedReader extends BufferedReader {
    public ModifiedBufferedReader(Reader in, int sz) {
        super(in, sz);
    }

    @Override
    public String readLine() throws IOException {
        this.rowsCount++;
        return currentValue = super.readLine();
    }

    public ModifiedBufferedReader(Reader in) {
        super(in);
    }

    private int rowsCount = 0;
    private String currentValue;
    private String nameOfFile;

    public int getRowsCount() {
        return rowsCount;
    }

    public void setRowsCount(int rowsCount) {
        this.rowsCount = rowsCount;
    }

    public String getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

    public String getNameOfFile() {
        return nameOfFile;
    }

    public void setNameOfFile(String nameOfFile) {
        this.nameOfFile = nameOfFile;
    }
}
