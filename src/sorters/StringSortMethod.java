package sorters;

import exeptionHandling.ExceptionAndLogFile;
import readAndWrite.ModifiedBufferedReader;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

public class StringSortMethod {

    private final List<ModifiedBufferedReader> modifiedBufferedReaderList;
    private final BufferedWriter bw;

    public StringSortMethod(List<ModifiedBufferedReader> modifiedBufferedReaderList, BufferedWriter bw) {
        this.modifiedBufferedReaderList = modifiedBufferedReaderList;
        this.bw = bw;

    }

    public void sortAsc() {

       // ModifiedBufferedReader mbrReadyToDelete = null;
        ModifiedBufferedReader mbrSorted = null;
        int size = modifiedBufferedReaderList.size();
        int finishedModBufferedReader = 0;

        while (size > finishedModBufferedReader) {
            for (ModifiedBufferedReader mbr : modifiedBufferedReaderList) {

            }

            finishedModBufferedReader = mbrCloseOrNext(mbrSorted, finishedModBufferedReader);
        }
    }

    private int mbrCloseOrNext(ModifiedBufferedReader mbrSorted, int finishedModBufferedReader) {
        try {
            if (!mbrSorted.ready()) {//если буферридер не готов
                System.out.println("     Stream " + mbrSorted.getNameOfFile() + " is closed");
                mbrSorted.close();//закрываем его
                modifiedBufferedReaderList.remove(mbrSorted);//чистим лист
                finishedModBufferedReader++;//один поток буфера завершен
            } else {
                mbrSorted.readLine();
            }
        } catch (IOException e) {
            new ExceptionAndLogFile("Something wrong in file '" + mbrSorted.getNameOfFile() +
                    "' error message: " + e.getMessage());
        }
        return finishedModBufferedReader;
    }


    public void sortDesc() {
    }
}


