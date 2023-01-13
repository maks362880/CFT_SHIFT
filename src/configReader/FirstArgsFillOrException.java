package configReader;

import builder.BuildAndRunSortMethod;
import exeptionHandling.ExceptionAndLogFile;
import sorters.ClassObjectType;
import sorters.SortingMethod;

public class FirstArgsFillOrException {

    private final String firstArgs;


    public FirstArgsFillOrException(String firstArgs) {
        this.firstArgs = firstArgs;
    }

    public void createFirstArg(BuildAndRunSortMethod buildAndRunSortMethod) {
        switch (firstArgs) {
            case ("-i") -> buildAndRunSortMethod.setClassObjectType(ClassObjectType.Integer);
            case ("-s") -> buildAndRunSortMethod.setClassObjectType(ClassObjectType.String);
            case ("-a") -> buildAndRunSortMethod.setSortingMethod(SortingMethod.Asc);
            case ("-d") -> buildAndRunSortMethod.setSortingMethod(SortingMethod.Desc);
            default -> new ExceptionAndLogFile("Wrong param '" + firstArgs + "' with id " + 0);

        }
    }

}
