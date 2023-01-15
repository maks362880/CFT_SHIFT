package sorters;

import exeptionHandling.ExceptionAndLogFile;
import readAndWrite.ModifiedBufferedReader;
import readAndWrite.BufferedReadFiles;
import readAndWrite.WriteFiles;

import java.io.FileWriter;
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
        List<ModifiedBufferedReader> mBufferList = getModifiedBufferedReaders(args, offset);
        sort(mBufferList, sortingMethod);
    }

    private List<ModifiedBufferedReader> getModifiedBufferedReaders(String[] args, int offset) {
        this.outputFileName = args[offset];
        List<ModifiedBufferedReader> mBufferList = new ArrayList<>();
        BufferedReadFiles bufferedReadOfFiles = new BufferedReadFiles();
        for (int i = offset + 1; i < args.length; i++) {
            bufferedReadOfFiles.read(mBufferList, args[i], maxPartSizeFileKb);
        }
        return mBufferList;
    }

    private void sort(List<ModifiedBufferedReader> modifiedBufferedReaderList, SortingMethod sortingMethod) {//нужна именно
        // Map т.к. по значению можно выбрасывать ошибки и писать логи по имени файла

        int minValue = Integer.MAX_VALUE;
        ModifiedBufferedReader mBufferedReaderWithMinValue = null;//буферридер с текущим минимальным значением внутри
         ModifiedBufferedReader mbrReadyToDelete = null;//буфферидер с пометкой на удаление
        int size = modifiedBufferedReaderList.size();// количество буферидеов
        int finishedModBufferedReader = 0;//количество буферридеров из которых взяли все данные

        WriteFiles writeStream = getOutputStream();
        getFirstElementsInAllModifiedBufferedList(modifiedBufferedReaderList);

        while (size > finishedModBufferedReader) {//пока есть незавершенные буферридеры
            for (ModifiedBufferedReader mbr : modifiedBufferedReaderList) {
                while (mbr.getCurrentIntValue() == null && !mbr.isClose()) {
                    try {
                        if (mbr.ready()) {
                            mbr.readLine();
                        } else {
                            System.out.println("Stream " + mbr.getNameOfFile() + " is closed");
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
                if (!mBufferedReaderWithMinValue.isClose()) {
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
            try {
                writeStream.write(String.valueOf(minValue));
                writeStream.newLine();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            minValue = Integer.MAX_VALUE;
        }
        try {
            writeStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private WriteFiles getOutputStream() {
        WriteFiles writeFile = null;//объект с помощью которого запишем выходной файл
        try {
            writeFile = new WriteFiles(new FileWriter("resource\\" + outputFileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return writeFile;
    }

    private static void getFirstElementsInAllModifiedBufferedList(List<ModifiedBufferedReader> modifiedBufferedReaderList) {
        for (ModifiedBufferedReader mBuff : modifiedBufferedReaderList) {
            try {
                mBuff.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public SortingMethod getSortingMethod() {
        return sortingMethod;
    }

    public int getMaxPartSizeFileKb() {
        return maxPartSizeFileKb;
    }
}
