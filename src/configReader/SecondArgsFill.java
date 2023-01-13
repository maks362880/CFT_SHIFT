package configReader;

import builder.BuildAndRunSortMethod;
import sorters.ClassObjectType;
import sorters.SortingMethod;

public class SecondArgsFill {

    private final String firstArgs;

    public SecondArgsFill(String firstArgs) {
        this.firstArgs = firstArgs;
    }

    public void createSecondArg(BuildAndRunSortMethod buildAndRunSortMethod) {
        switch (firstArgs) {
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
