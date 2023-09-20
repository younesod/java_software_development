public class Deadlock {
    private static final Object lock1= new Object();
    private static final Object lock2= new Object();
    private static class Thread1 extends Thread{

        @Override
        public void run(){
            synchronized (lock1){
                System.out.println("Thread1 acquired lock1");
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println("Thread2 waiting for lock2");
                synchronized (lock2){
                    System.out.println("Thread1 acquired lock2");
                }
            }
        }
    }
    private static class Thread2 extends Thread {
        @Override
        public void run() {
            synchronized (lock2) {
                System.out.println("Thread2 acquired lock2");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread2 waiting for lock1");
                synchronized (lock1) {
                    System.out.println("Thread2 acquired lock1");
                }
            }
        }

        public static void main(String[] args) {
            new Thread1().start();
            new Thread2().start();
        }
    }
}
