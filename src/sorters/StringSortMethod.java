package sorters;

import exeptionHandling.ExceptionAndLogFile;
import readAndWrite.ModifiedBufferedReader;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.TreeMap;

public class StringSortMethod {

    private final List<ModifiedBufferedReader> modifiedBufferedReaderList;
    private final BufferedWriter bw;

    private final TreeMap<String, ModifiedBufferedReader> firstCorrectElements = new TreeMap<>();
    private final TreeMap<String, ModifiedBufferedReader> secondCorrectElements = new TreeMap<>();

    public StringSortMethod(List<ModifiedBufferedReader> modifiedBufferedReaderList, BufferedWriter bw) {
        this.modifiedBufferedReaderList = modifiedBufferedReaderList;
        this.bw = bw;

    }


    public void sortAsc() {
        int sizeOfMbrList = modifiedBufferedReaderList.size();
        int finishedReaders = 0;
        getCorrectElements(firstCorrectElements);
        printAndWriteElement(firstCorrectElements.firstKey());
        firstCorrectElements.clear();
        while (sizeOfMbrList > finishedReaders) {
            getCorrectElements(firstCorrectElements);
            finishedReaders = mbrCloseOrNext(firstCorrectElements.firstEntry().getValue(), finishedReaders);
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

    private void printAndWriteElement(String firstCorrectElements) {
        try {
            System.out.println(firstCorrectElements);
            bw.write(firstCorrectElements);
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void getCorrectElements(TreeMap<String, ModifiedBufferedReader> result) {
        ModifiedBufferedReader emptyMbr = null;
        while (result.isEmpty()) {
            for (ModifiedBufferedReader mbr : modifiedBufferedReaderList) {
                if (mbr.getCurrentStringValueOrNullIfSpaceChar() != null) {
                    result.put(mbr.getCurrentStringValueOrNullIfSpaceChar(), mbr);
                } else {
                    try {
                        new ExceptionAndLogFile("  Error string '"
                                + mbr.getErrorStringValue() + "' in stream: '"
                                + mbr.getNameOfFile() + "' in row: '" + mbr.getRowsCount() + "'");
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
        int finishedReaders = 0;
        getCorrectElements(firstCorrectElements);
        printAndWriteElement(firstCorrectElements.lastKey());
        firstCorrectElements.clear();
        while (sizeOfMbrList > finishedReaders) {
            getCorrectElements(firstCorrectElements);
            finishedReaders = mbrCloseOrNext(firstCorrectElements.lastEntry().getValue(), finishedReaders);
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


    private void checkCorrectSortDataAsc(String ascValue, String preLastAscValue, ModifiedBufferedReader mbrSorted) {
        if (ascValue.compareTo(preLastAscValue) < 0) {
            new ExceptionAndLogFile("Error value '" + ascValue + "' in file: '"
                    + mbrSorted.getNameOfFile() + "' in Row: '"
                    + mbrSorted.getRowsCount() + "'  value '" + ascValue
                    + "' must be older than '" + preLastAscValue + "'");
        } else {
            printAndWriteElement(ascValue);
        }
    }


    private void checkCorrectSortDataDesc(String descValue, String preLastDescValue, ModifiedBufferedReader mbrSorted) {
        if (descValue.compareTo(preLastDescValue) > 0) {
            new ExceptionAndLogFile("Error value '" + descValue + "' in file: '"
                    + mbrSorted.getNameOfFile() + "' in Row: '"
                    + mbrSorted.getRowsCount() + "'  value '" + descValue
                    + "' must be earlier than '" + preLastDescValue + "'");
        } else {
            printAndWriteElement(descValue);
        }
    }


    private int mbrCloseOrNext(ModifiedBufferedReader mbr, int finishedModBufferedReader) {
        try {
            if (!mbr.ready()) {
                System.out.println("     Stream " + mbr.getNameOfFile() + " is closed");
                mbr.close();
                modifiedBufferedReaderList.remove(mbr);
                finishedModBufferedReader++;
            } else {
                mbr.readLine();
            }
        } catch (IOException e) {
            new ExceptionAndLogFile("Something wrong in file '" + mbr.getNameOfFile() +
                    "' error message: " + e.getMessage());
        }
        return finishedModBufferedReader;
    }


}


