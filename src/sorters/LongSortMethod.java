package sorters;

import exeptionHandling.ExceptionAndLogFile;
import readAndWrite.ModifiedBufferedReader;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;

public class LongSortMethod {

    private final List<ModifiedBufferedReader> modifiedBufferedReaderList;
    private final BufferedWriter bw;
    private final TreeMap<Long, ModifiedBufferedReader> firstCorrectElements = new TreeMap<>();
    private final TreeMap<Long, ModifiedBufferedReader> secondCorrectElements = new TreeMap<>();

    private int finishedReaders = 0;
    private boolean finishSort = false;
    private boolean skipErrorValue = false;

    public LongSortMethod(List<ModifiedBufferedReader> modifiedBufferedReaderList, BufferedWriter bw) {
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
            getCorrectElements(firstCorrectElements);
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
        if (sizeOfMbrList == 0) {
            new ExceptionAndLogFile("All files are empty nothing to read");
            System.exit(0);
        }
        getCorrectElements(firstCorrectElements);
        printAndWriteElement(firstCorrectElements.lastKey());
        mbrCloseOrNext(firstCorrectElements.lastEntry().getValue());
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


    private void getCorrectElements(TreeMap<Long, ModifiedBufferedReader> result) {
        ModifiedBufferedReader emptyMbr = null;
        while (result.isEmpty()) {
            for (ModifiedBufferedReader mbr : modifiedBufferedReaderList) {
                while (mbr.getCurrentLongValue() == null) {
                    {
                        try {
                            mbr.readLine();
                            if (!mbr.ready()) {
                                emptyMbr = mbr;
                                new ExceptionAndLogFile("Stream '" + emptyMbr.getNameOfFile() + "' is done and closed");
                                finishedReaders++;
                                break;
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }



            if (emptyMbr != null) {
                modifiedBufferedReaderList.remove(emptyMbr);
            }
            if (modifiedBufferedReaderList.size() == 0) {
                finishSort = true;
                if (Objects.requireNonNull(emptyMbr).getCurrentLongValue() != null) {
                    printAndWriteElement(emptyMbr.getCurrentLongValue());
                }
                break;
            }
            for (ModifiedBufferedReader mbr : modifiedBufferedReaderList) {
                result.put(mbr.getCurrentLongValue(), mbr);
            }

        }
    }


    private void printAndWriteElement(Long firstCorrectElements) {
        try {
            System.out.println(firstCorrectElements);
            bw.write(String.valueOf(firstCorrectElements));
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


    private void checkCorrectSortDataAsc(Long ascValue, Long preLastAscValue, ModifiedBufferedReader mbrSorted) {
        if (ascValue < preLastAscValue) {
            new ExceptionAndLogFile("Error value '" + ascValue + "' in file: '"
                    + mbrSorted.getNameOfFile() + "' in Row: '"
                    + mbrSorted.getRowsCount() + "'  value '" + ascValue
                    + "' must be bigger than '" + preLastAscValue + "'");
            skipErrorValue = true;
        } else {
            printAndWriteElement(ascValue);
        }
    }


    private void checkCorrectSortDataDesc(Long descValue, Long preLastDescValue, ModifiedBufferedReader mbrSorted) {
        if (descValue > preLastDescValue) {
            new ExceptionAndLogFile("Error value '" + descValue + "' in file: '"
                    + mbrSorted.getNameOfFile() + "' in Row: '"
                    + mbrSorted.getRowsCount() + "'  value '" + descValue
                    + "' must be smaller than '" + preLastDescValue + "'");
            skipErrorValue = true;
        } else {
            printAndWriteElement(descValue);
        }
    }


}
