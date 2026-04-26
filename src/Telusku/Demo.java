package Telusku;

public class Demo {

    public static void main(String[] args){
        Computer lap = new Laptop();
        Computer desk = new Desktop();

        Developer.devApp(lap);
        Developer.devApp(desk);

        X obj = new A();
        obj.show();
        obj.config();

        Z obj1 = new A();
        Y obj2 = new A();

        obj2.run();
        obj1.run();
    }
}
