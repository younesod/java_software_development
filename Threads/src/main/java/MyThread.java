public class MyThread extends Thread {
    private String name;

    public MyThread(String name) {
        this.name = name;
    }
    public void run(){
        for(int i=0;i<10;++i){
            for(int j=0;j<5000;++j){
                System.out.println("MyThread: "+name+":"+i);
            }
        }
    }
}
