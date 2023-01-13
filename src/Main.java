import facade.MergeSortFacade;

public class Main {
    public static void main(String[] args) {
        MergeSortFacade mergeSort = new MergeSortFacade();
        mergeSort.setMaxPartSizeFileKb(8);//не обязательны параметр
        mergeSort.start(args);
    }

}