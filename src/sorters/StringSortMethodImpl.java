package sorters;

import readAndWrite.ReadPartOfStringFiles;
import readAndWrite.WritePartOfStringFiles;

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
        for (int i = offset + 1; i < args.length; i++) {
            ReadPartOfStringFiles readPartOfStringFiles = new ReadPartOfStringFiles();
            readPartOfStringFiles.read(bufferList, args[i], maxPartSizeFileKb);
        }
        WritePartOfStringFiles writePartOfStringFiles = new WritePartOfStringFiles();
        writePartOfStringFiles.write(args[offset], bufferList);
        bufferList.forEach(System.out::println);//test
    }

}


