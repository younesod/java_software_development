public class MyThreadObjectRand extends Thread{
    private MyObjectRand myObject;

    public MyThreadObjectRand(String name,MyObjectRand myObject){
        super(name);
        this.myObject=myObject;
    }
    public void run(){
        String nom=Thread.currentThread().getName();
        System.out.println("MyThread :"+nom+" in run");
        myObject.fct();
        System.out.println("MyThread :"+nom+" out run");
    }

    public static void main(String[] args) {
        MyObjectRand mo = new MyObjectRand();
        MyThreadObjectRand t1= new MyThreadObjectRand("t1",mo);
        MyThreadObjectRand t2= new MyThreadObjectRand("t2",mo);

        t1.start();
        Thread.yield();
        t2.start();
    }
}
