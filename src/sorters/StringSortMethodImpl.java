package sorters;

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
    public List sort(String[] args, int offset) {
        List<String> bufferList = new ArrayList<>();
        for (int i = offset; i < args.length - 1; i++) {//не забываем что значение (args.length - 1) - это имя выходного файла!
            readPartOfFile(bufferList, args[i]);
        }
        writePartOfFiles(args, bufferList);
        bufferList.forEach(System.out::println);//test
        return null;
    }

    private static void writePartOfFiles(String[] args, List<String> bufferList) {//перенести в DAO
        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter("resource\\" + args[args.length - 1], true))) {//true - дописывание в конец файла
            for (String val : bufferList) {
                bw.write(val);
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readPartOfFile(List<String> bufferList, String args) {//перенести в DAO
        try (BufferedReader br = new BufferedReader(
                new FileReader("resource\\" + args), maxPartSizeFileKb * 1024)) {
            String line;
            while ((line = br.readLine()) != null) {
                bufferList.add(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}