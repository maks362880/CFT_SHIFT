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
            deleteFinishMbrAfterIterator(mbrReadyToDelete);
            mbrReadyToDelete = null;
            finishedModBufferedReader = mbrCloseOrNext(mBufferedReaderWithMinValue, finishedModBufferedReader);
            checkCorrectSortDataAsc(minValue, preLastMinValue, mBufferedReaderWithMinValue);
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
            deleteFinishMbrAfterIterator(mbrReadyToDelete);
            mbrReadyToDelete = null;
            finishedModBufferedReader = mbrCloseOrNext(mBufferedReaderWithMaxValue, finishedModBufferedReader);

            checkCorrectSortDataDesc(maxValue, preLastMaxValue, mBufferedReaderWithMaxValue);

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

    private void checkCorrectSortDataDesc(int maxValue, int preLastMaxValue, ModifiedBufferedReader mBufferedReaderWithMaxValue) {
        if (maxValue > preLastMaxValue) {
            new ExceptionAndLogFile("Error value '" + maxValue + "' in file: '"
                    + mBufferedReaderWithMaxValue.getNameOfFile() + "' in Row: '"
                    + mBufferedReaderWithMaxValue.getRowsCount() + "'  value '" + maxValue
                    + "' must be less than '" + preLastMaxValue + "'");
        } else {
            try {
                System.out.println(maxValue);//test
                bw.write(String.valueOf(maxValue));
                bw.newLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }



    private void checkCorrectSortDataAsc(int minValue, int preLastMinValue, ModifiedBufferedReader mBufferedReaderWithMinValue) {
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
    }

}
