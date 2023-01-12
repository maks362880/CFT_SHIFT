package parser;

import java.io.*;
import java.util.List;

public class ParserImpl implements Parser{

    ClassObjectType classObjectType;
    int maxPartSizeFileKb;
    public ParserImpl(ClassObjectType classObjectType, int maxPartSizeFileKb) {
        this.classObjectType = classObjectType;
        this.maxPartSizeFileKb = maxPartSizeFileKb;
    }


    @Override
    public List parse(String[] args, int offset) {
       return null;
    }
}
