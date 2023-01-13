import configReader.ArgsReader;

public class Main {
    public static void main(String[] args) {
        ArgsReader ar = new ArgsReader(args);
        ar.setMaxPartFileSizeKb(1024);//optional value
        ar.start();
    }

}