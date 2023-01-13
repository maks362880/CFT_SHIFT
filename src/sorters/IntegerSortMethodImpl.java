package sorters;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IntegerSortMethodImpl implements SortMethod {

    SortingMethod sortingMethod;
    int maxPartSizeFileKb;

    public IntegerSortMethodImpl(SortingMethod sortingMethod, int maxPartSizeFileKb) {
        this.sortingMethod = sortingMethod;
        this.maxPartSizeFileKb = maxPartSizeFileKb;
    }


    @Override
    public List sort(String[] args, int offset) {
        List<Integer> bufferList = new ArrayList<>();
        for (int i = offset; i < args.length - 1; i++) {//не забываем что значение (args.length - 1) - это имя выходного файла!
            readPartOfFile(bufferList, args[i]);
        }
        writePartOfFiles(args, bufferList);
        bufferList.forEach(System.out::println);//test
        return null;
    }

    private static void writePartOfFiles(String[] args, List<Integer> bufferList) {
        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter("resource\\" + args[args.length - 1], true))) {//true - дописывание в конец файла
            for (Integer val : bufferList) {
                bw.write(val);
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readPartOfFile(List<Integer> bufferList, String args) {
        try (BufferedReader br = new BufferedReader(
                new FileReader("resource\\" + args), maxPartSizeFileKb * 1024)) {
            String line;
            while ((line = br.readLine()) != null) {
                bufferList.add(Integer.parseInt(line));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
