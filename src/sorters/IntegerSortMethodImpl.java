package sorters;

import DAO.ReadPartOfFiles;
import DAO.ReadPartOfIntegerFilesImpl;
import DAO.WritePartOfFiles;
import DAO.WritePartOfIntegerFilesImpl;

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
        ReadPartOfFiles readPartOfFiles = new ReadPartOfIntegerFilesImpl();
        for (int i = offset; i < args.length - 1; i++) {//не забываем что значение (args.length - 1) - это имя выходного файла!
            readPartOfFiles.read(bufferList, args[i], maxPartSizeFileKb);
        }
        WritePartOfFiles writePartOfFiles = new WritePartOfIntegerFilesImpl();
        writePartOfFiles.write(args, bufferList);
        bufferList.forEach(System.out::println);//test
        return null;
    }


}
