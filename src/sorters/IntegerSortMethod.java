package sorters;

import exeptionHandling.ExceptionAndLogFile;
import readAndWrite.ModifiedBufferedReader;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;

public class IntegerSortMethod {
    private final List<ModifiedBufferedReader> modifiedBufferedReaderList;
    private final BufferedWriter bw;

    public IntegerSortMethod(List<ModifiedBufferedReader> modifiedBufferedReaderList, BufferedWriter bw) {
        this.modifiedBufferedReaderList = modifiedBufferedReaderList;
        this.bw = bw;
    }


    public void sortAsc() {
        int minValue = Integer.MAX_VALUE;
        int preLastMinValue = Integer.MIN_VALUE;
        ModifiedBufferedReader mBufferedReaderWithMinValue = null;//буферридер с текущим минимальным/максимальным значением внутри
        ModifiedBufferedReader mbrReadyToDelete = null;//буфферидер с пометкой на удаление
        int size = modifiedBufferedReaderList.size();// количество буферидеов
        int finishedModBufferedReader = 0;//количество буферридеров из которых взяли все данные

        while (size > finishedModBufferedReader) {//пока есть незавершенные буферридеры
            for (ModifiedBufferedReader mbr : modifiedBufferedReaderList) {
                while (mbr.getCurrentIntValue() == null && !mbr.isClose()) {
                    try {
                        if (mbr.ready()) {
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
                if (mbr.getCurrentIntValue() != null) {
                    int val = mbr.getCurrentIntValue();
                    if (val < minValue) {
                        minValue = val;
                        mBufferedReaderWithMinValue = mbr;
                    }
                }
            }
            if (mbrReadyToDelete != null) {
                modifiedBufferedReaderList.remove(mbrReadyToDelete);
                mbrReadyToDelete = null;
            }
            try {
                if (!mBufferedReaderWithMinValue.ready()) {//если буферридер не готов
                    System.out.println("     Stream " + mBufferedReaderWithMinValue.getNameOfFile() + " is closed");
                    mBufferedReaderWithMinValue.close();//закрываем его
                    modifiedBufferedReaderList.remove(mBufferedReaderWithMinValue);//чистим лист
                    finishedModBufferedReader++;//один поток буфера завершен
                } else {
                    mBufferedReaderWithMinValue.readLine();
                }
            } catch (IOException e) {
                new ExceptionAndLogFile("Something wrong in file '" + mBufferedReaderWithMinValue.getNameOfFile() +
                        "' error message: " + e.getMessage());
            }
            if (minValue < preLastMinValue) {
                new ExceptionAndLogFile("Error value '" + minValue + "' in file: '"
                        + mBufferedReaderWithMinValue.getNameOfFile() + "' in Row: '"
                        + mBufferedReaderWithMinValue.getRowsCount() + "'  value '" + minValue
                        + "' must be grater than '" + preLastMinValue + "'");
            } else {
                try {
                    System.out.println(minValue);//test
                    bw.write(String.valueOf(minValue));
                    bw.newLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            preLastMinValue = minValue;
            minValue = Integer.MAX_VALUE;
        }
        try {
            bw.flush();
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void sortDesc() {
        int maxValue = Integer.MIN_VALUE;
        int preLastMaxValue = Integer.MAX_VALUE;
        ModifiedBufferedReader mBufferedReaderWithMaxValue = null;//буферридер с текущим минимальным/максимальным значением внутри
        ModifiedBufferedReader mbrReadyToDelete = null;//буфферидер с пометкой на удаление
        int size = modifiedBufferedReaderList.size();// количество буферидеов
        int finishedModBufferedReader = 0;//количество буферридеров из которых взяли все данные

        while (size > finishedModBufferedReader) {//пока есть незавершенные буферридеры
            for (ModifiedBufferedReader mbr : modifiedBufferedReaderList) {
                while (mbr.getCurrentIntValue() == null && !mbr.isClose()) {
                    try {
                        if (mbr.ready()) {
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
                if (mbr.getCurrentIntValue() != null) {
                    int val = mbr.getCurrentIntValue();
                    if (val > maxValue) {
                        maxValue = val;
                        mBufferedReaderWithMaxValue = mbr;
                    }
                }
            }
            if (mbrReadyToDelete != null) {
                modifiedBufferedReaderList.remove(mbrReadyToDelete);
                mbrReadyToDelete = null;
            }
            try {
                System.out.println(maxValue);//test
                if (!mBufferedReaderWithMaxValue.ready()) {//если буферридер не готов
                    System.out.println("     Stream " + mBufferedReaderWithMaxValue.getNameOfFile() + " is closed");
                    mBufferedReaderWithMaxValue.close();//закрываем его
                    modifiedBufferedReaderList.remove(mBufferedReaderWithMaxValue);//чистим лист
                    finishedModBufferedReader++;//один поток буфера завершен
                } else {
                    mBufferedReaderWithMaxValue.readLine();
                }
            } catch (IOException e) {
                new ExceptionAndLogFile("Something wrong in file '" + mBufferedReaderWithMaxValue.getNameOfFile() +
                        "' error message: " + e.getMessage());
            }
            if (maxValue > preLastMaxValue) {
                new ExceptionAndLogFile("Error value '" + maxValue + "' in file: '"
                        + mBufferedReaderWithMaxValue.getNameOfFile() + "' in Row: '"
                        + mBufferedReaderWithMaxValue.getRowsCount() + "'  value '" + maxValue
                        + "' must be less than '" + preLastMaxValue + "'");
            } else {
                try {
                    bw.write(String.valueOf(maxValue));
                    bw.newLine();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            preLastMaxValue = maxValue;
            maxValue = Integer.MIN_VALUE;
        }
        try {
            bw.flush();
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
