package controller;

import parser.ClassObjectType;
import parser.Parser;
import parser.ParserImpl;
import parser.SortingMethod;

public class MergeSortImpl implements MergeSort {
    private String[] args;
    private int maxPartSizeFileKb;

    private ClassObjectType classObjectType;

    private SortingMethod sortingMethod = SortingMethod.Asc;//default ASC - возрастание

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
        switch (args[1]) {
            case ("-a") -> {
                Parser parser = new ParserImpl(classObjectType, SortingMethod.Asc, maxPartSizeFileKb);
                parser.parse(args, 2);
            }
            case ("-d") -> {
                Parser parser = new ParserImpl(classObjectType, SortingMethod.Desc, maxPartSizeFileKb);
                parser.parse(args, 2);
            }
            case ("-i") -> {
                Parser parser = new ParserImpl(ClassObjectType.Integer, SortingMethod.Desc, maxPartSizeFileKb);
                parser.parse(args, 2);
            }
            case ("-s") -> {
                Parser parser = new ParserImpl(ClassObjectType.String, SortingMethod.Desc, maxPartSizeFileKb);
                parser.parse(args, 2);
            }
            default -> {
                Parser parser = new ParserImpl(classObjectType, sortingMethod, maxPartSizeFileKb);
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
