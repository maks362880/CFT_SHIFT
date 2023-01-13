package facade;

import builder.Builder;
import configReader.FirstArgsBuilderOrException;

public class MergeSortFacade {

    private int maxPartSizeFileKb = 1024;//default value in kb
    public void start(String[] args) {
        FirstArgsBuilderOrException ar = new FirstArgsBuilderOrException(args[0]);
        Builder builder = new Builder(args,maxPartSizeFileKb);
        ar.createFirstArg(builder);
        builder.create();
    }

    public void setMaxPartSizeFileKb(int maxPartSizeFileKb) {
        this.maxPartSizeFileKb = maxPartSizeFileKb;
    }

    public int getMaxPartSizeFileKb() {
        return maxPartSizeFileKb;
    }
}
