public class MyTimerInterrupt extends Thread{
    private int laps;

    public MyTimerInterrupt(int laps){
        this.laps=laps;
    }

    @Override
    public void run(){
        while(!interrupted()){
            try{
                System.out.println("MyTimer: not interrupted");
                sleep(laps);
            }catch(InterruptedException e){
                System.out.println("MyTimer: exception "+e);
                return;
            }
        }
    }
}
