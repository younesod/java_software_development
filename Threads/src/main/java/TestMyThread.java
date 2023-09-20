public class TestMyThread {
    public static void main(String[] args) {
        MyThread t= new MyThread("one");
        t.start();
       //t.run();

        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 50000; ++j) ;
            System.out.println("TestMyThread: " + i);
        }
    }
}
