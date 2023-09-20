package BankAccount;

public class Transaction extends Thread{
    private BankAccount account;
    private int amount;

    public Transaction(BankAccount account,int amount){
        this.account=account;
        this.amount=amount;
    }
    public void run(){
        while(true){
            account.deposit(amount);
            account.withdraw(amount);
            System.out.println("Balance :"+account.getBalance());
        }
    }

    public static void main(String[] args) {
        BankAccount account= new BankAccount(100);
        for(int i=0;i<20;++i){
            Transaction transaction= new Transaction(account,10);
            transaction.start();
        }
    }
}
