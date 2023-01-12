package controller;

import parser.ClassObjectType;
import parser.Parser;
import parser.ParserImpl;
import parser.SortingMethod;

public class MergeSortImpl implements MergeSort {
    private String[] args;
    private int maxPartSizeFileKb;

    private ClassObjectType classObjectType;

    private SortingMethod sortingMethod;

    public MergeSortImpl(String[] args, ClassObjectType classObjectType, int maxPartSizeFile) {
        this.args = args;
        this.classObjectType = classObjectType;
        this.maxPartSizeFileKb = maxPartSizeFile;
    }

    public MergeSortImpl(String[] args, SortingMethod sortingMethod, int maxPartSizeFileKb) {
        this.args = args;
        this.sortingMethod = sortingMethod;
        this.maxPartSizeFileKb = maxPartSizeFileKb;
    }

    @Override
    public void run() {
        Parser parser = new ParserImpl(classObjectType, maxPartSizeFileKb);
        switch (args[1]) {
            case ("-a") -> {
                sortingMethod = SortingMethod.Asc;
                parser.parse(args, 2);
            }
            case ("-d") -> {
                sortingMethod = SortingMethod.Desc;
                parser.parse(args, 2);
            }
            case ("-i") -> {
                classObjectType = ClassObjectType.Integer;
                parser.parse(args, 2);
            }
            case ("-s") -> {
               classObjectType = ClassObjectType.String;
                parser.parse(args, 2);
            }
            default -> {
                sortingMethod = SortingMethod.Asc;
                parser.parse(args, 1);
            }
        }
    }

    public ClassObjectType getClassObjectType() {
        return classObjectType;
    }

    public SortingMethod getSortingMethod() {
        return sortingMethod;
    }
}
