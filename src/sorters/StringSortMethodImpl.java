package sorters;

import exeptionHandling.ExceptionWrongParam;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StringSortMethodImpl implements SortMethod {

    SortingMethod sortingMethod;
    int maxPartSizeFileKb;

    public StringSortMethodImpl(SortingMethod sortingMethod, int maxPartSizeFileKb) {
        this.sortingMethod = sortingMethod;
        this.maxPartSizeFileKb = maxPartSizeFileKb;
    }


    @Override
    public void sort(String[] args, int offset) {
        List<String> bufferList = new ArrayList<>();
        for (int i = offset+1; i < args.length; i++) {//не забываем что значение (args.length - 1) - это имя выходного файла!
            readPartOfFile(bufferList, args[i]);
        }
        writePartOfFiles(args[offset], bufferList);
        bufferList.forEach(System.out::println);//test
    }

    private static void writePartOfFiles(String outputFileName, List<String> bufferList) {//перенести в DAO
        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter("resource\\" + outputFileName, true))) {//true - дописывание в конец файла
            for (String val : bufferList) {
                bw.write(val);
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {
            new ExceptionWrongParam("Something wrong in file '" + outputFileName +
                    "' error message:\n" + e.getMessage());
        }
    }

    private void readPartOfFile(List<String> bufferList, String inputFileName) {//перенести в DAO
        try (BufferedReader br = new BufferedReader(
                new FileReader("resource\\" + inputFileName), maxPartSizeFileKb*1024)) {
            String line;
            while ((line = br.readLine()) != null) {
                bufferList.add(line);
            }
        } catch (IOException e) {
            new ExceptionWrongParam("Something wrong in file '" + inputFileName +
                    "' error message:\n" + e.getMessage());
        }
    }
}
