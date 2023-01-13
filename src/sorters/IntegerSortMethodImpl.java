package sorters;

import readAndWrite.ReadPartOfIntegerFiles;
import readAndWrite.WritePartOfIntegerFiles;

import java.util.ArrayList;
import java.util.List;

public class IntegerSortMethodImpl implements SortMethod {

    private final SortingMethod sortingMethod;
    private final int maxPartSizeFileKb;

    public IntegerSortMethodImpl(SortingMethod sortingMethod, int maxPartSizeFileKb) {
        this.sortingMethod = sortingMethod;
        this.maxPartSizeFileKb = maxPartSizeFileKb;
    }

    @Override
    public void sort(String[] args, int offset) {
        List<Integer> bufferList = new ArrayList<>();

        ReadPartOfIntegerFiles readPartOfFiles = new ReadPartOfIntegerFiles();
        for (int i = offset + 1; i < args.length; i++) {
            readPartOfFiles.read(bufferList, args[i], maxPartSizeFileKb);
        }
        WritePartOfIntegerFiles writePartOfFiles = new WritePartOfIntegerFiles();
        writePartOfFiles.write(args[offset], bufferList);
        bufferList.forEach(System.out::println);//test
    }

    public SortingMethod getSortingMethod() {
        return sortingMethod;
    }

    public int getMaxPartSizeFileKb() {
        return maxPartSizeFileKb;
    }
}
