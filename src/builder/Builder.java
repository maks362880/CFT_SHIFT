package builder;

import sorters.*;

public class Builder {
    private final String[] args;
    private final int maxPartSizeFileKb;

    private ClassObjectType classObjectType;

    private SortingMethod sortingMethod = SortingMethod.Asc;//default ASC - возрастание

    public Builder(String[] args, int maxPartSizeFileKb) {
        this.args = args;
        this.maxPartSizeFileKb = maxPartSizeFileKb;
    }


    public void create() {
        switch (args[1]) {
            case ("-a") -> {
                SortMethod sortMethod;
                if (classObjectType == ClassObjectType.Integer) {
                    sortMethod = new IntegerSortMethodImpl(SortingMethod.Asc, maxPartSizeFileKb);
                    sortMethod.sort(args, 2);
                } else if (classObjectType == ClassObjectType.String) {
                    sortMethod = new StringSortMethodImpl(SortingMethod.Asc, maxPartSizeFileKb);
                    sortMethod.sort(args, 2);
                }
            }
            case ("-d") -> {
                SortMethod sortMethod;
                if (classObjectType == ClassObjectType.Integer) {
                    sortMethod = new IntegerSortMethodImpl(SortingMethod.Desc, maxPartSizeFileKb);
                    sortMethod.sort(args, 2);
                } else if (classObjectType == ClassObjectType.String) {
                    sortMethod = new StringSortMethodImpl(SortingMethod.Desc, maxPartSizeFileKb);
                    sortMethod.sort(args, 2);
                }
            }
            case ("-i") -> {
                SortMethod sortMethod = new IntegerSortMethodImpl(sortingMethod, maxPartSizeFileKb);
                sortMethod.sort(args, 2);
            }
            case ("-s") -> {
                SortMethod sortMethod = new StringSortMethodImpl(sortingMethod, maxPartSizeFileKb);
                sortMethod.sort(args, 2);
            }
            default -> {
                SortMethod sortMethod;
                if (classObjectType == ClassObjectType.Integer) {
                    sortMethod = new IntegerSortMethodImpl(sortingMethod, maxPartSizeFileKb);
                    sortMethod.sort(args, 1);
                }
                if (classObjectType == ClassObjectType.String) {
                    sortMethod = new StringSortMethodImpl(sortingMethod, maxPartSizeFileKb);
                    sortMethod.sort(args, 1);
                }
            }
        }
    }

    public ClassObjectType getClassObjectType() {
        return classObjectType;
    }

    public SortingMethod getSortingMethod() {
        return sortingMethod;
    }

    public String[] getArgs() {
        return args;
    }

    public int getMaxPartSizeFileKb() {
        return maxPartSizeFileKb;
    }

    public void setClassObjectType(ClassObjectType classObjectType) {
        this.classObjectType = classObjectType;
    }

    public void setSortingMethod(SortingMethod sortingMethod) {
        this.sortingMethod = sortingMethod;
    }
}
