public class TestThreadRunnableLambda {
    public static void main(String[] args) {
        new Thread(()->{
            for(int i=0;i<10;++i){
                for(int j=0;j<50000;++j){
                    System.out.println("MyThread lambda : "+i);
                }
            }
        }).start();
        for(int i=0;i<10;++i){
            for(int j=0;j<50000;++j){
                System.out.println("TestMyThread : "+i);
            }
        }
    }
}
