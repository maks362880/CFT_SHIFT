package sorters;

import readAndWrite.ReadPartFiles;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

public class StringSortMethodImpl implements SortMethod {

   private final SortingMethod sortingMethod;
   private final int maxPartSizeFileKb;
   private String outputFileName;

    public StringSortMethodImpl(SortingMethod sortingMethod, int maxPartSizeFileKb) {
        this.sortingMethod = sortingMethod;
        this.maxPartSizeFileKb = maxPartSizeFileKb;
    }

    @Override
    public void startMethod(String[] args, int offset) {
        this.outputFileName = args[offset];
        Map<BufferedReader, String> bufferList = new HashMap<>();

        ReadPartFiles readPartOfFiles = new ReadPartFiles();
        for (int i = offset + 1; i < args.length; i++) {
            readPartOfFiles.read(bufferList, args[i], maxPartSizeFileKb);
        }
        sort(bufferList, sortingMethod);


//        List<String> bufferList = new ArrayList<>();
//        for (int i = offset + 1; i < args.length; i++) {
//            ReadPartOfStringFiles readPartOfStringFiles = new ReadPartOfStringFiles();
//            readPartOfStringFiles.read(bufferList, args[i], maxPartSizeFileKb);
//        }
//        WritePartOfStringFiles writePartOfStringFiles = new WritePartOfStringFiles();
//        writePartOfStringFiles.write(args[offset], bufferList);
//        bufferList.forEach(System.out::println);//test
    }

    private void sort(Map<BufferedReader, String> bufferList, SortingMethod sortingMethod) {
        //метод не реализованн




    }

    public SortingMethod getSortingMethod() {
        return sortingMethod;
    }

    public int getMaxPartSizeFileKb() {
        return maxPartSizeFileKb;
    }
}


