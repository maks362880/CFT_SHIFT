import controller.MergeSort;
import controller.MergeSortNumbersAsc;
import controller.MergeSortrunStringAsc;
import controller.MergeSortrunStringDesc;
import exeption_handling.ExceptionWrongParam;

public class Main {
    public static void main(String[] args) {
        MergeSort ms;
        switch (args[0]) {
            case ("-i") -> new MergeSortNumbersAsc(args);
            case ("-s") -> new MergeSortrunStringAsc(args);
            case ("-d") -> new MergeSortrunStringDesc(args);
            default -> new ExceptionWrongParam(args[0]);
        }


    }
}