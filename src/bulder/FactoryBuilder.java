package bulder;

import controller.MergeSortNumbers;
import controller.MergeSortString;
import exeption_handling.ExceptionWrongParam;

public class FactoryBuilder{
   private int maxPartSizeFileKb = 1024;//default value
    private final String[] args;


    public FactoryBuilder(String[] args) {
            this.args = args;
    }

    public void start(){
        switch (args[0]) {
            case ("-i") -> new MergeSortNumbers(args, maxPartSizeFileKb).run();
            case ("-s") -> new MergeSortString(args, maxPartSizeFileKb).run();
            default -> new ExceptionWrongParam("Wrong param " + args[0] + "with id " + 0);
        }
    }

    public void setMaxPartFileSizeKb(int maxPartFileSizeKb) {
        this.maxPartSizeFileKb = maxPartFileSizeKb;
    }
}
