import facade.MergeSortFacade;

public class Main {
    public static void main(String[] args) {
        MergeSortFacade mergeSort = new MergeSortFacade();
        mergeSort.setMaxPartSizeFileKb(1024);//не обязательный параметр
        mergeSort.start(args);
    }

}