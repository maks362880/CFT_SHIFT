package sorters;

import exeptionHandling.ExceptionAndLogFile;
import readAndWrite.ModifiedBufferedReader;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.TreeMap;

public class IntegerSortMethod {

    private final List<ModifiedBufferedReader> modifiedBufferedReaderList;
    private final BufferedWriter bw;
    private int finishedReaders = 0;

    private final TreeMap<Integer, ModifiedBufferedReader> firstCorrectElements = new TreeMap<>();
    private final TreeMap<Integer, ModifiedBufferedReader> secondCorrectElements = new TreeMap<>();

    public IntegerSortMethod(List<ModifiedBufferedReader> modifiedBufferedReaderList, BufferedWriter bw) {
        this.modifiedBufferedReaderList = modifiedBufferedReaderList;
        this.bw = bw;

    }

    public void sortAsc() {
        int sizeOfMbrList = modifiedBufferedReaderList.size();
        if(sizeOfMbrList == 0){
            new ExceptionAndLogFile("All files are empty nothing to read");
            throw new RuntimeException("All files are empty nothing to read");
        }
        getCorrectElements(firstCorrectElements);
        printAndWriteElement(firstCorrectElements.firstKey());
        firstCorrectElements.clear();
        while (sizeOfMbrList > finishedReaders) {
            getCorrectElements(firstCorrectElements);
            mbrCloseOrNext(firstCorrectElements.firstEntry().getValue());
            if (finishedReaders != sizeOfMbrList) {
                getCorrectElements(secondCorrectElements);
                checkCorrectSortDataAsc(secondCorrectElements.firstKey(), firstCorrectElements.firstKey(),
                        firstCorrectElements.firstEntry().getValue());
                firstCorrectElements.clear();
                secondCorrectElements.clear();
            }
        }
        try {
            bw.flush();
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void printAndWriteElement(Integer firstCorrectElements) {
        try {
            System.out.println(firstCorrectElements);
            bw.write(String.valueOf(firstCorrectElements));
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void getCorrectElements(TreeMap<Integer, ModifiedBufferedReader> result) {
        ModifiedBufferedReader emptyMbr = null;
        while (result.isEmpty()) {
            for (ModifiedBufferedReader mbr : modifiedBufferedReaderList) {
                if (mbr.getCurrentIntValue() != null) {
                    result.put(mbr.getCurrentIntValue(), mbr);
                } else {
                    try {
                        mbr.readLine();
                        if (!mbr.ready()) {
                            emptyMbr = mbr;
                            System.out.println("     Stream " + emptyMbr.getNameOfFile() + " is closed");
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            if (emptyMbr != null) {
                modifiedBufferedReaderList.remove(emptyMbr);
            }
        }
    }

    public void sortDesc() {
        int sizeOfMbrList = modifiedBufferedReaderList.size();
        getCorrectElements(firstCorrectElements);
        printAndWriteElement(firstCorrectElements.lastKey());
        firstCorrectElements.clear();
        while (sizeOfMbrList > finishedReaders) {
            getCorrectElements(firstCorrectElements);
            mbrCloseOrNext(firstCorrectElements.lastEntry().getValue());
            if (finishedReaders != sizeOfMbrList) {
                getCorrectElements(secondCorrectElements);
                checkCorrectSortDataDesc(secondCorrectElements.lastKey(), firstCorrectElements.lastKey(),
                        firstCorrectElements.lastEntry().getValue());
                firstCorrectElements.clear();
                secondCorrectElements.clear();
            }
        }
        try {
            bw.flush();
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void checkCorrectSortDataAsc(Integer ascValue, Integer preLastAscValue, ModifiedBufferedReader mbrSorted) {
        if (ascValue < preLastAscValue) {
            new ExceptionAndLogFile("Error value '" + ascValue + "' in file: '"
                    + mbrSorted.getNameOfFile() + "' in Row: '"
                    + mbrSorted.getRowsCount() + "'  value '" + ascValue
                    + "' must be bigger than '" + preLastAscValue + "'");
        } else {
            printAndWriteElement(ascValue);
        }
    }


    private void checkCorrectSortDataDesc(Integer descValue, Integer preLastDescValue, ModifiedBufferedReader mbrSorted) {
        if (descValue > preLastDescValue) {
            new ExceptionAndLogFile("Error value '" + descValue + "' in file: '"
                    + mbrSorted.getNameOfFile() + "' in Row: '"
                    + mbrSorted.getRowsCount() + "'  value '" + descValue
                    + "' must be smaller than '" + preLastDescValue + "'");
        } else {
            printAndWriteElement(descValue);
        }
    }


    private void mbrCloseOrNext(ModifiedBufferedReader mbr) {
        try {
            if (!mbr.ready()) {
                System.out.println("     Stream " + mbr.getNameOfFile() + " is closed");
                mbr.close();
                modifiedBufferedReaderList.remove(mbr);
                finishedReaders++;
            } else {
                mbr.readLine();
            }
        } catch (IOException e) {
            new ExceptionAndLogFile("Something wrong in file '" + mbr.getNameOfFile() +
                    "' error message: " + e.getMessage());
        }
    }

}
