package sorters;

import exeptionHandling.ExceptionAndLogFile;
import readAndWrite.ModifiedBufferedReader;
import readAndWrite.BufferedReadFiles;
import readAndWrite.WritePartOfIntegerFiles;

import java.io.BufferedReader;
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



        // WritePartOfIntegerFiles writePartOfFiles = new WritePartOfIntegerFiles();
        // writePartOfFiles.write(args[offset], bufferList);
        // bufferList.forEach(System.out::println);//test
    }

    private void sort(List<ModifiedBufferedReader> modifiedBufferedReaderList, SortingMethod sortingMethod) {//нужна именно
        // Map т.к. по значению можно выбрасывать ошибки и писать логи по имени файла

        int minValue = Integer.MAX_VALUE;
        ModifiedBufferedReader  mBufferedReaderWithMinValue;//буферридер с текущим минимальным значением внутри
        int size = modifiedBufferedReaderList.size();// количество буферидеов
        int finishedModBufferedReader = 0;//количество буферридеров из которых взяли все данные
        List<Integer> integerList = new ArrayList<>(1000);//создадим коллекцию которую при заполнении будем
        // записывать в файл и чистить объем 1000 можно заменить внешним параметром/аргументом

        WritePartOfIntegerFiles writePartOfFiles = new WritePartOfIntegerFiles();//объект с помощью которого запишем выходной файл

        while (size > finishedModBufferedReader) {//пока есть незавершенные буферридеры
            for (ModifiedBufferedReader mbr : modifiedBufferedReaderList) {
                try {
                    mbr.readLine();
                    try {
                        int val = Integer.parseInt(mbr.getCurrentValue());
                        //здесь должна быть сложная логика






                        if (val < minValue) {
                            minValue = val;
                            mBufferedReaderWithMinValue = mbr;
                        }
                        System.out.println(val);//вывод на экран что бы понимать что происходит
                        integerList.add(val);





                        //конец сложной логики
                    } catch (NumberFormatException e) {
                        new ExceptionAndLogFile("Something wrong in file '" + mbr.getNameOfFile() +//это ошибка если значение НЕ int. Запишется в лог файл (запросили путь к файлу через мапу)
                                "' error message: " + e.getMessage()+" in row: " + mbr.getRowsCount());
                    }


                } catch (IOException e) {
                    new ExceptionAndLogFile("Something wrong in file '" + mbr.getNameOfFile() +//это ошибка если у буферридера не все хорошо
                            "' error message: " + e.getMessage());
                }

                try {
                    if (!mbr.ready()) {//если буферридер не готов
                        mbr.close();//закрываем его
                        modifiedBufferedReaderList.remove(mbr);//чистим лист
                        finishedModBufferedReader++;//один поток буфера завершен
                    }
                } catch (IOException e) {
                    new ExceptionAndLogFile("Something wrong in file '" + mbr.getNameOfFile() +
                            "' error message: " + e.getMessage());
                }
            }
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
