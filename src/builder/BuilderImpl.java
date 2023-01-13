package builder;

import controller.*;

public class BuilderImpl implements Builder {
    private final String[] args;
    private final int maxPartSizeFileKb;

    private ClassObjectType classObjectType;

    private SortingMethod sortingMethod = SortingMethod.Asc;//default ASC - возрастание

    public BuilderImpl(String[] args, ClassObjectType classObjectType, int maxPartSizeFile) {
        this.args = args;
        this.classObjectType = classObjectType;
        this.maxPartSizeFileKb = maxPartSizeFile;
    }

    public BuilderImpl(String[] args, SortingMethod sortingMethod, int maxPartSizeFileKb) {
        this.args = args;
        this.sortingMethod = sortingMethod;
        this.maxPartSizeFileKb = maxPartSizeFileKb;
    }

    @Override
    public void create() {
        switch (args[1]) {
            case ("-a") -> {
                Controller controller;
                if(classObjectType == ClassObjectType.Integer) {
                    controller = new IntegerControllerImpl(SortingMethod.Asc, maxPartSizeFileKb);
                    controller.create(args, 2);
                }else if(classObjectType == ClassObjectType.String){
                    controller = new StringControllerImpl(SortingMethod.Asc, maxPartSizeFileKb);
                    controller.create(args, 2);
                }
            }
            case ("-d") -> {
                Controller controller;
                if(classObjectType == ClassObjectType.Integer) {
                    controller = new IntegerControllerImpl(SortingMethod.Desc, maxPartSizeFileKb);
                    controller.create(args, 2);
                }else if(classObjectType == ClassObjectType.String){
                    controller = new StringControllerImpl(SortingMethod.Desc, maxPartSizeFileKb);
                    controller.create(args, 2);
                }
            }
            case ("-i") -> {
                Controller controller = new IntegerControllerImpl(sortingMethod, maxPartSizeFileKb);
                controller.create(args, 2);
            }
            case ("-s") -> {
                Controller controller = new StringControllerImpl(sortingMethod, maxPartSizeFileKb);
                controller.create(args, 2);
            }
            default -> {
                Controller controller;
                if(classObjectType == ClassObjectType.Integer) {
                    controller = new IntegerControllerImpl(sortingMethod, maxPartSizeFileKb);
                    controller.create(args, 1);
                }
                if(classObjectType == ClassObjectType.String) {
                    controller = new StringControllerImpl(sortingMethod, maxPartSizeFileKb);
                    controller.create(args, 1);
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
}
