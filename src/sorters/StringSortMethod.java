package sorters;

import exeptionHandling.ExceptionAndLogFile;
import readAndWrite.ModifiedBufferedReader;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

public class StringSortMethod {

    private final List<ModifiedBufferedReader> modifiedBufferedReaderList;
    private final BufferedWriter bw;

    public StringSortMethod(List<ModifiedBufferedReader> modifiedBufferedReaderList, BufferedWriter bw) {
        this.modifiedBufferedReaderList = modifiedBufferedReaderList;
        this.bw = bw;

    }

    public void sortAsc() {
        String firstMaxElement = getFirstMaxElement();
        String ascValue = firstMaxElement;
        String preLastAscValue = firstMaxElement;

        ModifiedBufferedReader mbrReadyToDelete = null;
        ModifiedBufferedReader mbrSorted = modifiedBufferedReaderList.get(0);
        int size = modifiedBufferedReaderList.size();
        int finishedModBufferedReader = 0;

        while (size > finishedModBufferedReader) {
            for (ModifiedBufferedReader mbr : modifiedBufferedReaderList) {
                while (mbr.getCurrentStringValueOrNullIfSpaceChar() == null && !mbr.isClose()) {
                    try {
                        if (mbr.ready()) {
                            new ExceptionAndLogFile("  Error string '"
                                    + mbr.getErrorStringValue() + "' in stream: '"
                                    + mbr.getNameOfFile() + "' in row: '" + mbr.getRowsCount() + "'");
                            mbr.readLine();
                        } else {
                            System.out.println("     Stream " + mbr.getNameOfFile() + " is closed");
                            mbr.close();//закрываем его
                            mbrReadyToDelete = mbr;//ставим метку т.к. в итерраторе удалять запрещено
                            finishedModBufferedReader++;//один поток буфера завершен
                            break;
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                String val = mbr.getCurrentStringValueOrNullIfSpaceChar();
                if (val.compareTo(ascValue) < 0) {//сравнить строки
                    ascValue = val;
                    mbrSorted = mbr;
                }
            }
            deleteFinishMbrAfterIterator(mbrReadyToDelete);
            mbrReadyToDelete = null;
            finishedModBufferedReader = mbrCloseOrNext(mbrSorted, finishedModBufferedReader);
            checkCorrectSortDataAsc(ascValue, preLastAscValue, mbrSorted);
            preLastAscValue = ascValue;
            ascValue = "zzzzzzzzzzzzzzzzzzzzzzzzzz";
        }
        try {
            bw.flush();
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getFirstMaxElement() {
        TreeMap<String,ModifiedBufferedReader> result = new TreeMap<>();
        for (ModifiedBufferedReader mbr : modifiedBufferedReaderList) {
            if (mbr.getCurrentStringValueOrNullIfSpaceChar() != null) {
                result.put(mbr.getCurrentStringValueOrNullIfSpaceChar(),mbr);
            }
        }
        try {
            result.firstEntry().getValue().readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result.firstEntry().getKey();
    }

    public void sortDesc() {
        String firsMinElement = getFirstMinElement();
        String descValue = firsMinElement;
        String preLastAscValue = firsMinElement;

        ModifiedBufferedReader mbrReadyToDelete = null;
        ModifiedBufferedReader mbrSorted = modifiedBufferedReaderList.get(0);
        int size = modifiedBufferedReaderList.size();
        int finishedModBufferedReader = 0;

        while (size > finishedModBufferedReader) {
            for (ModifiedBufferedReader mbr : modifiedBufferedReaderList) {
                while (mbr.getCurrentStringValueOrNullIfSpaceChar() == null && !mbr.isClose()) {
                    try {
                        if (mbr.ready()) {
                            new ExceptionAndLogFile("Invalid string error '"
                                    + mbr.getErrorStringValue() + "' in stream: '"
                                    + mbr.getNameOfFile() + "' in row: '" + mbr.getRowsCount() + "'");
                            mbr.readLine();
                        } else {
                            System.out.println("     Stream " + mbr.getNameOfFile() + " is closed");
                            mbr.close();//закрываем его
                            mbrReadyToDelete = mbr;//ставим метку т.к. в итерраторе удалять запрещено
                            finishedModBufferedReader++;//один поток буфера завершен
                            break;
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                String val = mbr.getCurrentStringValueOrNullIfSpaceChar();
                if (val.compareTo(descValue) > 0) {//сравнить строки
                    descValue = val;
                    mbrSorted = mbr;
                }
            }
            deleteFinishMbrAfterIterator(mbrReadyToDelete);
            mbrReadyToDelete = null;
            finishedModBufferedReader = mbrCloseOrNext(mbrSorted, finishedModBufferedReader);
            checkCorrectSortDataDesc(descValue, preLastAscValue, mbrSorted);
            preLastAscValue = descValue;
            descValue = "aaaaaaaaaaaaaaaaaaaaaaaaaaa";
        }
        try {
            bw.flush();
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getFirstMinElement() {
        TreeMap<String,ModifiedBufferedReader> result = new TreeMap<>();
        for (ModifiedBufferedReader mbr : modifiedBufferedReaderList) {
            if (mbr.getCurrentStringValueOrNullIfSpaceChar() != null) {
                result.put(mbr.getCurrentStringValueOrNullIfSpaceChar(),mbr);
            }
        }
        try {
            result.lastEntry().getValue().readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result.lastEntry().getKey();
    }


    private void checkCorrectSortDataAsc(String ascValue, String preLastAscValue, ModifiedBufferedReader mbrSorted) {
        if (ascValue.compareTo(preLastAscValue) < 0) {
            new ExceptionAndLogFile("Error value '" + ascValue + "' in file: '"
                    + mbrSorted.getNameOfFile() + "' in Row: '"
                    + mbrSorted.getRowsCount() + "'  value '" + ascValue
                    + "' must be older than '" + preLastAscValue + "'");
        } else {
            try {
                System.out.println(ascValue);//test
                bw.write(ascValue);
                bw.newLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private void checkCorrectSortDataDesc(String descValue, String preLastDescValue, ModifiedBufferedReader mbrSorted) {
        if (descValue.compareTo(preLastDescValue) > 0) {
            new ExceptionAndLogFile("Error value '" + descValue + "' in file: '"
                    + mbrSorted.getNameOfFile() + "' in Row: '"
                    + mbrSorted.getRowsCount() + "'  value '" + descValue
                    + "' must be earlier than '" + preLastDescValue + "'");
        } else {
            try {
                System.out.println(descValue);//test
                bw.write(descValue);
                bw.newLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void deleteFinishMbrAfterIterator(ModifiedBufferedReader mbrReadyToDelete) {
        if (mbrReadyToDelete != null) {
            modifiedBufferedReaderList.remove(mbrReadyToDelete);
        }
    }


    private int mbrCloseOrNext(ModifiedBufferedReader mbr, int finishedModBufferedReader) {
        try {
            if (!mbr.ready()) {//если буферридер не готов
                System.out.println("     Stream " + mbr.getNameOfFile() + " is closed");
                mbr.close();//закрываем его
                modifiedBufferedReaderList.remove(mbr);//чистим лист
                finishedModBufferedReader++;//один поток буфера завершен
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


