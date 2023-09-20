import java.util.concurrent.Executors;

public class MyThreadPair extends Thread{
    ToujoursPair tp;
    public MyThreadPair(ToujoursPair tp){
        this.tp=tp;
    }
    public void run(){
        while(true){
            int val=tp.getI();
            if(val%2!=0){
                System.out.println("MyThread :"+val);
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) {
        ToujoursPair tp= new ToujoursPair();
        MyThreadPair t= new MyThreadPair(tp);
        t.start();
        while(true){
            tp.next();
            if(tp.getI()% 1000000 ==0){
                System.out.println(tp.getI());
            }
        }
    }
}
