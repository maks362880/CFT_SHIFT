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
    public List parse(String[] args, int offset) {//не забываем что значение args.length - это имя выходного файла!


            List<Object> bufferList = new ArrayList<Object>();//замутить generics и wildcard


        for (int i = offset; i < args.length - 1; i++) {
            try (BufferedReader br = new BufferedReader(new FileReader("resource\\" + args[i]), maxPartSizeFileKb*1024)) {
                String line;
                while ((line = br.readLine()) != null){
                    bufferList.add(line);
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        bufferList.forEach(System.out::println);//test
        return null;
    }
}
