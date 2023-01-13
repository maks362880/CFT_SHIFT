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

    private void sort(Map<BufferedReader, String> bufferList, SortingMethod sortingMethod) {
        int minValue = Integer.MAX_VALUE;
        BufferedReader bufferedReaderWithMinValue;
        int size = bufferList.size();
        int finishedBufferedReader = 0;
        List<Integer> integerList = new ArrayList<>(1000);
        WritePartOfIntegerFiles writePartOfFiles = new WritePartOfIntegerFiles();

        while (size > finishedBufferedReader) {
            for (Map.Entry<BufferedReader, String> reader : bufferList.entrySet()) {
                try {
                    try {
                        int val = Integer.parseInt(reader.getKey().readLine());
                        if (val < minValue) {
                            minValue = val;
                            bufferedReaderWithMinValue = reader.getKey();
                        }
                        System.out.println(val);//test
                        integerList.add(val);
                    } catch (NumberFormatException e) {
                        new ExceptionAndLogFile("Something wrong in file '" + reader.getValue() +
                                "' error message: " + e.getMessage());
                    }


                } catch (IOException e) {
                    new ExceptionAndLogFile("Something wrong in file '" + reader.getValue() +
                            "' error message: " + e.getMessage());
                }

                try {
                    if (!reader.getKey().ready()) {
                        reader.getKey().close();
                        bufferList.remove(reader.getKey());
                        finishedBufferedReader++;
                    }
                } catch (IOException e) {
                    new ExceptionAndLogFile("Something wrong in file '" + reader.getValue() +
                            "' error message: " + e.getMessage());
                }
            }
            if (integerList.size() == 1000) {
                writePartOfFiles.write(outputFileName, integerList);
                integerList.clear();
            }
        }
        writePartOfFiles.write(outputFileName, integerList);
    }

    public SortingMethod getSortingMethod() {
        return sortingMethod;
    }

    public int getMaxPartSizeFileKb() {
        return maxPartSizeFileKb;
    }
}
