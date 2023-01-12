package controller;

public class MergeSortString implements MergeSort{

    private String[] args;
    private int MAX_PART_SIZE_FILE;

    public MergeSortString(String[] args, int MAX_PART_SIZE_FILE) {
        this.args = args;
        this.MAX_PART_SIZE_FILE = MAX_PART_SIZE_FILE;
    }
    @Override
    public void run() {
        switch (args[1]) {
            case ("-a") -> ;
            case ("-d") -> ;
            default -> ;
        }
    }

}
