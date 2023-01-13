package facade;

import builder.BuildAndRunSortMethod;
import configReader.FirstArgsFillOrException;
import configReader.SecondArgsFill;

public class MergeSortFacade {

    private int maxPartSizeFileKb = 1024;//default value in kb
    public void start(String[] args) {
        FirstArgsFillOrException firstArgsFillOrException = new FirstArgsFillOrException(args[0]);
        SecondArgsFill secondArgsFill = new SecondArgsFill(args[1]);
        BuildAndRunSortMethod buildAndRunSortMethod = new BuildAndRunSortMethod(args,maxPartSizeFileKb);
        firstArgsFillOrException.createFirstArg(buildAndRunSortMethod);
        secondArgsFill.createSecondArg(buildAndRunSortMethod);
        buildAndRunSortMethod.create();
    }

    public void setMaxPartSizeFileKb(int maxPartSizeFileKb) {
        this.maxPartSizeFileKb = maxPartSizeFileKb;
    }

    public int getMaxPartSizeFileKb() {
        return maxPartSizeFileKb;
    }
}
