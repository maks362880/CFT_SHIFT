package facade;

import builder.BuildAndRunSortMethod;
import configReader.ArgsFillOrException;

public class MergeSortFacade {

    private int maxPartSizeFileKb = 8;
    public void start(String[] args) {
        BuildAndRunSortMethod buildAndRunSortMethod = new BuildAndRunSortMethod(args,maxPartSizeFileKb);
        ArgsFillOrException argsFillOrException = new ArgsFillOrException(args);
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
