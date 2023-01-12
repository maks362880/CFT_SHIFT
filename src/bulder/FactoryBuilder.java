package bulder;

import controller.MergeSortImpl;
import exeption_handling.ExceptionWrongParam;
import parser.ClassObjectType;
import parser.SortingMethod;

public class FactoryBuilder{
   private int maxPartSizeFileKb = 1024;//default value
    private final String[] args;


    public FactoryBuilder(String[] args) {
            this.args = args;
    }

    public void start(){
        switch (args[0]) {
            case ("-i") -> new MergeSortImpl(args, ClassObjectType.Integer, maxPartSizeFileKb).run();
            case ("-s") -> new MergeSortImpl(args, ClassObjectType.String, maxPartSizeFileKb).run();
            case ("-a") -> new MergeSortImpl(args, SortingMethod.Asc, maxPartSizeFileKb).run();
            case ("-d") -> new MergeSortImpl(args, SortingMethod.Desc, maxPartSizeFileKb).run();
            default -> new ExceptionWrongParam("Wrong param " + args[0] + "with id " + 0);
        }
    }

    public void setMaxPartFileSizeKb(int maxPartFileSizeKb) {
        this.maxPartSizeFileKb = maxPartFileSizeKb;
    }
}
