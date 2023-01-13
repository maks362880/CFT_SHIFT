package sorters;

import exeptionHandling.ExceptionAndLogFile;
import readAndWrite.ReadPartOfIntegerFiles;
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
    public void sort(String[] args, int offset) {
        this.outputFileName = args[offset];
        Map<BufferedReader, String> bufferList = new HashMap<>();

        ReadPartOfIntegerFiles readPartOfFiles = new ReadPartOfIntegerFiles();
        for (int i = offset + 1; i < args.length; i++) {
            readPartOfFiles.read(bufferList, args[i], maxPartSizeFileKb);
        }
        sort(bufferList, sortingMethod);


        // WritePartOfIntegerFiles writePartOfFiles = new WritePartOfIntegerFiles();
        // writePartOfFiles.write(args[offset], bufferList);
        // bufferList.forEach(System.out::println);//test
    }

    private void sort(Map<BufferedReader, String> bufferMap, SortingMethod sortingMethod) {//нужна именно
        // Map т.к. по значению можно выбрасывать ошибки и писать логи по имени файла

        int minValue = Integer.MAX_VALUE;
        BufferedReader bufferedReaderWithMinValue;//буферридер с текущим минимальным значением внутри
        int size = bufferMap.size();// количество буферидеов
        int finishedBufferedReader = 0;//количество буферридеров из которых взяли все данные
        List<Integer> integerList = new ArrayList<>(1000);//создадим коллекцию которую при заполнении будем
        // записывать в файл и чистить объем 1000 можно заменить внешним параметром/аргументом

        List<BufferedReader> bufferedReaderList = new ArrayList<>(bufferMap.size());//для простоты работы с потоками из буферридеров
        //перезапишем их в лист, ошибки можно найти кинув ссылку объекта буфера в мапу и получить оттуда значение
        for (Map.Entry<BufferedReader, String> reader : bufferMap.entrySet()) {
            bufferedReaderList.add(reader.getKey());//сама перезапись в лист
        }

        WritePartOfIntegerFiles writePartOfFiles = new WritePartOfIntegerFiles();//объект с помощью которого запишем выходной файл

        while (size > finishedBufferedReader) {//пока есть незавершенные буферридеры
            for (BufferedReader reader : bufferedReaderList) {
                try {
                    try {
                        int val = Integer.parseInt(reader.readLine());
                        //здесь должна быть сложная логика






                        if (val < minValue) {
                            minValue = val;
                            bufferedReaderWithMinValue = reader;
                        }
                        System.out.println(val);//вывод на экран что бы понимать что происходит
                        integerList.add(val);





                        //конец сложной логики
                    } catch (NumberFormatException e) {
                        new ExceptionAndLogFile("Something wrong in file '" + bufferMap.get(reader) +//это ошибка если значение НЕ int. Запишется в лог файл (запросили путь к файлу через мапу)
                                "' error message: " + e.getMessage());
                    }


                } catch (IOException e) {
                    new ExceptionAndLogFile("Something wrong in file '" + bufferMap.get(reader) +//это ошибка если у буферридера не все хорошо
                            "' error message: " + e.getMessage());
                }

                try {
                    if (!reader.ready()) {//если буферридер не готов
                        reader.close();//закрываем его
                        bufferedReaderList.remove(reader);//чистим лист
                        bufferMap.remove(reader);//чистим мапу
                        finishedBufferedReader++;//один поток буфера завершен
                    }
                } catch (IOException e) {
                    new ExceptionAndLogFile("Something wrong in file '" + bufferMap.get(reader) +
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
