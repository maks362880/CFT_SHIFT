package configReader;

import builder.BuildAndRunSortMethod;
import exeptionHandling.ExceptionAndLogFile;
import sorters.ClassObjectType;
import sorters.SortingMethod;

public class argsFillOrException {

    private final String[] args;


    public argsFillOrException(String args[]) {
        this.args = args;
    }

    public void createUseArgs(BuildAndRunSortMethod buildAndRunSortMethod) {
        switch (args[0]) {
            case ("-i") -> buildAndRunSortMethod.setClassObjectType(ClassObjectType.Integer);
            case ("-s") -> buildAndRunSortMethod.setClassObjectType(ClassObjectType.String);
            case ("-a") -> buildAndRunSortMethod.setSortingMethod(SortingMethod.Asc);
            case ("-d") -> buildAndRunSortMethod.setSortingMethod(SortingMethod.Desc);
            default -> {
                new ExceptionAndLogFile("Wrong param '" + args[0] + "' with id " + 0);
                throw new RuntimeException();
            }
        }

        switch (args[1]) {
            case ("-i") -> {
                buildAndRunSortMethod.setClassObjectType(ClassObjectType.Integer);
                buildAndRunSortMethod.setOffset(2);
            }
            case ("-s") -> {
                buildAndRunSortMethod.setClassObjectType(ClassObjectType.String);
                buildAndRunSortMethod.setOffset(2);
            }
            case ("-a") -> {
                buildAndRunSortMethod.setSortingMethod(SortingMethod.Asc);
                buildAndRunSortMethod.setOffset(2);
            }
            case ("-d") -> {
                buildAndRunSortMethod.setSortingMethod(SortingMethod.Desc);
                buildAndRunSortMethod.setOffset(2);
            }
            default -> buildAndRunSortMethod.setOffset(1);
        }
    }
}
