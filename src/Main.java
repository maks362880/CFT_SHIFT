import Controller.MergeSort;
import Controller.MergeSortImpl;

public class Main {
    public static void main(String[] args) {
        MergeSort mg = new MergeSortImpl();
        switch (args[0]) {
            case ("-i") -> runNumbersAsc(args);
            case ("-s") -> runStringAsc(args);
            case ("-d") -> runStringDesc(args);
            default -> runErrorMessage(args[0]);
        }


    }
}