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
    private boolean finishSort = false;
    private boolean skipErrorValue = false;
    private int finishedReaders = 0;

    public StringSortMethod(List<ModifiedBufferedReader> modifiedBufferedReaderList, BufferedWriter bw) {
        this.modifiedBufferedReaderList = modifiedBufferedReaderList;
        this.bw = bw;

    }


    public void sortAsc() {
        int sizeOfMbrList = modifiedBufferedReaderList.size();
        if (sizeOfMbrList == 0) {
            new ExceptionAndLogFile("All files are empty nothing to read");
            System.exit(0);
        }
        getCorrectElements(firstCorrectElements);
        printAndWriteElement(firstCorrectElements.firstKey());
        firstCorrectElements.clear();
        while (sizeOfMbrList > finishedReaders) {
            getCorrectElements(firstCorrectElements);
            mbrCloseOrNext(firstCorrectElements.firstEntry().getValue());
            if (finishedReaders != sizeOfMbrList) {
                getCorrectElements(secondCorrectElements);
                if (!finishSort) {
                    checkCorrectSortDataAsc(secondCorrectElements.firstKey(), firstCorrectElements.firstKey(),
                            firstCorrectElements.firstEntry().getValue());
                    if (!skipErrorValue) {
                        firstCorrectElements.clear();
                    }
                    secondCorrectElements.clear();
                }
                skipErrorValue = false;
            }
        }
        try {
            bw.flush();
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
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
                if (!finishSort) {
                    checkCorrectSortDataDesc(secondCorrectElements.lastKey(), firstCorrectElements.lastKey(),
                            firstCorrectElements.lastEntry().getValue());
                    if (!skipErrorValue) {
                        firstCorrectElements.clear();
                    }
                    secondCorrectElements.clear();
                }
            }
        }
        try {
            bw.flush();
            bw.close();
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
                            new ExceptionAndLogFile("Stream '" + emptyMbr.getNameOfFile() + "' is done and closed");
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            if (emptyMbr != null) {
                modifiedBufferedReaderList.remove(emptyMbr);
            }
            if (modifiedBufferedReaderList.size() == 0) {
                finishSort = true;
                try {
                    if (emptyMbr.getCurrentValue() != null) {
                        printAndWriteElement(emptyMbr.getCurrentValue());
                        finishSort = true;
                    }
                } catch (NullPointerException e) {
                    finishSort = true;
                    finishedReaders++;
                    break;
                }
                break;
            }
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


    private void mbrCloseOrNext(ModifiedBufferedReader mbr) {
        try {
            if (!mbr.ready()) {
                new ExceptionAndLogFile("Stream '" + mbr.getNameOfFile() + "' is done and closed");
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


    private void checkCorrectSortDataAsc(String ascValue, String preLastAscValue, ModifiedBufferedReader mbrSorted) {
        if (ascValue.compareTo(preLastAscValue) < 0) {
            new ExceptionAndLogFile("Error value '" + ascValue + "' in file: '"
                    + mbrSorted.getNameOfFile() + "' in Row: '"
                    + mbrSorted.getRowsCount() + "'  value '" + ascValue
                    + "' must be older than '" + preLastAscValue + "'");
            skipErrorValue = true;
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
            skipErrorValue = true;
        } else {
            printAndWriteElement(descValue);
        }
    }


}


