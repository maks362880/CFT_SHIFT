package facade;

import builder.BuildAndRunSortMethod;
import configReader.argsFillOrException;

public class MergeSortFacade {

    private int maxPartSizeFileKb = 1024;//default value in kb
    public void start(String[] args) {
        argsFillOrException argsFillOrException = new argsFillOrException(args);
        BuildAndRunSortMethod buildAndRunSortMethod = new BuildAndRunSortMethod(args,maxPartSizeFileKb);
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
