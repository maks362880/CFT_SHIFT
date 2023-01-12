import bulder.FactoryBuilder;

public class Main {
    public static void main(String[] args) {
        FactoryBuilder fb = new FactoryBuilder(args);
        fb.setMaxPartFileSizeKb(8*1024);//optional value
        fb.start();
    }

}