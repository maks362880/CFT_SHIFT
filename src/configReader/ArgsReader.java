package configReader;

import builder.BuilderImpl;
import exeptionHandling.ExceptionWrongParam;
import sorters.ClassObjectType;
import sorters.SortingMethod;

public class ArgsReader {
   private int maxPartSizeFileKb = 1024;//default value in kb
    private final String[] args;


    public ArgsReader(String[] args) {
            this.args = args;
    }

    public void start(){
        switch (args[0]) {
            case ("-i") -> new BuilderImpl(args, ClassObjectType.Integer, maxPartSizeFileKb).create();
            case ("-s") -> new BuilderImpl(args, ClassObjectType.String, maxPartSizeFileKb).create();
            case ("-a") -> new BuilderImpl(args, SortingMethod.Asc, maxPartSizeFileKb).create();
            case ("-d") -> new BuilderImpl(args, SortingMethod.Desc, maxPartSizeFileKb).create();
            default -> new ExceptionWrongParam("Wrong param " + args[0] + " with id " + 0);
        }
    }

    public void setMaxPartFileSizeKb(int maxPartFileSizeKb) {
        this.maxPartSizeFileKb = maxPartFileSizeKb;
    }
}
