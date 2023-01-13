package sorters;

import readAndWrite.ReadPartOfStringFiles;
import readAndWrite.WritePartOfStringFiles;
import java.util.ArrayList;
import java.util.List;

public class StringSortMethodImpl implements SortMethod {

   private final SortingMethod sortingMethod;
   private final int maxPartSizeFileKb;

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

    public SortingMethod getSortingMethod() {
        return sortingMethod;
    }

    public int getMaxPartSizeFileKb() {
        return maxPartSizeFileKb;
    }
}


