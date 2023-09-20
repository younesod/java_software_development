public class MyObject {
    private String name;
    public MyObject(String name){
        this.name=name;
    }
    public synchronized void show(){
        String nom= Thread.currentThread().getName();
        System.out.println("My object: thread "+ nom+",objet "+ name+ "in show");
        try{
            Thread.sleep(7000);
        }catch(InterruptedException e){
            System.out.println("My object: thread "+ nom+",objet "+ name+ "out show");
        }
    }
    public synchronized  void print(){
        String nom= Thread.currentThread().getName();
        System.out.println("My object: thread "+ nom+",objet "+ name+ "in print");
        try{
            Thread.sleep(7000);
        }catch(InterruptedException e){
            System.out.println("My object: thread "+ nom+",objet "+ name+ "out print");
        }
    }
}
