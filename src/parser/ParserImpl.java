package parser;

import java.util.List;

public class ParserImpl implements Parser{

    ClassObjectType classObjectType;
    SortingMethod sortingMethod;
    int maxPartSizeFileKb;
    public ParserImpl(ClassObjectType classObjectType, SortingMethod sortingMethod, int maxPartSizeFileKb) {
        this.classObjectType = classObjectType;
        this.sortingMethod = sortingMethod;
        this.maxPartSizeFileKb = maxPartSizeFileKb;
    }


    @Override
    public List parse(String[] args, int offset) {
       return null;
    }
}
