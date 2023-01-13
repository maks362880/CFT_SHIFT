package sorters;

import DAO.ReadPartOfIntegerFilesImpl;
import DAO.WritePartOfIntegerFiles;

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
        ReadPartOfIntegerFilesImpl readPartOfFiles = new ReadPartOfIntegerFilesImpl();
        for (int i = offset; i < args.length - 1; i++) {//не забываем что значение (args.length - 1) - это имя выходного файла!
            readPartOfFiles.read(bufferList, args[i], maxPartSizeFileKb);
        }
        WritePartOfIntegerFiles writePartOfFiles = new WritePartOfIntegerFiles();
        writePartOfFiles.write(args[args.length - 1], bufferList);
        bufferList.forEach(System.out::println);//test
        return null;
    }


}
