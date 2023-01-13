package builder;

import sorters.*;

public class BuildAndRunSortMethod {

    private SortingMethod sortingMethod = SortingMethod.Asc;//default ASC - возрастание
    public void create() {
        if (classObjectType == ClassObjectType.Integer) {
            SortMethod sortMethod = new IntegerSortMethodImpl(sortingMethod, maxPartSizeFileKb);
            sortMethod.sort(args, offset);
        } else if (classObjectType == ClassObjectType.String) {
            SortMethod sortMethod = new StringSortMethodImpl(sortingMethod, maxPartSizeFileKb);
            sortMethod.sort(args, offset);
        }
    }

    public void setClassObjectType(ClassObjectType classObjectType) {
        this.classObjectType = classObjectType;
    }

    public void setSortingMethod(SortingMethod sortingMethod) {
        this.sortingMethod = sortingMethod;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public BuildAndRunSortMethod(String[] args, int maxPartSizeFileKb) {
        this.args = args;
        this.maxPartSizeFileKb = maxPartSizeFileKb;
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

    private final String[] args;
    private final int maxPartSizeFileKb;

    private int offset;

    private ClassObjectType classObjectType;
}
