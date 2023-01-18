package builder;

import exeptionHandling.ExceptionAndLogFile;
import readAndWrite.BufferedReadFiles;
import readAndWrite.ModifiedBufferedReader;
import sorters.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BuildAndRunSortMethod {
    private final String[] args;
    private final int maxPartSizeFileKb;
    private int offset;
    private ClassObjectType classObjectType = ClassObjectType.Exception;

    private SortingMethod sortingMethod = SortingMethod.Asc;

    public BuildAndRunSortMethod(String[] args, int maxPartSizeFileKb) {
        this.args = args;
        this.maxPartSizeFileKb = maxPartSizeFileKb;
    }

    public String[] getArgs() {
        return args;
    }

    public int getMaxPartSizeFileKb() {
        return maxPartSizeFileKb;
    }

    public int getOffset() {
        return offset;
    }
    public void setClassObjectType(ClassObjectType classObjectType) {
        this.classObjectType = classObjectType;
    }

    public void setSortingMethod(SortingMethod sortingMethod) {
        this.sortingMethod = sortingMethod;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
    public ClassObjectType getClassObjectType() {
        return classObjectType;
    }

    public SortingMethod getSortingMethod() {
        return sortingMethod;
    }

    public void create() {
        List<ModifiedBufferedReader> modifiedBufferedReaderList = getModifiedBufferedReaders(args, offset);
        getFirstElementsInAllModifiedBufferedList(modifiedBufferedReaderList);

        BufferedWriter bw = getOutputStream(args[offset]);

        if (classObjectType == ClassObjectType.Exception) {
            new ExceptionAndLogFile("Necessary args is not found");
        }

        if (classObjectType == ClassObjectType.Integer) {
            LongSortMethod sortMethod = new LongSortMethod(modifiedBufferedReaderList, bw);
            if (sortingMethod == SortingMethod.Asc) {
                sortMethod.sortAsc();
            } else if (sortingMethod == SortingMethod.Desc) {
                sortMethod.sortDesc();
            }
        }

        if (classObjectType == ClassObjectType.String) {
            StringSortMethod sortMethod = new StringSortMethod(modifiedBufferedReaderList, bw);
            if (sortingMethod == SortingMethod.Asc) {
                sortMethod.sortAsc();
            } else if (sortingMethod == SortingMethod.Desc) {
                sortMethod.sortDesc();
            }
        }

    }

    private List<ModifiedBufferedReader> getModifiedBufferedReaders(String[] args, int offset) {
        List<ModifiedBufferedReader> mBufferList = new ArrayList<>();
        BufferedReadFiles bufferedReadOfFiles = new BufferedReadFiles();
        for (int i = offset + 1; i < args.length; i++) {
            bufferedReadOfFiles.read(mBufferList, args[i], maxPartSizeFileKb);
        }
        return mBufferList;
    }


    private void getFirstElementsInAllModifiedBufferedList(List<ModifiedBufferedReader> modifiedBufferedReaderList) {
        for (ModifiedBufferedReader mBuff : modifiedBufferedReaderList) {
            try {
                mBuff.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private BufferedWriter getOutputStream(String outputFileName) {
        BufferedWriter bw = null;
        try {
           // bw = new BufferedWriter(new FileWriter("resource\\" + outputFileName));
            bw = new BufferedWriter(new FileWriter(outputFileName));
        } catch (IOException e) {
            new ExceptionAndLogFile("Output Stream '" + outputFileName + "' threw an exception");
            throw new RuntimeException(e);
        }
        return bw;
    }


}
