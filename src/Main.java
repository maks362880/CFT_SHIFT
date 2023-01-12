import controller.*;
import exeption_handling.ExceptionWrongParam;

public class Main {
    public static void main(String[] args) {
        startMethod(args);
    }

    private static void startMethod(String[] args) {
        final int MAX_PART_SIZE_FILE = 1024;
        MergeSort ms;
        switch (args[0]) {
            case ("-i") -> new MergeSortNumbers(args, MAX_PART_SIZE_FILE);
            case ("-s") -> new MergeSortString(args, MAX_PART_SIZE_FILE);
            default -> new ExceptionWrongParam("Wrong param " + args[0] + "with id " + 0);
        }
    }
}