public class MyTimer extends Thread{
    private int laps;
    public boolean shouldRun;

    public MyTimer(int laps){
        this.laps=laps;
        shouldRun=true;
    }
    public void run(){
        while(shouldRun){
            try{
                sleep(laps/2);
                System.out.println("MyTimer : run");
                sleep(laps/2);
            }
            catch(InterruptedException e){
                System.out.println(e);
            }
        }
    }
}
