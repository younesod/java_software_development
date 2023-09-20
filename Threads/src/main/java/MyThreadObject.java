public class MyThreadObject extends Thread{
    private MyObject myObject;
    public MyThreadObject(String name, MyObject myObject){
        super(name);
        this.myObject=myObject;
    }

    public void run(){
        String nom = Thread.currentThread().getName();
        System.out.println("My thread: thread "+ nom+ "in run");
        myObject.show();
        System.out.println("My thread: thread "+nom+"out run");
    }

    public static void main(String[] args) {
        MyObject mo1= new MyObject("mo1");
//        MyObject mo2= new MyObject("mo2");
        MyThreadObject mt1= new MyThreadObject("mt1",mo1);
//        MyThreadObject mt2= new MyThreadObject("mt2",mo2);
        mt1.start();
//        mt2.start();
        try{
            Thread.sleep(0L);
        }catch(InterruptedException e){}
        mo1.print();
    }
}
