package parser;

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


        for (int i = offset; i < args.length; i++) {

        }

        return null;
    }
}
