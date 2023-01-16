package facade;

import builder.BuildAndRunSortMethod;
import configReader.argsFillOrException;

public class MergeSortFacade {

    private int maxPartSizeFileKb = 8;//default value in kb in bufferedreader
    public void start(String[] args) {
        BuildAndRunSortMethod buildAndRunSortMethod = new BuildAndRunSortMethod(args,maxPartSizeFileKb);
        argsFillOrException argsFillOrException = new argsFillOrException(args);
        argsFillOrException.createUseArgs(buildAndRunSortMethod);
        buildAndRunSortMethod.create();
    }

    public void setMaxPartSizeFileKb(int maxPartSizeFileKb) {
        this.maxPartSizeFileKb = maxPartSizeFileKb;
    }

    public int getMaxPartSizeFileKb() {
        return maxPartSizeFileKb;
    }
}
