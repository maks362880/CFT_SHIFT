package sorters;

import exeptionHandling.ExceptionAndLogFile;
import readAndWrite.ModifiedBufferedReader;
import readAndWrite.BufferedReadFiles;
import readAndWrite.WritePartOfIntegerFiles;

import java.io.IOException;
import java.util.*;

public class IntegerSortMethodImpl implements SortMethod {

    private final SortingMethod sortingMethod;
    private final int maxPartSizeFileKb;
    private String outputFileName;

    public IntegerSortMethodImpl(SortingMethod sortingMethod, int maxPartSizeFileKb) {
        this.sortingMethod = sortingMethod;
        this.maxPartSizeFileKb = maxPartSizeFileKb;
    }

    @Override
    public void startMethod(String[] args, int offset) {
        this.outputFileName = args[offset];
        List<ModifiedBufferedReader> mBufferList = new ArrayList<>();

        BufferedReadFiles bufferedReadOfFiles = new BufferedReadFiles();
        for (int i = offset + 1; i < args.length; i++) {
            bufferedReadOfFiles.read(mBufferList, args[i], maxPartSizeFileKb);
        }
        sort(mBufferList, sortingMethod);


        // WritePartOfIntegerFiles writePartOfFiles = new WritePartOfIntegerFiles();
        // writePartOfFiles.write(args[offset], bufferList);
        // bufferList.forEach(System.out::println);//test
    }

    private void sort(List<ModifiedBufferedReader> modifiedBufferedReaderList, SortingMethod sortingMethod) {//нужна именно
        // Map т.к. по значению можно выбрасывать ошибки и писать логи по имени файла

        int minValue = Integer.MAX_VALUE;
        ModifiedBufferedReader mBufferedReaderWithMinValue = null;//буферридер с текущим минимальным значением внутри
        int size = modifiedBufferedReaderList.size();// количество буферидеов
        int finishedModBufferedReader = 0;//количество буферридеров из которых взяли все данные
        List<Integer> integerList = new ArrayList<>(1000);//создадим коллекцию которую при заполнении будем
        // записывать в файл и чистить объем 1000 можно заменить внешним параметром/аргументом

        WritePartOfIntegerFiles writePartOfFiles = new WritePartOfIntegerFiles();//объект с помощью которого запишем выходной файл
        for (ModifiedBufferedReader mBuff : modifiedBufferedReaderList) {
            try {
                mBuff.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        while (size > finishedModBufferedReader) {//пока есть незавершенные буферридеры
            for (ModifiedBufferedReader mbr : modifiedBufferedReaderList) {
                while (mbr.getCurrentIntValue() == null && !mbr.isClose()) {
                    try {
                        if (mbr.ready()) {
                            mbr.readLine();
                        } else {
                            System.out.println("Stream " + mBufferedReaderWithMinValue.getNameOfFile() + " is closed");
                            mbr.close();//закрываем его
                            finishedModBufferedReader++;//один поток буфера завершен
                            break;
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (!mbr.isClose() && mbr.getCurrentIntValue() != null) {
                    int val = mbr.getCurrentIntValue();
                    if (val < minValue) {
                        minValue = val;
                        mBufferedReaderWithMinValue = mbr;
                    }
                }
            }
            try {
                if(!mBufferedReaderWithMinValue.isClose()) {
                    integerList.add(minValue);
                    System.out.println(minValue);//test
                    mBufferedReaderWithMinValue.readLine();
                }
                if (!mBufferedReaderWithMinValue.ready()) {//если буферридер не готов
                    System.out.println("Stream " + mBufferedReaderWithMinValue.getNameOfFile() + " is closed");
                    mBufferedReaderWithMinValue.close();//закрываем его
                    modifiedBufferedReaderList.remove(mBufferedReaderWithMinValue);//чистим лист
                    finishedModBufferedReader++;//один поток буфера завершен
                }
            } catch (IOException e) {
                new ExceptionAndLogFile("Something wrong in file '" + mBufferedReaderWithMinValue.getNameOfFile() +
                        "' error message: " + e.getMessage());
            }
            minValue = Integer.MAX_VALUE;

                if (integerList.size() == 1000) {//если лист заполен
                    writePartOfFiles.write(outputFileName, integerList);//записываем кусок (на след кусок будет дозапись в конец преведущего)
                    integerList.clear();//чистим наш массив Integer'ов
                }

        }
        writePartOfFiles.write(outputFileName, integerList);//запись в файл когда массив Integer'ов не дошел до своего лимита а читать уже нечего
    }

    public SortingMethod getSortingMethod() {
        return sortingMethod;
    }

    public int getMaxPartSizeFileKb() {
        return maxPartSizeFileKb;
    }
}
