package bulder;


import controller.MergeSortNumbers;
import controller.MergeSortString;
import exeption_handling.ExceptionWrongParam;

public class FactoryBuilder{
    final int MAX_PART_SIZE_FILE = 4*1024;
    String[] args;


    public FactoryBuilder(String[] args) {
            this.args = args;
    }

    public void start(){
        switch (args[0]) {
            case ("-i") -> new MergeSortNumbers(args, MAX_PART_SIZE_FILE);
            case ("-s") -> new MergeSortString(args, MAX_PART_SIZE_FILE);
            default -> new ExceptionWrongParam("Wrong param " + args[0] + "with id " + 0);
        }
    }
}
