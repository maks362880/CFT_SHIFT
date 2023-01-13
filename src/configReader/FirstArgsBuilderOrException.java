package configReader;

import builder.Builder;
import exeptionHandling.ExceptionWrongParam;
import sorters.ClassObjectType;
import sorters.SortingMethod;

public class FirstArgsBuilderOrException {

    private final String firstArgs;


    public FirstArgsBuilderOrException(String firstArgs) {
        this.firstArgs = firstArgs;
    }

    public void createFirstArg(Builder builder) {
        switch (firstArgs) {
            case ("-i") -> builder.setClassObjectType(ClassObjectType.Integer);
            case ("-s") -> builder.setClassObjectType(ClassObjectType.String);
            case ("-a") -> builder.setSortingMethod(SortingMethod.Asc);
            case ("-d") -> builder.setSortingMethod(SortingMethod.Desc);
            default -> new ExceptionWrongParam("Wrong param '" + firstArgs + "' with id " + 0);

        }
    }

}
