package parser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ParserImpl implements Parser {

    ClassObjectType classObjectType;
    SortingMethod sortingMethod;
    int maxPartSizeFileKb;

    public ParserImpl(ClassObjectType classObjectType, SortingMethod sortingMethod, int maxPartSizeFileKb) {
        this.classObjectType = classObjectType;
        this.sortingMethod = sortingMethod;
        this.maxPartSizeFileKb = maxPartSizeFileKb;
    }


    @Override
    public List parse(String[] args, int offset) {
        List<Object> bufferList = new ArrayList<Object>();//замутить generics и wildcard
        for (int i = offset; i < args.length - 1; i++) {//не забываем что значение (args.length - 1) - это имя выходного файла!
            readPartOfFile(bufferList, args[i]);
        }
        writePartOfFiles(args, bufferList);
        bufferList.forEach(System.out::println);//test
        return null;
    }

    private static void writePartOfFiles(String[] args, List<Object> bufferList) {
        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter("resource\\" + args[args.length - 1], true))) {//true - дописывание в конец файла
            for (Object obj : bufferList) {
                bw.write((String) obj);//решить проблему с кастингом
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readPartOfFile(List<Object> bufferList, String args) {
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
